package web.agil.cadastro

import web.agil.cadastro.enums.StatusPapel
import web.agil.financeiro.enums.StatusFinanceiro
import web.agil.financeiro.planoPagamento.PlanoPagamento

class Papel {

    Date dateCreated
    Date lastUpdated
    String codigo
    StatusPapel statusPapel = StatusPapel.ATIVO
    StatusFinanceiro statusFinanceiro = StatusFinanceiro.EM_DIA
    PlanoPagamento planoPagamento

    static belongsTo = [participante: Participante]
    static constraints = {
        statusFinanceiro nullable: true
        dateCreated      nullable: true
        lastUpdated      nullable: true
        codigo           nullable: true
        planoPagamento   nullable: true
    }

    String toString() {
        participante ?: super.toString()
    }

    def beforeInsert() {
        if (!dateCreated)
            dateCreated = new Date()
        if (!codigo)
            codigo = "${sprintf('%05d', Papel.count() + 1)}"
    }

    def beforeUpdate() {
        lastUpdated = new Date()
    }
}
