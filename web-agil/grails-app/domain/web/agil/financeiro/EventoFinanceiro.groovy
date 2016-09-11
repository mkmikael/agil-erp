package web.agil.financeiro

import web.agil.cadastro.Papel
import web.agil.financeiro.enums.StatusEventoFinanceiro
import web.agil.financeiro.enums.TipoEventoFinanceiro
import web.agil.financeiro.planoPagamento.ConjuntoIntervaloPgto
import web.agil.financeiro.planoPagamento.IntervaloPagamento
import web.agil.financeiro.planoPagamento.PlanoPagamento

class EventoFinanceiro {

    Date dateCreated
    Date lastUpdated
    String codigo
    Papel papel
    PlanoPagamento planoPagamento
    TipoEventoFinanceiro tipo
    StatusEventoFinanceiro status = StatusEventoFinanceiro.BAIXADO
    NotaComercial notaComercial
    BigDecimal valor

    static constraints = {
        codigo        nullable: true
        papel         nullable: true
        dateCreated   nullable: true
        lastUpdated   nullable: true
        notaComercial nullable: true
        valor scale: 6
    }

    def beforeInsert() {
        if (!dateCreated)
            dateCreated = new Date()
        if (!codigo)
            codigo = sprintf('%08d', this.count() + 1)
    }

    def beforeUpdate() {
        lastUpdated = new Date()
    }

    ConjuntoIntervaloPgto addToIntervalo(IntervaloPagamento intervalo) {
        ConjuntoIntervaloPgto conjunto = planoPagamento
        if (!conjunto) {
            conjunto = new ConjuntoIntervaloPgto()
            conjunto.save(failOnError: true)
            planoPagamento = conjunto
        }
        conjunto.addToIntervalos(intervalo)
        conjunto
    }

    void addAllToIntervalo(List ids) {
        ids.each {
            def id = (it as Long)
            def intervalo = IntervaloPagamento.get(id)
            addToIntervalo(intervalo)
        }
    }

    def cancelar() {
        if (status != StatusEventoFinanceiro.CANCELADO) {
            status = StatusEventoFinanceiro.CANCELADO
            def lancs = Lancamento.createCriteria().list {
                eq('evento', this)
            }
            lancs.each { Lancamento l ->
                l.cancelar()
            }
        } else
            throw new RuntimeException('O evento já esta cancelado.')
    }

    List<Lancamento> getLancamentos() {
        Lancamento.withCriteria { eq('evento', this) }
    }

}
