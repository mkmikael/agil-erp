package web.agil

import grails.transaction.Transactional
import org.hibernate.SessionFactory
import web.agil.cadastro.Cidade
import web.agil.cadastro.Cliente
import web.agil.cadastro.Endereco
import web.agil.cadastro.Estado
import web.agil.cadastro.Fornecedor
import web.agil.cadastro.Organizacao
import web.agil.cadastro.Participante
import web.agil.cadastro.Pessoa
import web.agil.cadastro.PrecoComponente
import web.agil.cadastro.Produto
import web.agil.cadastro.Telefone
import web.agil.cadastro.UnidadeMedida
import web.agil.financeiro.ItemNotaComercial
import web.agil.financeiro.NotaFiscal
import web.agil.financeiro.enums.TipoNotaFiscal

@Transactional
class ImportNotaFiscalService {

    String filePath = "C:\\Users\\mkmik\\Google Drive\\agil\\XML"

    SessionFactory sessionFactory
    def grailsApplication
    def importProdutoSEFAService

    def execute() {
        filePath = grailsApplication.config.arquivo?.nfs
        log.debug("Init read XML files: ${filePath}")
        def xmlParser = new XmlParser()
        def fileBase = new File(filePath)
        def files = fileBase.list() as List
        def count = 0;
        files.eachWithIndex { fileName, i ->
            if (fileName.endsWith('.xml')) {
                def nf = NotaFiscal.findByArquivoNome(fileName)
                if (!nf)
                    createNotaFiscal(fileName, xmlParser)
                count++
                gormClean()
            }
            log.debug("**********************************$i")
        }
        null
    }

    def createNotaFiscal(String fileName, XmlParser xmlParser) {
        def file = new File("$filePath$File.separator$fileName")
        log.debug("Arquivo          : ${file.absolutePath}")
        def xml = xmlParser.parseText(new String(file.bytes, 'utf-8'))
        def infNFe = xml?.NFe?.infNFe
        if (!infNFe)
            infNFe = xml.infNFe
        def doc = infNFe.dest.CNPJ.text() ?: infNFe.dest.CPF.text()
        doc = doc.trim()
        def valorTotal = infNFe.total.ICMSTot.vNF.text().trim()
        def codigo = infNFe.ide.cNF.text().trim()
        def cliente = infNFe.dest.xNome.text().trim()
        String operacao = infNFe.ide.natOp.text().trim();
        def dataEmissao = Date.parse('yyyy-MM-dd', infNFe.ide.dhEmi.text().trim() as String)
        def dataAuthStr = xml.protNFe?.infProt?.dhRecbto?.text()?.trim()
        def dataAutorizacao
        if (dataAuthStr)
            dataAutorizacao = Date.parse("yyyy-MM-dd" , dataAuthStr as String)
        if (operacao ==~ /^(?i)DEV.*$/) {
            operacao = "DEVOLUCAO"
        } else if (operacao ==~ /^(?i)BONI?.*[CÇ].*$/) {
            operacao = "BONIFICACAO"
        } else if (operacao ==~ /^(?i)VEN[DT]A.*$/)
            operacao = "VENDA"
        else if (operacao ==~ /^(?i).*TROCA.*$/)
            operacao = "TROCA"

        def clienteInstance = createCliente(infNFe)

        log.debug("Codigo           : $codigo")
        log.debug("Operacao         : $operacao")
        log.debug("Data Emissao     : ${dataEmissao.format('dd-MM-yyyy')}")
        log.debug("Data Autorizacao : ${dataAutorizacao?.format('dd-MM-yyyy')}")
        log.debug("CNPJ / CPF       : $doc")
        log.debug("Cliente          : $cliente")
        log.debug("Valor Total      : $valorTotal")

        def nf = NotaFiscal.findByCodigo(codigo as String)
        if (!nf) {
            nf = new NotaFiscal()
            nf.arquivo = file.absolutePath
            nf.arquivoNome = fileName
            nf.codigo = codigo
            nf.tipo = TipoNotaFiscal.valueOf(operacao)
            nf.dataEmissao = dataEmissao
            nf.dataAutorizacao = dataAutorizacao
            nf.doc = doc
            nf.cliente = clienteInstance
            nf.clienteNome = cliente
            nf.total = new BigDecimal(valorTotal)
            nf.save(failOnError: true)

            infNFe.det.each { det ->
                createItemNotaFiscal(det, nf)
            }
        }

    }

