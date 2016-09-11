package web.agil.financeiro

import web.agil.LancamentoService
import web.agil.financeiro.enums.StatusLancamento

class LancamentoController {

    LancamentoService lancamentoService

    def index() {
        params.max = Math.min(params.int('max') ?: 30, 100)
        if (!params.sort)
            params.sort = 'dataPrevista'
        def model = lancamentoService.lancamentoListAndCount(params)
        def lancamentoList = model.lancamentoList
        def valorTotalAberto = Lancamento.getValorTotal(StatusLancamento.ABERTO)
        def valorTotalPago   = Lancamento.getValorTotal(StatusLancamento.PAGO)
        respond lancamentoList, model: [lancamentoCount: model.lancamentoCount,
                                        valorTotalAberto: valorTotalAberto,
                                        valorTotalPago: valorTotalPago] + params
    }

    def pago(Long id) {
        updateStatus(id, StatusLancamento.PAGO)
    }

    def aberto(Long id) {
        updateStatus(id, StatusLancamento.ABERTO)
    }

    def cancelar(Lancamento lancamento) {
        lancamento.cancelar()
        redirect(action: 'index')
    }

    protected void updateStatus(Long id, StatusLancamento status) {
        def lanc = Lancamento.get(id)
        lanc.status = status
        lanc.save(flush: true)
        def valorTotalAberto = Lancamento.getValorTotal(StatusLancamento.ABERTO)
        def valorTotalPago   = Lancamento.getValorTotal(StatusLancamento.PAGO)
        render template: 'dados', model: [valorTotalAberto: valorTotalAberto, valorTotalPago: valorTotalPago]
    }

}
