package web.agil.cadastro

import web.agil.financeiro.planoPagamento.PlanoPagamento

class Administradora extends Papel {

    static hasMany = [planosPagamento: PlanoPagamento]
    static constraints = {
    }

    static Administradora getUnica() {
        def adm = Administradora.first()
        if (!adm)
            throw RuntimeException('Deve ser criado pelo menos uma Administradora')
        adm
    }
}
