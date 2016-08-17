package web.agil.cadastro

import web.agil.util.Util

class Pessoa extends Participante {

    String cpf

    static constraints = {
        cpf nullable: true
    }

    void setDoc(String doc) {
        this.cpf = Util.onlyNumber(doc)
    }

    void setCpf(String cpf) {
        this.cpf = Util.onlyNumber(cpf)
    }

    String getDoc() {
        cpf
    }
}
