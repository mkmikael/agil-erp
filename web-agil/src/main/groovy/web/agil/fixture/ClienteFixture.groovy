package web.agil.fixture

import web.agil.cadastro.Cliente
import web.agil.cadastro.Pessoa

import static web.agil.fixture.FixtureHelper.createInstance

/**
 * Created by mikael on 11/09/16.
 */
class ClienteFixture {

    static execute() {
        Cliente.withTransaction {
            if (Cliente.count() == 0) {
                creates()
            }
        }
    }

    static creates() {
        def pessoa = createInstance(Pessoa, nome: 'OUTROS')
        createInstance(Cliente, participante: pessoa)
    }
}
