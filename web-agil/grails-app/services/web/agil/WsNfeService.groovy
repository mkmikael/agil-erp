package web.agil

import com.fincatto.nfe310.NFeConfig
import com.fincatto.nfe310.classes.NFAmbiente
import com.fincatto.nfe310.classes.NFEndereco
import com.fincatto.nfe310.classes.NFFinalidade
import com.fincatto.nfe310.classes.NFFormaPagamentoPrazo
import com.fincatto.nfe310.classes.NFModalidadeFrete
import com.fincatto.nfe310.classes.NFModelo
import com.fincatto.nfe310.classes.NFNotaInfoImpostoTributacaoICMS
import com.fincatto.nfe310.classes.NFNotaInfoItemImpostoICMSModalidadeBaseCalculo
import com.fincatto.nfe310.classes.NFNotaInfoSituacaoTributariaCOFINS
import com.fincatto.nfe310.classes.NFOrigem
import com.fincatto.nfe310.classes.NFProcessoEmissor
import com.fincatto.nfe310.classes.NFProdutoCompoeValorNota
import com.fincatto.nfe310.classes.NFRegimeTributario
import com.fincatto.nfe310.classes.NFTipo
import com.fincatto.nfe310.classes.NFTipoEmissao
import com.fincatto.nfe310.classes.NFTipoImpressao
import com.fincatto.nfe310.classes.NFUnidadeFederativa
import com.fincatto.nfe310.classes.evento.NFEnviaEventoRetorno
import com.fincatto.nfe310.classes.lote.envio.NFLoteEnvio
import com.fincatto.nfe310.classes.lote.envio.NFLoteEnvioRetorno
import com.fincatto.nfe310.classes.lote.envio.NFLoteIndicadorProcessamento
import com.fincatto.nfe310.classes.nota.NFIdentificadorLocalDestinoOperacao
import com.fincatto.nfe310.classes.nota.NFIndicadorIEDestinatario
import com.fincatto.nfe310.classes.nota.NFIndicadorPresencaComprador
import com.fincatto.nfe310.classes.nota.NFNota
import com.fincatto.nfe310.classes.nota.NFNotaInfo
import com.fincatto.nfe310.classes.nota.NFNotaInfoAvulsa
import com.fincatto.nfe310.classes.nota.NFNotaInfoDestinatario
import com.fincatto.nfe310.classes.nota.NFNotaInfoEmitente
import com.fincatto.nfe310.classes.nota.NFNotaInfoICMSTotal
import com.fincatto.nfe310.classes.nota.NFNotaInfoISSQNTotal
import com.fincatto.nfe310.classes.nota.NFNotaInfoIdentificacao
import com.fincatto.nfe310.classes.nota.NFNotaInfoInformacoesAdicionais
import com.fincatto.nfe310.classes.nota.NFNotaInfoItem
import com.fincatto.nfe310.classes.nota.NFNotaInfoItemImposto
import com.fincatto.nfe310.classes.nota.NFNotaInfoItemImpostoCOFINS
import com.fincatto.nfe310.classes.nota.NFNotaInfoItemImpostoCOFINSAliquota
import com.fincatto.nfe310.classes.nota.NFNotaInfoItemImpostoICMS
import com.fincatto.nfe310.classes.nota.NFNotaInfoItemImpostoICMS00
import com.fincatto.nfe310.classes.nota.NFNotaInfoItemProduto
import com.fincatto.nfe310.classes.nota.NFNotaInfoRegimeEspecialTributacao
import com.fincatto.nfe310.classes.nota.NFNotaInfoRetencoesTributos
import com.fincatto.nfe310.classes.nota.NFNotaInfoTotal
import com.fincatto.nfe310.classes.nota.NFNotaInfoTransporte
import com.fincatto.nfe310.classes.nota.NFOperacaoConsumidorFinal
import com.fincatto.nfe310.classes.nota.assinatura.NFCanonicalizationMethod
import com.fincatto.nfe310.classes.nota.assinatura.NFKeyInfo
import com.fincatto.nfe310.classes.nota.assinatura.NFSignature
import com.fincatto.nfe310.classes.nota.assinatura.NFSignatureMethod
import com.fincatto.nfe310.classes.nota.assinatura.NFSignedInfo
import com.fincatto.nfe310.classes.statusservico.consulta.NFStatusServicoConsultaRetorno
import com.fincatto.nfe310.utils.NFGeraCadeiaCertificados
import com.fincatto.nfe310.webservices.WSFacade
import com.fincatto.nfe310.webservices.WSLoteEnvio
import grails.transaction.Transactional
import org.apache.commons.io.FileUtils
import org.joda.time.DateTime
import org.joda.time.LocalDate
import web.agil.nfe.NFeConfigImpl

