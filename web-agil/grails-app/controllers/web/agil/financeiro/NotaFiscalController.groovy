package web.agil.financeiro

import web.agil.financeiro.enums.TipoNotaFiscal

import java.math.MathContext
import java.math.RoundingMode
import java.text.DecimalFormat

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class NotaFiscalController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        //params.max = Math.min(max ?: , 100)
        params.max = 100
        if (!params.sort) params.sort = 'dataEmissao'
        if (!params.order) params.order = 'desc'
        def df = new DecimalFormat()
        def where = {
            if (params.searchCodigo)
                like('codigo', "%${params.searchCodigo?.trim()}%")
            if (params.searchTipo)
                eq('tipo', TipoNotaFiscal.valueOf(params.searchTipo))
            if (params.searchTotal) {
                def searchTotal = df.parse(params.searchTotal)
                searchTotal = new BigDecimal(searchTotal, MathContext.DECIMAL64)
                eq('total', searchTotal)
            }
//            new BigDecimal().setScale(, RoundingMode.HALF_UP)
            if (params.searchCliente)
                ilike('clienteNome', "%${params.searchCliente}%")

            if (params.searchDtEmissaoInicio)
                ge('dataEmissao', Date.parse('dd/MM/yyyy', params.searchDtEmissaoInicio))
            if (params.searchDtEmissaoFim)
                le('dataEmissao', Date.parse('dd/MM/yyyy', params.searchDtEmissaoFim))

            if (params.searchDtAuthInicio)
                ge('dataAutorizacao', Date.parse('dd/MM/yyyy', params.searchDtAuthInicio))
            if (params.searchDtAuthFim)
                le('dataAutorizacao', Date.parse('dd/MM/yyyy', params.searchDtAuthFim))
        }
        def notaFiscalList = NotaFiscal.createCriteria().list(params) {
            where.delegate = delegate
            where()
        }
        def notaFiscalCount = NotaFiscal.createCriteria().count {
            where.delegate = delegate
            where()
        }
        respond notaFiscalList, model:[notaFiscalCount: notaFiscalCount] + params
    }

    def show(NotaFiscal notaFiscal) {
        respond notaFiscal
    }

    def create() {
        respond new NotaFiscal(params)
    }

    @Transactional
    def save(NotaFiscal notaFiscal) {
        if (notaFiscal == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (notaFiscal.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond notaFiscal.errors, view:'create'
            return
        }

        notaFiscal.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'notaFiscal.label', default: 'NotaFiscal'), notaFiscal.id])
                redirect notaFiscal
            }
            '*' { respond notaFiscal, [status: CREATED] }
        }
    }

    def edit(NotaFiscal notaFiscal) {
        respond notaFiscal
    }

    @Transactional
    def update(NotaFiscal notaFiscal) {
        if (notaFiscal == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (notaFiscal.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond notaFiscal.errors, view:'edit'
            return
        }

        notaFiscal.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'notaFiscal.label', default: 'NotaFiscal'), notaFiscal.id])
                redirect notaFiscal
            }
            '*'{ respond notaFiscal, [status: OK] }
        }
    }

    @Transactional
    def delete(NotaFiscal notaFiscal) {

        if (notaFiscal == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        notaFiscal.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'notaFiscal.label', default: 'NotaFiscal'), notaFiscal.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'notaFiscal.label', default: 'NotaFiscal'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
