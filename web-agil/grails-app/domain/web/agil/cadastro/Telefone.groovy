package web.agil.cadastro

class Telefone {

    String ddd
    String numero

    static belongsTo = [participante: Participante]
    static constraints = {
    }

    String toString() {
        "($ddd) $numero"
    }
}
