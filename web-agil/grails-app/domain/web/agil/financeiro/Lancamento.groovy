package web.agil.financeiro

import web.agil.financeiro.enums.StatusLancamento
import web.agil.financeiro.enums.TipoLancamento

class Lancamento {

    Date dateCreated
    Date lastUpdated
    Conta conta
    Date dataOriginal
    Date dataPrevista
    Date dataEfetivacao
    BigDecimal valor
    TipoLancamento tipo = TipoLancamento.RECEBER
    StatusLancamento status = StatusLancamento.ABERTO

    static belongsTo = [evento: EventoFinanceiro]
    static constraints = {
        dateCreated     nullable: true
        lastUpdated     nullable: true
        dataEfetivacao  nullable: true
        tipo            nullable: true
        valor           scale: 6
    }

    def beforeInsert() {
        if (!dateCreated)
            dateCreated = new Date()
    }

    def beforeUpdate() {
        lastUpdated = new Date()
    }

    def copy() {
        def lanc = new Lancamento()
        lanc.conta = conta
        lanc.dataOriginal = dataOriginal
        lanc.dataPrevista = dataPrevista
        lanc.dataEfetivacao = dataEfetivacao
        lanc.evento = evento
        lanc.status = status
        lanc.tipo = tipo
        lanc.valor = valor
        lanc
    }

    def cancelar() {
        status = StatusLancamento.CANCELADO
        def lanc = copy()
        lanc.status = StatusLancamento.PAGO
        lanc.tipo = TipoLancamento.CANCELAMENTO
        lanc.valor = -lanc.valor
        lanc.save(failOnError: true)
    }

    static BigDecimal getValorTotal(StatusLancamento status) {
        Lancamento.createCriteria().get {
            projections {
                sum 'valor'
            }
            ne('tipo', TipoLancamento.CANCELAMENTO)
            'in'('status', status)
        }
    }

    String toString() {
        def dtPrevista = dataPrevista?.format('dd/MM/yyyy')
        def dtCreated = dateCreated?.format('dd/MM/yyyy')
        "Lancamento #$id contaId: ${conta?.id} valor: $valor dataPrevista: ${dtPrevista} dateCreated: $dtCreated"
    }

}