@Transactional
class WsNfeService {

    def getPublicKey() {
        FileUtils.writeByteArrayToFile(new File("/home/mikael/Documentos/producao.cacerts"),
                NFGeraCadeiaCertificados.geraCadeiaCertificados(NFAmbiente.PRODUCAO, "nfeproducao"));
        FileUtils.writeByteArrayToFile(new File("/home/mikael/Documentos//homologacao.cacerts"),
                NFGeraCadeiaCertificados.geraCadeiaCertificados(NFAmbiente.HOMOLOGACAO, "nfehomologacao"));
    }

    def checkStatusWebService() {
        def config = new NFeConfigImpl()
        NFStatusServicoConsultaRetorno retorno = new WSFacade(config)
                .consultaStatus(NFUnidadeFederativa.SC, NFModelo.NFE);
        log.debug "Status: $retorno.status"
        log.debug "Motivo: $retorno.motivo"
    }

    def enviarLote() {
        def aliquota = new NFNotaInfoItemImpostoCOFINSAliquota()
        aliquota.situacaoTributaria = NFNotaInfoSituacaoTributariaCOFINS.OPERACAO_TRIBUTAVEL_CUMULATIVO_NAO_CUMULATIVO
        aliquota.valor = 10
        aliquota.valorBaseCalulo = 10
        aliquota.percentualAliquota = 0

        def cofins = new NFNotaInfoItemImpostoCOFINS()
        cofins.aliquota = aliquota

        def icms00 = new NFNotaInfoItemImpostoICMS00()
        icms00.origem = NFOrigem.NACIONAL
        icms00.situacaoTributaria = NFNotaInfoImpostoTributacaoICMS.TRIBUTACAO_INTEGRALMENTE
        icms00.modalidadeBaseCalculo = NFNotaInfoItemImpostoICMSModalidadeBaseCalculo.VALOR_OPERACAO
        icms00.valorBaseCalculo = 10
        icms00.valorTributo = 0
        icms00.percentualAliquota = 0
        def icms = new NFNotaInfoItemImpostoICMS()
        icms.icms00 = icms00

        def imposto = new NFNotaInfoItemImposto()
        imposto.cofins = cofins
        imposto.icms = icms

        def produto = new NFNotaInfoItemProduto(compoeValorNota: NFProdutoCompoeValorNota.SIM)
        produto.with {
            cfop = "5102"
            codigo = "001"
            codigoDeBarras = ""
            codigoDeBarrasTributavel = ""
            descricao = "BONITOL EXTRA FORTE"
            ncm = "39174090"
            unidadeComercial = "UN"
            unidadeTributavel = "UN"
            quantidadeComercial = 1
            quantidadeTributavel = 1
            valorUnitario = 10
            valorUnitarioTributavel = 10
            valorTotalBruto = 10
        }

        def item = new NFNotaInfoItem()
        item.numeroItem = 1
        item.produto = produto
        item.imposto = imposto

        def transporte = new NFNotaInfoTransporte()
        transporte.modalidadeFrete = NFModalidadeFrete.SEM_FRETE

        def addInfos = new NFNotaInfoInformacoesAdicionais()
        addInfos.informacoesAdicionaisInteresseFisco = "LOL"

        def enderecoDest = new NFEndereco()
        enderecoDest.with {
            logradouro = "RUA SANTO ANTONIO"
            bairro = "CENTRO"
            numero = "507"
            codigoMunicipio = "0000001"
            descricaoMunicipio = "Barcarena"
            uf = NFUnidadeFederativa.PA
            cep = "68445000"
        }
        def destinatario = new NFNotaInfoDestinatario()
        destinatario.with {
            cpf = "01759324256"
            razaoSocial = "MIKAEL LIMA"
            indicadorIEDestinatario = NFIndicadorIEDestinatario.NAO_CONTRIBUINTE
            endereco = enderecoDest
        }

        def enderecoEmitente = new NFEndereco()
        enderecoEmitente.with {
            logradouro = "RUA SANTO ANTONIO"
            bairro = "CENTRO"
            numero = "507"
            codigoMunicipio = "0000001"
            descricaoMunicipio = "Barcarena"
            uf = NFUnidadeFederativa.PA
            cep = "68445000"
        }
        def emitente = new NFNotaInfoEmitente()
        emitente.with {
            cnpj = "22132033000128"
            razaoSocial = "AGIL DISTRIBUIÇÕES LTDA"
            nomeFantasia = "AGIL DISTRIBUIÇÕES"
            inscricaoEstadual = "154811955"
            regimeTributario = NFRegimeTributario.SIMPLES_NACIONAL
            endereco = enderecoEmitente
        }

        def retencoesTributos = new NFNotaInfoRetencoesTributos()
        def icmsTotal = new NFNotaInfoICMSTotal()
        icmsTotal.with {
            baseCalculoICMS = 10
            baseCalculoICMSST = 10
            valorTotalICMS = 0
            valorTotalICMSST = 0
            valorICMSDesonerado = 0
            valorTotalDosProdutosServicos = 10
            valorTotalFrete = 0
            valorTotalSeguro = 0
            valorTotalDesconto = 0
            valorTotalII = 0
            valorTotalIPI = 0
            valorPIS = 0
            valorCOFINS = 0
            outrasDespesasAcessorias = 0
            valorTotalNFe = 10
        }
        def issqnTotal = new NFNotaInfoISSQNTotal()
        issqnTotal.with {
            dataPrestacaoServico = LocalDate.now()
            baseCalculoISS = 10
            tributacao = NFNotaInfoRegimeEspecialTributacao.MICROEMPRESA_MUNICIPAL
            valorCOFINSsobreServicos = 0
            valorDeducao = 0
            valorOutros = 0
            valorPISsobreServicos = 0
            valorTotalDescontoCondicionado = 0
            valorTotalDescontoIncondicionado = 0
            valorTotalISS = 0
            valorTotalRetencaoISS = 0
            valorTotalServicosSobNaoIncidenciaNaoTributadosICMS = 0
        }

        def total = new NFNotaInfoTotal()
        total.icmsTotal = icmsTotal
        total.issqnTotal = issqnTotal
        total.retencoesTributos = retencoesTributos

        def identificacao = new NFNotaInfoIdentificacao()
        identificacao.with {
            versaoEmissor = "1"
            programaEmissor = NFProcessoEmissor.AVULSA_CONTRIBUINTE_COM_CERTIFICADO_DIGITAL_FISCO
            indicadorPresencaComprador = NFIndicadorPresencaComprador.NAO_APLICA
            codigoRandomico = "00000001"
            modelo = NFModelo.NFE
            ambiente = NFAmbiente.HOMOLOGACAO
            serie = "101"
            numeroNota = "100000001"
            dataHoraEmissao = DateTime.now()
            tipo = NFTipo.SAIDA
            formaPagamento = NFFormaPagamentoPrazo.A_VISTA
            tipoEmissao = NFTipoEmissao.EMISSAO_NORMAL
            finalidade = NFFinalidade.NORMAL
            tipoImpressao = NFTipoImpressao.DANFE_NORMAL_RETRATO
            operacaoConsumidorFinal = NFOperacaoConsumidorFinal.SIM
            identificadorLocalDestinoOperacao = NFIdentificadorLocalDestinoOperacao.OPERACAO_INTERNA
            naturezaOperacao = "VENDA"
            uf = NFUnidadeFederativa.PA
            codigoMunicipio = "0000001"
        }

        def info = new NFNotaInfo()
        info.versao = new BigDecimal(NFeConfig.VERSAO_NFE)
        info.informacoesAdicionais = addInfos
        info.transporte = transporte
        info.destinatario = destinatario
        info.emitente = emitente
        info.total = total
        info.identificacao = identificacao
        info.itens = [item]

        def signedInfo = new NFSignedInfo()
//        signedInfo.signatureMethod = new NFSignatureMethod(algorithm: "http://www.w3.org/2000/09/xmldsig#rsa-sha1")
//        signedInfo.canonicalizationMethod = new NFCanonicalizationMethod(algorithm: "http://www.w3.org/TR/2001/REC-xml-c14n-20010315")
        def keyInfo = new NFKeyInfo()
        def assinatura = new NFSignature()
        assinatura.signedInfo = signedInfo
        assinatura.keyInfo = keyInfo
        assinatura.signatureValue = ""

        def nota = new NFNota()
        nota.info = info
//        nota.assinatura = assinatura

        def lote = new NFLoteEnvio()
        lote.idLote = "1"
        lote.versao = NFeConfig.VERSAO_NFE
        lote.indicadorProcessamento = NFLoteIndicadorProcessamento.PROCESSAMENTO_ASSINCRONO
        lote.notas = [nota]

        def config = new NFeConfigImpl()
        def wsFacade = new WSFacade(config)
        def retorno = wsFacade.enviaLote(lote);
        log.debug(retorno)
    }

    def corigirNota() {
        def config = new NFeConfigImpl()
        final NFEnviaEventoRetorno retorno = new WSFacade(config).corrigeNota(chaveDeAcessoDaNota, textoCorrecao, sequencialEventoDaNota);
    }

    def cancelarsNota() {
        def config = new NFeConfigImpl()
        final NFEnviaEventoRetorno retorno = new WSFacade(config).cancelaNota(chaveDeAcessoDaNota, protocoloDaNota, motivoCancelaamento);
    }
}
