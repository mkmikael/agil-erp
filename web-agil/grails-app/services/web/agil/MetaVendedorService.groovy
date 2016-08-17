package web.agil

import grails.transaction.Transactional
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import web.agil.cadastro.Cliente
import web.agil.cadastro.Vendedor
import web.agil.cadastro.enums.StatusPapel
import web.agil.financeiro.NotaComercial

@Transactional
class MetaVendedorService {

    def execute(InputStream inputStream) {

    }

    def metaPorMes(Integer mes) {
        def vendedorList = Vendedor.createCriteria().list {
            participante {
                order 'nome'
            }
        }
        def metas = []
        vendedorList.each { Vendedor vendedor ->
            def notasVendedor = buscarNotaPorMesEVendedor(mes, vendedor)
            def meta = metaPorVendedor(notasVendedor)
            metas << [vendedor: vendedor, meta: meta]
        }
    }

    def buscarNotaPorMesEVendedor(Integer mes, Vendedor vendedor) {
        def hql = 'from NotaComercial n where month(n.dataEmissao) = :mes and n.vendedor = :vendedor'
        NotaComercial.executeQuery(hql, [mes: mes, vendedor: vendedor])
    }

    def buscarNotaPorMesEVendedor(InputStream inputStream, Vendedor vendedor) {
        def prop = [

        ]
        Workbook workbook = new XSSFWorkbook(inputStream)
        Sheet sheet = workbook.getSheetAt(0)
        inputStream.close();
        NotaComercial.executeQuery(hql, [mes: mes, vendedor: vendedor])
    }

    Map metaPorVendedor(List<NotaComercial> notas) {
        def notasByCliente = notas.groupBy { it.cliente }
        def positivacao = notasByCliente.entrySet().size()
        def minimo = 0
        def vendas = 0
        notasByCliente.each { Cliente c, List<NotaComercial> notasCliente ->
            def total = notasCliente.total.sum()
            if (total >= 300)
                minimo++
            vendas += total
        }
        [positivacao: positivacao, minimo: minimo, vendas: vendas]
    }

    protected static String get(Row row, int field) {
        row.getCell( field )?.toString()
    }

}