    def createItemNotaFiscal(det, NotaFiscal nf) {
        def prod = det.prod
        def valorProd = prod.vUnCom.text().trim()
        def quantProd = prod.qCom.text().trim()
        def unidadeProd = prod.uCom.text().trim()
        def totalProd = prod.vProd.text().trim()

        def produto = importProdutoSEFAService.createProduto(prod)
        def unidadeMedida = importProdutoSEFAService.createUnidadeMedida(unidadeProd)

        def itemNf = new ItemNotaComercial()
        itemNf.notaComercial = nf
        itemNf.produto = produto
        itemNf.preco = new BigDecimal(valorProd)
        itemNf.unidadeMedida = unidadeMedida
        itemNf.quantidade = new BigDecimal(quantProd).intValue()
        itemNf.total = new BigDecimal(totalProd)
        if (nf.tipo == TipoNotaFiscal.DEVOLUCAO) {
            itemNf.quantidade = -itemNf.quantidade
            itemNf.total = -itemNf.total
        }
        itemNf.save(failOnError: true)
    }

    Cliente createCliente(infNFe) {
        def xNome = infNFe.dest.xNome.text().trim()
        def doc = infNFe.dest.CNPJ.text() ?: infNFe.dest.CPF.text()
        doc = doc.trim()
        xNome = xNome.split('-')
        def nome
        def nomeFantasia
        if (xNome.size() == 2) {
            nomeFantasia = xNome[1]
        } else if (xNome.size() == 1) {
            nomeFantasia = xNome[0]
        }
        nome = xNome[0]

        def clienteInstance = Cliente.createCriteria().get {
            participante {
                eq('nome', nome)
            }
        }
        if (!clienteInstance) {
            def participante
            clienteInstance = new Cliente()
            if (doc.length() == 11) {
                participante = new Pessoa()
                participante.cpf = doc
            } else {
                participante = new Organizacao()
                participante.cnpj = doc
            }
            participante.nome = nome
            participante.nomeFantasia = nomeFantasia
            participante.save()
            clienteInstance.participante = participante
            clienteInstance.save()

            createEnderecoAndContato(infNFe.dest.enderDest, participante)
        }
        clienteInstance
    }

    def createEnderecoAndContato(enderDest, Participante participante) {
        def logradouro = enderDest.xLgr.text().trim()
        def numero = enderDest.nro.text().trim()
        def bairro = enderDest.xBairro.text().trim()
        def cep = enderDest.CEP.text().trim()
        def uf = enderDest.UF.text().trim()
        def cidade = enderDest.xMun.text().trim()
        def fone = enderDest.fone.text().trim()
        def pais = enderDest.xPais.text().trim()

        def cidadeInstance = Cidade.findByNome(cidade)
        if (!cidadeInstance) {
            cidadeInstance = new Cidade()
            cidadeInstance.nome = cidade
            cidadeInstance.save()
        }

        def estado = Estado.findBySigla(uf)
        if (!estado) {
            estado = new Estado()
            estado.sigla = uf
            if (uf == 'PA')
                estado.nome = "Pará"
            else
                estado.nome = uf
            estado.save()
        }

        def endereco = new Endereco()
        endereco.logradouro = logradouro
        endereco.numero = numero
        endereco.bairro = bairro
        endereco.cep = cep
        endereco.cidade = cidadeInstance
        endereco.estado = estado
        endereco.participante = participante
        endereco.save()

        def telefone = new Telefone()
        telefone.ddd = '91'
        telefone.numero = fone
        telefone.participante = participante
        telefone.save()
    }

    protected gormClean() {
        log.debug('<<<<<<<<<<<GORM CLEAN>>>>>>>>>>>')
        sessionFactory.currentSession.flush()
        sessionFactory.currentSession.clear()
    }
}
