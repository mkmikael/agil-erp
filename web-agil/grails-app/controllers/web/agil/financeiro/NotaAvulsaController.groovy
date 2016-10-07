package web.agil.financeiro

import grails.plugins.export.ExportService
import web.agil.LancamentoService
import web.agil.cadastro.Cliente
import web.agil.financeiro.enums.StatusEventoFinanceiro
import web.agil.financeiro.enums.TipoNotaFiscal

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

import org.hibernate.FetchMode as FM

@Transactional(readOnly = true)
class NotaAvulsaController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    LancamentoService lancamentoService
    ExportService exportService

    def index(Integer max) {
        params.max = Math.min(max ?: 40, 100)
        if (!params.sort)
            params.sort = 'dataEmissao'
        if (!params.order)
            params.order = 'desc'
        def where = {
            if (params.statusEvento) {
                evento {
                    eq('status', StatusEventoFinanceiro.valueOf(params.statusEvento))
                }
            }
        }

        def notaAvulsaList

        if (params.f) {
            params.remove('offset')
            params.remove('max')
            def fdate = new Date().format("dd-MM-yyyy HH:mm")
            log.debug("R: Gerando relatorio de notas avulsa ${fdate}")
            params.sort = 'dataEmissao'
            params.order = 'desc'
            notaAvulsaList = NotaAvulsa.createCriteria().list(params, where)
            def fields = ['codigo', 'cliente.participante.doc', 'cliente.participante.nomeFantasia', 'dataEmissao', 'total']
            def labels = [
                    'codigo': 'Codigo',
                    'cliente.participante.doc': 'CNPJ/CPF',
                    'cliente.participante.nomeFantasia': 'Cliente',
                    'dataEmissao': 'Dt Emissão',
                    'total': 'Total'
            ]
            def formatters = [
                    'cliente.participante.nomeFantasia': { NotaAvulsa n, v -> n.cliente.toString() }
            ]
            def filename = "notas avulsa ${fdate}"
            def type = params.f.toString()
            exportService.export(type, response, filename, "xls", notaAvulsaList, fields, labels, formatters, [:])
            log.debug("R: Relatorio de notas avulsa gerado com ${notaAvulsaList.size()} registros")
            return
        }

        notaAvulsaList = NotaAvulsa.createCriteria().list(params, where)
        def notaAvulsaCount = NotaAvulsa.createCriteria().count(where)
        if (!params.statusEvento) {
            def toRemove = []
            notaAvulsaList.each { n ->
                if (n.evento?.status == StatusEventoFinanceiro.CANCELADO)
                    toRemove << n
            }
            toRemove.each {
                notaAvulsaList.remove(it)
            }
            notaAvulsaCount -= toRemove.size()
        }
        respond notaAvulsaList, model: [notaAvulsaCount: notaAvulsaCount] + params
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
        def notaAvulsa
        def id = params.id
        if (id) {
            notaAvulsa = NotaAvulsa.get(id as Long)
        } else {
            notaAvulsa = new NotaAvulsa(params)
        }
        respond notaAvulsa, model: [clienteList: clienteList, oldId: id]
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
        if (notaAvulsa.tipo == TipoNotaFiscal.VENDA) {
            List intervalosIds = params.list('intervalos')
            lancamentoService.criarLancamentos(notaAvulsa, intervalosIds)
        }

        if (params.oldId) {
            def oldId = params.long('oldId')
            def notaAnterior = NotaAvulsa.get(oldId)
            if (notaAnterior.eventoFinanceiro && !notaAnterior.isCancelado()) {
                notaAnterior.cancelar()
            }
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'notaAvulsa.label', default: 'NotaAvulsa'), notaAvulsa.id])
                redirect notaAvulsa
            }
            '*' { respond notaAvulsa, [status: CREATED] }
        }

    }

    def edit(NotaAvulsa notaAvulsa) {
        redirect action: 'create', id: notaAvulsa.id
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
        def item = ItemNotaComercial.get(id)
        item.delete()
        response.status = 200
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
