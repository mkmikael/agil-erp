package web.agil.financeiro.planoPagamento

import grails.test.mixin.TestFor
import spock.lang.Specification
import web.agil.util.Util

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(IntervaloPagamento)
class IntervaloPagamentoSpec extends Specification {

    def intervalo

    static doWithSpring = {
        calendarioService(web.anp.CalendarioService)
    }

    def setup() {
        intervalo = new IntervaloPagamento()
        intervalo.calendarioService = grailsApplication.mainContext.calendarioService
    }

    def cleanup() {
    }

    void "test dataPrevista 5 dias"() {
        given:
        intervalo.dias = 5
        def data = Date.parse('dd/MM/yyyy', '10/09/2016')
        when:
        def dataPrevista = intervalo.getDatasPrevistas(data)[0]
        then:
        Util.truncDate(dataPrevista) == Date.parse('dd/MM/yyyy', '15/09/2016')
    }

    void "test dataPrevista 7 dias"() {
        given:
        intervalo.dias = 7
        def data = Date.parse('dd/MM/yyyy', '10/09/2016')
        when:
        def dataPrevista = intervalo.getDatasPrevistas(data)[0]
        then:
        Util.truncDate(dataPrevista) == Date.parse('dd/MM/yyyy', '19/09/2016')
    }

}
