package web.agil.financeiro.planoPagamento

import web.agil.cadastro.Administradora

class PlanoPagamento {

    Date dateCreated
    Date lastUpdated
    Boolean ativo = true

    static belongsTo = [administradora: Administradora]
    static constraints = {
        dateCreated nullable: true
        lastUpdated nullable: true
        administradora nullable: true
    }

    String getDisplay() {
        "Avista"
    }

    List<Date> getDatasPrevistas(Date dataReferencia) {
        [new Date()]
    }

    def beforeInsert() {
        if (!dateCreated)
            dateCreated = new Date()
    }

    def beforeUpdate() {
        lastUpdated = new Date()
    }

    String toString() { display }
}
