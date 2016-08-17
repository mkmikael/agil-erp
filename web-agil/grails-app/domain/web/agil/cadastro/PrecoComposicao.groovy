package web.agil.cadastro

class PrecoComposicao {

    Date dateCreated

    static hasMany = [precos: PrecoComponente]
    static belongsTo = [produto: Produto]
    static constraints = {
        dateCreated nullable: true
    }

    def beforeInsert() {
        if (!dateCreated) dateCreated = new Date()
    }
}
