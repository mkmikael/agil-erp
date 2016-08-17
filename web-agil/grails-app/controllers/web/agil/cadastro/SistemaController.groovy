package web.agil.cadastro

import grails.transaction.Transactional
import web.agil.arquivo.Arquivo
import web.agil.sistema.Config

class SistemaController {


    def importNotaFiscalService
    def importProdutoSEFAService
    def importClienteExcelService

    def index() {
        def arquivoXlsxList = Arquivo.list(sort: 'codigo', order: 'desc')
        [config: grailsApplication.config, arquivoXlsxList: arquivoXlsxList] + params
    }

    @Transactional
    def save() {
        def config
        if (params.arquivo?.nfs) {
            config = Config.findOrCreate('arquivo.nfs', params.arquivo.nfs as String)
            grailsApplication.config.arquivo.nfs = config.value
        }
        if (params.arquivo?.produtos) {
            config = Config.findOrCreate('arquivo.produtos', params.arquivo.produtos as String)
            grailsApplication.config.arquivo.produtos = config.value
        }
        redirect(action: 'index')
    }

    def processarNfs() {
        processar {
           importNotaFiscalService.execute()
        }
    }

    def processarProdutos() {
        processar {
            importProdutoSEFAService.execute()
        }
    }

    def processarPlanilhaCliente(Long arquivoId) {
        processar {
            Arquivo arquivo = Arquivo.get(arquivoId)
            if (!arquivo)
                return 'Arquivo não encontrado'
            importClienteExcelService.execute(arquivo.inputStream)
        }
    }

    protected processar(Closure closure) {
        def result
        try {
            result = closure()
            if (!result)
                result = 'Processamento realizado com sucesso.'
        } catch (Exception e) {
            result = 'Ocorreu um erro ao processar.'
            e.printStackTrace()
        }
        redirect(action: 'index', params: [resultProc: result])
    }
}
