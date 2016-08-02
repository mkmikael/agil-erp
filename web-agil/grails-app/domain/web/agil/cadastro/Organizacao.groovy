package web.agil.cadastro

class Organizacao extends Participante {

    String cnpj
    String inscricaoEstadual

    static constraints = {
        cnpj nullable: true
        inscricaoEstadual nullable: true
    }

    void setDoc(String doc) {
        this.cnpj = doc
    }

    String getDoc() {
        cnpj
    }
}
