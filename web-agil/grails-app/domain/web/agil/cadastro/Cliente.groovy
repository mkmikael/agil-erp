package web.agil.cadastro


class Cliente extends Papel {

    String vendedor

    static constraints = {
        vendedor nullable: true
    }

    def beforeInsert() {
        super.beforeInsert()
        if (!codigo)
            codigo = "C${sprintf('%05d', Papel.count() + 1)}"
    }

    String toString() {
        if (participante?.nome && participante?.nomeFantasia)
            "${participante?.nome} - ${participante?.nomeFantasia}"
        else
            super.toString()
    }
}
