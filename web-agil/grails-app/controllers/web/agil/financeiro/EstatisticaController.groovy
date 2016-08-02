package web.agil.financeiro

import web.agil.EstatisticaService
import web.agil.cadastro.Produto

class EstatisticaController {

    EstatisticaService estatisticaService

    def index() {}

    private cache() {
        def comparativoAnualList = []
        def produtoList = Produto.list(sort: 'nome')
        produtoList.each { p ->
            comparativoAnualList << estatisticaService.comparativoMensal(p)
        }
        def model = [
                comparativoAnualList : comparativoAnualList,
                itemNfList: estatisticaService.biProduto(params),
                resumoFornecedores: estatisticaService.biFornecedor(params)
        ]
        model + params
    }

    def biProduto() {
        if (!params.mes)
            params.mes = Calendar.instance.get(Calendar.MONTH) + 1
        render template: 'tableProdutoBI', model: [itemNfList: estatisticaService.biProduto(params)] + params
    }

    def biFornecedor() {
        if (!params.mes)
            params.mes = Calendar.instance.get(Calendar.MONTH) + 1
        render template: 'tableFornecedorBI', model: [resumoFornecedores: estatisticaService.biFornecedor(params)] + params
    }

    def produtoComparativoMensal() {
        def comparativoAnualList = []
        def produtoList = Produto.list(sort: 'nome')
        produtoList.each { p ->
            comparativoAnualList << estatisticaService.comparativoMensal(p)
        }
        render template: 'tableComparativoMensal', model: [comparativoAnualList: comparativoAnualList]
    }

}
