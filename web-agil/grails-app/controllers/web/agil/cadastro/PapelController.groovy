package web.agil.cadastro

import web.agil.AlertService
import web.agil.cadastro.enums.StatusPapel
import web.agil.util.Util

class PapelController {

    static allowedMethods = [ativar: 'POST', 'inativar': 'POST', 'checkPapel': 'POST']

    AlertService alertService

    def ativar(Long id) {
        def papel = Papel.get(id)
        papel.statusPapel = StatusPapel.ATIVO
        redirect papel
    }

    def inativar(Long id) {
        def papel = Papel.get(id)
        papel.statusPapel = StatusPapel.INATIVO
        redirect papel
    }

    def checkPapel() {
        def count = Papel.createCriteria().count {
            participante {
                eq(params.docType, Util.onlyNumber(params.doc))
            } // participante
        } // papel
        if (count > 0) {
            alertService.warning(title: 'O CPF ou CNPJ já está cadastrado no sistema.')
        }
        render 'OK'
    }

}
