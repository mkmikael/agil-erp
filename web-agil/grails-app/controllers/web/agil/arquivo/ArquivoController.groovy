package web.agil.arquivo

import org.springframework.web.multipart.MultipartFile

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class ArquivoController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        if (!params.sort)
            params.sort = 'dateCreated'
        if (!params.order)
            params.order = 'desc'
        respond Arquivo.list(params), model:[arquivoCount: Arquivo.count()]
    }

    def show(Arquivo arquivo) {
        respond arquivo
    }

    def create() {
        respond new Arquivo(params)
    }

    @Transactional
    def save() {
        MultipartFile arquivo = request.getFile('arquivo')
        if (arquivo.empty) {
            flash.message = [type: 'danger', text: 'O arquivo não pode estar vazio.']
            redirect(action: 'create')
            return
        }

        def arquivoInstance = new Arquivo()
        arquivoInstance.with {
            nome        = arquivo.originalFilename
            conteudo    = arquivo.bytes
            contentType = arquivo.contentType
            tamanho     = arquivo.size
        }
        arquivoInstance.save()

        redirect arquivoInstance
    }

    def edit(Arquivo arquivo) {
        respond arquivo
    }

    @Transactional
    def update(Arquivo arquivo) {
        if (arquivo == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (arquivo.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond arquivo.errors, view:'edit'
            return
        }

        arquivo.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'arquivo.label', default: 'Arquivo'), arquivo.id])
                redirect arquivo
            }
            '*'{ respond arquivo, [status: OK] }
        }
    }

    @Transactional
    def delete(Arquivo arquivo) {

        if (arquivo == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        arquivo.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'arquivo.label', default: 'Arquivo'), arquivo.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    def visualizarConteudo(Long id) {
        requestFile(id, 'inline')
    }

    def download(Long id) {
        requestFile(id, 'attachment')
    }

    protected requestFile(Long id, String contentDisposition) {
        Arquivo arquivo = Arquivo.get(id)
        response.contentType = arquivo.contentType
        response.contentLength = arquivo.tamanho
        response.setHeader('content-disposition', "$contentDisposition; filename=${arquivo.nome}")
        response.outputStream << arquivo.conteudo
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'arquivo.label', default: 'Arquivo'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
