import web.agil.cadastro.Estado
import web.agil.cadastro.Pessoa
import web.agil.cadastro.Vendedor
import web.agil.sistema.Config

class BootStrap {

    def grailsApplication

    def init = { servletContext ->
        Locale.default = new Locale('pt', 'BR')
        initializeFixture()
        initConfig()
    }

    def initConfig() {
        grailsApplication.config.arquivo.nfs = Config.getVal('arquivo.nfs')
        grailsApplication.config.arquivo.produtos = Config.getVal('arquivo.produtos')
    }

    def initializeFixture() {
        if (!Estado.findBySigla('PA'))
            new Estado(sigla: 'PA', nome: 'Pará').save(flush: true)

        if (Vendedor.count() == 0) {
            def list = []
            list << [codigo: '001', nome: 'SANTOS']
            list << [codigo: '002', nome: 'GABRIEL']
            list << [codigo: '003', nome: 'THIAGO']
            list << [codigo: '004', nome: 'FELIPE']
            list.each {
                def v = new Vendedor(codigo: it.codigo)
                def p = new Pessoa(nome: it.nome)
                p.addToPapeis(v)
                p.save(flush: true)
            }
        }
    }

    def destroy = {
    }
}
