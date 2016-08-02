package web.agil.cadastro

class SistemaController {


    def importNotaFiscalService
    def importProdutoSEFAService

    def index() {
        [config: grailsApplication.config] + params
    }

    def save() {
        if (params.arquivo?.nfs)
            grailsApplication.config.arquivo.nfs = params.arquivo.nfs
        if (params.arquivo?.produtos)
            grailsApplication.config.arquivo.produtos = params.arquivo.produtos
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

    protected processar(Closure closure) {
        def result
        try {
            closure()
            result = 'Processamento realizado com sucesso.'
        } catch (Exception e) {
            result = 'Ocorreu um erro ao processar.'
            e.printStackTrace()
        }
        redirect(action: 'index', params: [resultProc: result])
    }
}
