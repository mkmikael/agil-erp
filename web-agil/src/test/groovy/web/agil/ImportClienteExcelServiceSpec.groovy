package web.agil

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import spock.lang.Specification
import web.agil.cadastro.Cliente
import web.agil.cadastro.Fornecedor
import web.agil.cadastro.Organizacao
import web.agil.cadastro.Pessoa
import web.agil.cadastro.Vendedor
import web.agil.financeiro.NotaAvulsa
import web.agil.financeiro.NotaFiscal

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(ImportClienteExcelService)
//@Mock([Cliente, Fornecedor, NotaFiscal, NotaAvulsa, Organizacao, Pessoa, Vendedor])
class ImportClienteExcelServiceSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test Cell Row"() {
        given:
        def params = [:]
        params.getCell = { int i ->
            def cell = Mock(Cell)
            cell.toString = {
                if (i == 0)
                    'primeira celula'
                else
                    'LOL'
            }
            cell
        }
        def row = Mock(params, Row)
        when:
        def result = row.getCell(0).toString()
        then:
//            true == true
            result == 'primeira celula'
    }

}
