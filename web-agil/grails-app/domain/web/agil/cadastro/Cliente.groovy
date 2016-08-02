package web.agil.cadastro

class Cliente extends Papel {

    static belongsTo = [vendedor: Vendedor]
    static constraints = {
        vendedor nullable: true
    }

    def afterInsert() {
        if (!codigo)
            codigo = "C${sprintf('%05d', Papel.count())}"
    }

    String toString() {
        if (participante?.nome && participante?.nomeFantasia)
            "${participante?.nome} - ${participante?.nomeFantasia}"
        else
            super.toString()
    }
}
