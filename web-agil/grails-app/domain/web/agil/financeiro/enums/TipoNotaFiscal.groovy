package web.agil.financeiro.enums

/**
 * Created by mkmik on 26/06/2016.
 */
enum TipoNotaFiscal {
    DEVOLUCAO('Devolução'), BONIFICACAO('Bonificação'), VENDA('Venda'), TROCA('Troca')

    String nome

    TipoNotaFiscal(String nome) {
        this.nome = nome
    }

}