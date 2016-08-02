package web.agil.cadastro

import web.agil.cadastro.enums.TipoEndereco

class Endereco {

    String cep
    String numero
    String logradouro
    String complemento
    String bairro
    Cidade cidade
    Estado estado
    TipoEndereco tipo = TipoEndereco.COMERCIAL

    static belongsTo = [participante: Participante]
    static constraints = {
        tipo nullable: true
        complemento nullable: true
    }
}
