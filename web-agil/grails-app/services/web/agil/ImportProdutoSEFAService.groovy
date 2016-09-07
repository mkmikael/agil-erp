package web.agil

import grails.transaction.Transactional
import org.hibernate.SessionFactory
import web.agil.cadastro.*

@Transactional
class ImportProdutoSEFAService {

    def grailsApplication
    SessionFactory sessionFactory
    String filePath

    def execute() {
        filePath = grailsApplication.config.arquivo?.produtos
        log.debug("Init read XML files: ${filePath}")
        def xmlParser = new XmlParser()
        def fileBase = new File(filePath)
        def files = fileBase.list() as List
        def count = 0;
        files.eachWithIndex { fileName, i ->
            if (fileName.endsWith('.xml')) {
                produtoFile(xmlParser, fileName)
                count++
                if (count >= 100) {
                    gormClean()
                    count = 0
                }
            }
            log.debug("**********************************$i")
        }
        null
    }

    def produtoFile(xmlParser, fileName) {
        def file = new File("$filePath$File.separator$fileName")
        log.debug("Arquivo          : ${file.absolutePath}")
        def xml = xmlParser.parseText(new String(file.bytes, 'utf-8'))
        def prod = xml.det.prod
        createProduto(prod)
    }

    def createProduto(prod) {
        def codigoProd = prod.cProd.text().trim()
        def nomeProd = prod.xProd.text().trim()
        def valorProd = prod.vUnCom.text().trim()
        def ncmProd = prod.NCM.text().trim()
        def unidadeProd = prod.uCom.text().trim()

        log.debug "\t\t$codigoProd;$nomeProd;$valorProd"

        def unidadeMedida = createUnidadeMedida(unidadeProd as String)

        def produto = Produto.findByCodigo(codigoProd as String)
        if (!produto) {
            def nomeProdList = nomeProd.split('-')
            if (nomeProdList.size() > 1) {
                nomeProd = nomeProdList[1]
            } else {
                nomeProdList = nomeProdList[0].split(' ')
            }
            def nomeForne = nomeProdList[0]
            nomeForne = nomeForne.trim()
            nomeProd = nomeProd.trim()
            if (nomeForne == "COTTOBABY") {
                nomeForne = "COTTONBABY"
            } else if (nomeForne == "CARNE" || nomeForne == "MORTADELA") {
                nomeForne = "JBS"
            } else if (nomeForne == "TIC") {
                nomeForne = "TIC-TAC"
                nomeProd = nomeProd.replace('TAC ', '')
            }

            def fornecedor = Fornecedor.createCriteria().get {
                participante {
                    eq('nome', nomeForne)
                }
            }
            if (!fornecedor) {
                def org = new Organizacao()
                org.nome = nomeForne
                org.save()
                fornecedor = new Fornecedor()
                fornecedor.participante = org
                fornecedor.codigo = "F${Fornecedor.count() + 1}"
                fornecedor.save()
            }

            produto = new Produto()
            produto.ncm = ncmProd
            produto.codigo = codigoProd
            produto.nome = nomeProd
            produto.fornecedor = fornecedor
//            produto.addToFornecedores(fornecedor)
            produto.save(failOnError: true)

//            def precoComp = new PrecoComponente()
//            precoComp.unidadeMedida = unidadeMedida
//            precoComp.preco = new BigDecimal(valorProd)
//            precoComp.produto = produto
//            precoComp.save(failOnError: true)
        } // !produto
        produto
    }

    def createUnidadeMedida(String unidadeProd) {
        if (unidadeProd ==~ /^(?i)UN.*$/)
            unidadeProd = "UN"
        else if (unidadeProd ==~ /^(?i)CX.*$/)
            unidadeProd = "CX"
        else if (unidadeProd ==~ /^(?i)PCT.*$/)
            unidadeProd = "PCT"
        else if (unidadeProd ==~ /^(?i)PT.*$/)
            unidadeProd = "PT"
        else if (unidadeProd ==~ /^(?i)DP.*$/)
            unidadeProd = "DP"

        def unidadeMedida = UnidadeMedida.findByNome(unidadeProd)
        if (!unidadeMedida) {
            unidadeMedida = new UnidadeMedida()
            unidadeMedida.nome = unidadeProd
            unidadeMedida.save(failOnError: true)
        }
        unidadeMedida
    }

    protected gormClean() {
        log.debug('<<<<<<<<<<<GORM CLEAN>>>>>>>>>>>')
        sessionFactory.currentSession.flush()
        sessionFactory.currentSession.clear()
    }

}
