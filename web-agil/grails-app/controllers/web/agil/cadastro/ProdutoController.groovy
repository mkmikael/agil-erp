package web.agil.cadastro

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class ProdutoController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Produto.list(params), model:[produtoCount: Produto.count()]
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
                flash.message = message(code: 'default.created.message', args: [message(code: 'produto.label', default: 'Produto'), produto.id])
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
                flash.message = message(code: 'default.updated.message', args: [message(code: 'produto.label', default: 'Produto'), produto.id])
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
