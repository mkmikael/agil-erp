package web.agil.arquivo

class Arquivo {

    Date dateCreated
    Date lastUpdated
    String contentType
    String codigo
    String nome
    String caminho
    Long tamanho
    byte[] conteudo

    static transients = ['text']
    static constraints = {
        dateCreated nullable: true
        lastUpdated nullable: true
        codigo      nullable: true
        caminho     nullable: true
        maxSize: 1024 * 1024 * 5
    }

    void setText(String text) {
        this.conteudo = text.bytes
    }

    String getText() {
        new String(conteudo, 'utf-8')
    }

    String getText(String charset) {
        new String(conteudo, charset)
    }

    InputStream getInputStream() {
        new ByteArrayInputStream(conteudo)
    }

    def beforeInsert() {
        if (!codigo)
            codigo = "${sprintf('%05d', Arquivo.count() + 1)}"
        if (!dateCreated)
            dateCreated = new Date()
    }

    def beforeUpdate() {
        lastUpdated = new Date()
    }

}
