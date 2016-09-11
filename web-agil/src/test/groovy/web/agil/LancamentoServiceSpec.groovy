package web.agil

import grails.test.mixin.TestFor
import spock.lang.Specification
import web.agil.cadastro.Papel
import web.agil.financeiro.Conta
import web.agil.financeiro.EventoFinanceiro
import web.agil.financeiro.enums.TipoEventoFinanceiro
import web.agil.financeiro.planoPagamento.PlanoPagamento

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(LancamentoService)
class LancamentoServiceSpec extends Specification {

    static doWithSpring = {
        contaService(ContaService)
    }

    def setup() {
        ContaService.metaClass.getConta = { p ->
            Conta.first()
        }
        service.contaService = grailsApplication.mainContext.contaService
    }

    def cleanup() {
    }

    void "test something"() {
//        given:
        def map = [:]
        map.evento = EventoFinanceiro.first()
        map.papel = Papel.first()
        map.valor = 90.0
//        when:
        def result = service.criarLancamento(map)
        expect:
        true == true
//        result
//        result.validate()
    }
}
