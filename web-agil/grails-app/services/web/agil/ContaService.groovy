package web.agil

import grails.transaction.Transactional
import web.agil.cadastro.Administradora
import web.agil.cadastro.Papel
import web.agil.financeiro.Conta


@Transactional
class ContaService {

    Conta getConta(Papel papel) {
        def admin = Administradora.getUnica()
        def conta = Conta.createCriteria().get {
            eq('dono', admin)
            eq('papel', papel)
        }
        if (!conta) {
            conta = new Conta(dono: admin, papel: papel)
            conta.save(failOnError: true)
        }
        conta
    }

}
