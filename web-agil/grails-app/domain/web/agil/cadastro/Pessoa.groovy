package web.agil.cadastro

class Pessoa extends Participante {

    String cpf

    static constraints = {
    }

    void setDoc(String doc) {
        this.cpf = doc
    }

    String getDoc() {
        cpf
    }
}
