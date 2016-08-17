package web.agil.cadastro

import web.agil.cadastro.enums.StatusPapel

class PapelController {

    static allowedMethods = [ativar: 'POST', 'inativar': 'POST']

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
}
