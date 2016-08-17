package web.agil.cadastro

import web.agil.util.Util

class Organizacao extends Participante {

    String cnpj
    String inscricaoEstadual

    static constraints = {
        cnpj nullable: true
        inscricaoEstadual nullable: true
    }

    void setDoc(String doc) {
        this.cnpj = Util.onlyNumber(doc)
    }

    void setCnpj(String cnpj) {
        this.cnpj = Util.onlyNumber(cnpj)
    }

    String getDoc() {
        cnpj
    }
}
