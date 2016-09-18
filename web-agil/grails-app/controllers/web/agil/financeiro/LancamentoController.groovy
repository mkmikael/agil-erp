package web.agil.financeiro

import org.hibernate.criterion.CriteriaSpecification

import web.agil.LancamentoService
import web.agil.financeiro.enums.*
import web.agil.financeiro.enums.StatusLancamento as SL

class LancamentoController {

    LancamentoService lancamentoService

    def index() {
        params.max = Math.min(params.int('max') ?: 30, 100)
        if (!params.sort)
            params.sort = 'codigo'

        def where = {
            papel {
                participante {
                    if (params.nome)
                        ilike('nome', "%${params.nome}%")
                }
            }
            lancamentos {
                if (params.status) {
                    def statusStr = params.list('status')
                    def status = []
                    statusStr.each {
                        if (it instanceof String) status << StatusLancamento.valueOf(it)
                        else status << it
                    }
                    'in'('status', status)
                }
                if (params.dataPrevista_inicio)
                    ge('dataPrevista', Date.parse('dd/MM/yyyy', params.dataPrevista_inicio))
                if (params.dataPrevista_fim)
                    le('dataPrevista', Date.parse('dd/MM/yyyy', params.dataPrevista_fim))
            }
        }
        def eventoList  = EventoFinanceiro.createCriteria().listDistinct(where)
        def eventoCount = EventoFinanceiro.createCriteria().count(where)
        def valorTotalAberto   = Lancamento.getValorTotal(StatusLancamento.ABERTO)
        def valorTotalAtrasado = Lancamento.getValorTotal(StatusLancamento.ATRASADO)
        def valorTotalPago     = Lancamento.getValorTotal(StatusLancamento.PAGO)
        [eventoCount: eventoCount, eventoList: eventoList,
            valorTotalAberto:   valorTotalAberto,
            valorTotalAtrasado: valorTotalAtrasado,
            valorTotalPago:     valorTotalPago] + params
    }

    def pago(Long id) {
        updateStatus(id, StatusLancamento.PAGO)
    }

    def aberto(Long id) {
        updateStatus(id, StatusLancamento.ABERTO)
    }

    def atrasado(Long id) {
        updateStatus(id, StatusLancamento.ATRASADO)
    }

    def cancelar(Lancamento lancamento) {
        lancamento.cancelar()
        redirect(action: 'index')
    }

    protected void updateStatus(Long id, StatusLancamento status) {
        def lanc = Lancamento.get(id)
        lanc.status = status
        lanc.save(flush: true)
        def valorTotalAberto   = Lancamento.getValorTotal(StatusLancamento.ABERTO)
        def valorTotalAtrasado = Lancamento.getValorTotal(StatusLancamento.ATRASADO)
        def valorTotalPago     = Lancamento.getValorTotal(StatusLancamento.PAGO)
        render template: 'dados', model: [valorTotalAberto: valorTotalAberto,
            valorTotalPago: valorTotalPago,
            valorTotalAtrasado: valorTotalAtrasado]
    }

}
