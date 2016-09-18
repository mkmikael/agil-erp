package web.agil.financeiro


import web.agil.AlertService
import web.agil.LancamentoService

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class EventoFinanceiroController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE", cancelar: "POST"]

    AlertService alertService
    LancamentoService lancamentoService

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond EventoFinanceiro.list(params), model:[eventoFinanceiroCount: EventoFinanceiro.count()]
    }

    def show(EventoFinanceiro eventoFinanceiro) {
        respond eventoFinanceiro
    }

    def create() {
        respond new EventoFinanceiro(params)
    }

    @Transactional
    def save() {
        def eventoFinanceiro = new EventoFinanceiro(params)

        def intervalos = params.list('intervalos')
        eventoFinanceiro.addToAllIntervalo(intervalos)
        def dataReferencia = Date.parse('dd/MM/yyyy', params.dataReferencia)
        lancamentoService.criarLancamentos(eventoFinanceiro, dataReferencia)

        if (eventoFinanceiro.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond eventoFinanceiro.errors, view:'create'
            return
        }

        eventoFinanceiro.save flush:true

        request.withFormat {
            form multipartForm {
                def message = message(code: 'default.created.message', args: [message(code: 'eventoFinanceiro.label', default: 'EventoFinanceiro'), eventoFinanceiro])
                alertService.success(text: message)
                redirect eventoFinanceiro
            }
            '*' { respond eventoFinanceiro, [status: CREATED] }
        }
    }

    def edit(EventoFinanceiro eventoFinanceiro) {
        respond eventoFinanceiro
    }

    @Transactional
    def update(EventoFinanceiro eventoFinanceiro) {
        if (eventoFinanceiro == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (eventoFinanceiro.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond eventoFinanceiro.errors, view:'edit'
            return
        }

        eventoFinanceiro.save flush:true

        request.withFormat {
            form multipartForm {
                def message = message(code: 'default.updated.message', args: [message(code: 'eventoFinanceiro.label', default: 'EventoFinanceiro'), eventoFinanceiro])
                alertService.success(text: message)
                redirect eventoFinanceiro
            }
            '*'{ respond eventoFinanceiro, [status: OK] }
        }
    }

    @Transactional
    def delete(EventoFinanceiro eventoFinanceiro) {

        if (eventoFinanceiro == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        eventoFinanceiro.delete flush:true

        request.withFormat {
            form multipartForm {
                def message = message(code: 'default.deleted.message', args: [message(code: 'eventoFinanceiro.label', default: 'EventoFinanceiro'), eventoFinanceiro])
                alertService.success(text: message)
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    @Transactional
    def cancelar(EventoFinanceiro evento) {
        evento.cancelar()
        redirect evento.notaComercial
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'eventoFinanceiro.label', default: 'EventoFinanceiro'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
