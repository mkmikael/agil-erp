package web.agil.financeiro

import web.agil.cadastro.Cliente
import web.agil.cadastro.Vendedor
import web.agil.financeiro.enums.TipoNotaFiscal

class NotaComercial {

    String codigo
    Date dataEmissao
    Cliente cliente
    BigDecimal total
    TipoNotaFiscal tipo = TipoNotaFiscal.VENDA
    Vendedor vendedor

    static hasOne = [evento: EventoFinanceiro]
    static hasMany = [itens: ItemNotaComercial]
    static constraints = {
        evento nullable: true
        codigo nullable: true
        total nullable: true, scale: 6
        vendedor nullable: true
    }

    def beforeInsert() {
        if (!dataEmissao)
            dataEmissao = new Date()
    }

    EventoFinanceiro getEventoFinanceiro() {
        EventoFinanceiro.findByNotaComercial(this)
    }

    def cancelar() {
        def eventoAnterior = getEventoFinanceiro()
        eventoAnterior?.cancelar()
    }

    def isCancelado() {
      def eventoAnterior = getEventoFinanceiro()
      eventoAnterior?.isCancelado()
    }

}
