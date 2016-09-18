package web.agil.cadastro

import web.agil.util.Util

class Cidade {

    String nome
    Estado estado

    void setNome(String nome) {
        this.nome = Util.removeSpecialCaracter(nome)?.toUpperCase()
    }

    static constraints = {
        estado nullable: true
    }

    String toString() { "$nome - $estado.sigla" }
}
