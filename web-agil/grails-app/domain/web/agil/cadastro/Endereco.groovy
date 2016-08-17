package web.agil.cadastro

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

    String toString() {
        "$logradouro, $bairro - $cidade"
    }

}
