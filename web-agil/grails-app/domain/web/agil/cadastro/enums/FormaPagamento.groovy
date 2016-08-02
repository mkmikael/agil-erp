package web.agil.cadastro.enums

/**
 * Created by mkmik on 23/07/2016.
 */
enum FormaPagamento {
    AVISTA('Avista'), A_PRAZO('A Prazo')

    String nome

    FormaPagamento(String nome) {
        this.nome = nome
    }
}