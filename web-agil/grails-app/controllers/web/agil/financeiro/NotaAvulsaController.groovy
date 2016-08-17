package web.agil.financeiro

import web.agil.cadastro.Cliente

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class NotaAvulsaController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 40, 100)
        if (!params.sort)
            params.sort = 'dataEmissao'
        if (!params.order)
            params.order = 'desc'
        respond NotaAvulsa.list(params), model:[notaAvulsaCount: NotaAvulsa.count()]
    }

    def show(NotaAvulsa notaAvulsa) {
        respond notaAvulsa
    }

    def create() {
        def clienteList = Cliente.withCriteria {
            participante {
                order 'nomeFantasia'
            }
        }
        respond new NotaAvulsa(params), model: [clienteList: clienteList]
    }

    @Transactional
    def save(NotaAvulsa notaAvulsa) {
        if (notaAvulsa == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (notaAvulsa.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond notaAvulsa.errors, view:'create'
            return
        }
        notaAvulsa.calcularTotal()
        notaAvulsa.save failOnSave:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'notaAvulsa.label', default: 'NotaAvulsa'), notaAvulsa.id])
                redirect notaAvulsa
            }
            '*' { respond notaAvulsa, [status: CREATED] }
        }
    }

    def edit(NotaAvulsa notaAvulsa) {
        def clienteList = Cliente.withCriteria {
            participante {
                order 'nomeFantasia'
            }
        }
        respond notaAvulsa, model: [clienteList: clienteList]
    }

    @Transactional
    def update(NotaAvulsa notaAvulsa) {
        if (notaAvulsa == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (notaAvulsa.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond notaAvulsa.errors, view:'edit'
            return
        }

        notaAvulsa.calcularTotal()
        notaAvulsa.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'notaAvulsa.label', default: 'NotaAvulsa'), notaAvulsa.id])
                redirect notaAvulsa
            }
            '*'{ respond notaAvulsa, [status: OK] }
        }
    }

    @Transactional
    def delete(NotaAvulsa notaAvulsa) {

        if (notaAvulsa == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        notaAvulsa.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'notaAvulsa.label', default: 'NotaAvulsa'), notaAvulsa.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    def emissao(NotaAvulsa notaAvulsa) {
        respond notaAvulsa//[notaAvulsa: notaAvulsa, itens: itens]
    }

    def itemLinha() { render template: 'itemLinha', model: [i: params.index] }

    @Transactional
    def removerItem(Long id) {
        def item = ItemNotaAvulsa.get(id)
        item.delete()
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'notaAvulsa.label', default: 'NotaAvulsa'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
