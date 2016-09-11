package web.agil.financeiro.planoPagamento

import web.agil.cadastro.Administradora

class PlanoPagamento {

    Date dateCreated
    Date lastUpdated
    String display
    Boolean ativo = true

    static transients = ['display']
    static belongsTo = [administradora: Administradora]
    static constraints = {
        dateCreated nullable: true
        lastUpdated nullable: true
        administradora nullable: true
    }

    List<Date> getDatasPrevistas(Date dataReferencia) {}

    def beforeInsert() {
        if (!dateCreated)
            dateCreated = new Date()
    }

    def beforeUpdate() {
        lastUpdated = new Date()
    }

    String toString() { display }
}
