package web.agil.fixture

import web.agil.cadastro.Administradora
import web.agil.financeiro.planoPagamento.ConjuntoIntervaloPgto
import web.agil.financeiro.planoPagamento.IntervaloPagamento
import web.agil.financeiro.planoPagamento.PlanoPagamento

import static web.agil.fixture.FixtureHelper.createInstance

/**
 * Created by mikael on 10/09/16.
 */
class PlanoPagamentoFixture {

    static execute() {
        PlanoPagamento.withTransaction {
            if (PlanoPagamento.count() == 0)
                creates()
        }
    }

    static creates() {
        // 3, 5, 7, 10, 15, 20
        // 3 parcelas
        def adm = Administradora.getUnica()
        createInstance(IntervaloPagamento, administradora: adm, dias: 3)
        createInstance(IntervaloPagamento, administradora: adm, dias: 5)
        createInstance(IntervaloPagamento, administradora: adm, dias: 7)
        createInstance(IntervaloPagamento, administradora: adm, dias: 10)
        createInstance(IntervaloPagamento, administradora: adm, dias: 15)
        createInstance(IntervaloPagamento, administradora: adm, dias: 20)
    }

}
