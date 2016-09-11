package web.agil.fixture

import web.agil.cadastro.Administradora
import web.agil.cadastro.Organizacao

import static web.agil.fixture.FixtureHelper.createInstance

/**
 * Created by mikael on 10/09/16.
 */
class AdministradoraFixture {

    static execute() {
        Administradora.withTransaction {
            if (Administradora.count() == 0)
                creates()
        }
    }

    static creates() {
        def org = createInstance(Organizacao, nome: 'Adam Sasaki dos Santos', nomeFantasia: 'Ágil Distribuições')
        def adm = createInstance(Administradora, participante: org)
    }

}
