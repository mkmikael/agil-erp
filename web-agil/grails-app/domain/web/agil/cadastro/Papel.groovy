package web.agil.cadastro

import web.agil.cadastro.enums.StatusPapel

class Papel {

    Date dateCreated
    Date lastUpdated
    String codigo
    StatusPapel statusPapel = StatusPapel.ATIVO

    static belongsTo = [participante: Participante]
    static constraints = {
        dateCreated nullable: true
        lastUpdated nullable: true
        codigo nullable: true
    }

    String toString() {
        participante?.nome ?: participante?.nomeFantasia ?: super.toString()
    }

    def beforeInsert() {
        if (!dateCreated)
            dateCreated = new Date()
        if (!codigo)
            codigo = "${sprintf('%05d', Papel.count() + 1)}"
    }

    def beforeUpdate() {
        if (!lastUpdated)
            lastUpdated = new Date()
    }
}
