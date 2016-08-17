package web.agil.cadastro

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class ProdutoController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        if (!params.sort)
            params.sort = 'nome'
        if (!params.order)
            params.order = 'asc'
        def criteria = {
            if (params.id)
                eq('id', params.id as Long)
            if (params.nome)
                ilike('nome', "%${params.nome}%")
            if (params.codigo)
                ilike('codigo', "%${params.codigo}%")
            if (params.ncm)
                ilike('ncm', "%${params.ncm}%")

            if (params.fornecedor) {
                fornecedor {
                    eq('id', params.fornecedor as Long)
                }
            }

        }
        def sort = params.remove('sort')
        def _order = params.remove('order')
        def produtoList = Produto.createCriteria().list(params) {
            criteria.delegate = delegate
            criteria()
            order(sort, _order)
            order 'id'
        }
        def produtoCount = Produto.createCriteria().count(criteria)
        def fornecedorList = Fornecedor.list(sort: 'participante.nome')
        [produtoList: produtoList, produtoCount: produtoCount, fornecedorList: fornecedorList] + params
    }

    def show(Produto produto) {
        respond produto
    }

    def create() {
        respond new Produto(params)
    }

    @Transactional
    def save(Produto produto) {
        if (produto == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (produto.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond produto.errors, view:'create'
            return
        }

        produto.save flush:true

        request.withFormat {
            form multipartForm {
                def text = message(code: 'default.created.message', args: [message(code: 'produto.label', default: 'Produto'), produto.id])
                flash.message = [type: 'info', text: text]
                redirect produto
            }
            '*' { respond produto, [status: CREATED] }
        }
    }

    def edit(Produto produto) {
        respond produto
    }

    @Transactional
    def update(Produto produto) {
        if (produto == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (produto.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond produto.errors, view:'edit'
            return
        }

        produto.save flush:true

        request.withFormat {
            form multipartForm {
                def text = message(code: 'default.updated.message', args: [message(code: 'produto.label', default: 'Produto'), produto.id])
                flash.message = [type: 'info', text: text]
                redirect produto
            }
            '*'{ respond produto, [status: OK] }
        }
    }

    @Transactional
    def delete(Produto produto) {

        if (produto == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        produto.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'produto.label', default: 'Produto'), produto.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'produto.label', default: 'Produto'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
