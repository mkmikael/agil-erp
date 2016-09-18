package web.agil.cadastro

import web.agil.util.Util
import web.agil.cadastro.enums.TipoEndereco

class Endereco {

    String cep
    String numero
    String logradouro
    String complemento
    String bairro
    Cidade cidade
    TipoEndereco tipo = TipoEndereco.COMERCIAL

    static belongsTo = [participante: Participante]
    static constraints = {
        cep nullable: true
        numero nullable: true
        complemento nullable: true
    }

    void setComplemento(String complemento) {
        this.complemento = Util.removeSpecialCaracter(complemento)?.toUpperCase()
    }

    void setLogradouro(String logradouro) {
        this.logradouro = Util.removeSpecialCaracter(logradouro)?.toUpperCase()
    }

    void setBairro(String bairro) {
        this.bairro = Util.removeSpecialCaracter(bairro)?.toUpperCase()
    }

    String toString() {
        "$logradouro, $bairro - $cidade"
    }

}
