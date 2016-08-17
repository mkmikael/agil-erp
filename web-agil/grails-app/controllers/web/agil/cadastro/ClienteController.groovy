package web.agil.cadastro

import web.agil.ClienteService
import web.agil.ParticipanteService
import web.agil.cadastro.enums.StatusPapel

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class ClienteController {

    ParticipanteService participanteService
    ClienteService clienteService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        if (!params.sort)
            params.sort = 'participante.nome'
        if (!params.order)
            params.order = 'asc'
        if (!params.statusPapel) params.statusPapel = 'ATIVO'
        def model = clienteService.clienteListAndCount(params)
        model + params
    }

    def show(Cliente cliente) {
        respond cliente
    }

    def create() {
        respond new Cliente(params)
    }

    @Transactional
    def save() {
        def cliente = new Cliente()
        cliente.participante = new Pessoa(params.participante)
        def participante = cliente.participante
        participante.save()
        participante.addToEnderecos(params.endereco)
        participante.addToTelefones(params.telefone)
        if (cliente.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond cliente.errors, view:'create'
            return
        }

        cliente.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'cliente.label', default: 'Cliente'), cliente.id])
                redirect cliente
            }
            '*' { respond cliente, [status: CREATED] }
        }
    }

    def edit(Cliente cliente) {
        respond cliente
    }

    @Transactional
    def update() {
        def cliente = Cliente.get(params.long('id'))
        if (cliente == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        def participante = cliente.participante
        participante.properties = params.participante

        def endereco = participante.endereco ?: new Endereco(participante: participante)
        endereco.properties = params.endereco
        endereco.save()

        def telefone = participante.telefone ?: new Telefone(participante: participante)
        telefone.properties = params.telefone
        telefone.save()

        if (cliente.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond cliente.errors, view:'edit'
            return
        }

        cliente.save flush:true

        request.withFormat {
            form multipartForm {
                def text = message(code: 'default.updated.message', args: [message(code: 'cliente.label', default: 'Cliente'), cliente.id])
                flash.message = [type: 'info', text: text]
                redirect cliente
            }
            '*'{ respond cliente, [status: OK] }
        }
    }

    @Transactional
    def delete(Cliente cliente) {

        if (cliente == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        cliente.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'cliente.label', default: 'Cliente'), cliente.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'cliente.label', default: 'Cliente'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }

}
