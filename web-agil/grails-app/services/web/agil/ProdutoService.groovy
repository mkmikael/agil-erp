package web.agil

import grails.transaction.Transactional
import web.agil.cadastro.Produto

@Transactional
class ProdutoService {

    Map produtoListAndCount(Map params) {
        def criteria = {
            if (params.id)
                eq('id', params.id as Long)
            if (params.nome)
                ilike('nome', "%${params.nome}%")
            if (params.codigo)
                ilike('codigo', "%${params.codigo}%")
            if (params.ncm)
                ilike('ncm', "%${params.ncm}%")

            if (params.fornecedor) {
                fornecedor {
                    eq('id', params.fornecedor as Long)
                }
            }

        }
        def produtoList = Produto.createCriteria().list(params, criteria)
        def produtoCount = Produto.createCriteria().count(criteria)
        [produtoList: produtoList, produtoCount: produtoCount]
    }
}
