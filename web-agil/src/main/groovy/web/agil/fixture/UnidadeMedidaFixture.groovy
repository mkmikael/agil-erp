package web.agil.fixture

import web.agil.cadastro.UnidadeMedida

import static web.agil.fixture.FixtureHelper.checkExists
import static web.agil.fixture.FixtureHelper.createInstance

/**
 * Created by mikael on 11/09/16.
 */
class UnidadeMedidaFixture {

    static execute() {
        checkExists(UnidadeMedida) {
            creates()
        }
    }

    static creates() {
        createInstance(UnidadeMedida, nome: 'UN')
        createInstance(UnidadeMedida, nome: 'PT')
        createInstance(UnidadeMedida, nome: 'CJ')
        createInstance(UnidadeMedida, nome: 'CX')
    }
}
