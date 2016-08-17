package web.agil.cadastro

class Cidade {

    String nome
    Estado estado

    static constraints = {
        estado nullable: true
    }

    String toString() { "$nome - $estado.sigla" }
}
