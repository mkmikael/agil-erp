package web.agil.sistema

class Config {

    String nome
    String value

    static constraints = {
    }

    static Config findOrCreate(String nome, String value) {
        def config = Config.findByNome('arquivo.nfs')
        if (!config)
            config = new Config(nome: 'arquivo.nfs')
        config.value = value
        config.save(flush: true)
    }

    static String getVal(String nome) {
        def config = Config.findByNome('arquivo.nfs')
        config?.value
    }
}
