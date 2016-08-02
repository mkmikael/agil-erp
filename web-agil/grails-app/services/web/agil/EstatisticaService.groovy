package web.agil

import grails.transaction.Transactional
import web.agil.cadastro.Produto
import web.agil.financeiro.ItemNotaComercial
import web.agil.financeiro.enums.TipoNotaFiscal

@Transactional
class EstatisticaService {

    def resumoAnualProduto(Produto p) {
        def hql = """
		select new map(
			month(i.notaComercial.dataEmissao) as mes,
			sum(i.quantidade) as quant,
			sum(i.total) as total)
        from ItemNotaComercial i
        where
            i.notaComercial.tipo != 'BONIFICACAO'
            and i.notaComercial.tipo != 'TROCA'
            and i.notaComercial.cliente.id not in (28399, 30453, 28389)
			and i.produto = :produto
        group by month(i.notaComercial.dataEmissao)
        order by total desc
"""
        def resumo = ItemNotaComercial.executeQuery(hql, [produto: p])
        (1..12).each { mes ->
            boolean contains = false
            resumo.each { map ->
                map.each { key, value ->
                    if (key == "mes" && value == mes)
                        contains = true
                }
            }
            if (!contains)
                resumo << [mes: mes, total: 0.0, quant: 0]
        }
        resumo.sort { it.mes }
        resumo.each {
            it.produto = p
        }
        resumo
    }

    def comparativoMensal(Produto p) {
        def list = resumoAnualProduto(p)
        def comparativoList = []
        list.eachWithIndex { map, i ->
            if (i != 0) {
                def a = list[i - 1].quant
                def b = list[i].quant
                if (a != 0) {
                    comparativoList << (b - a) / a
                } else if (b > 0) {
                    comparativoList << 1
                } else {
                    comparativoList << 0
                }
            }
        } // list
        def count = 0
        def listCopy = new ArrayList(list)
        comparativoList.eachWithIndex { cmp, i ->
            listCopy.add(i + 1 + count, cmp)
            count++
        }
        listCopy
    }

    def biProduto(params) {
        def hql = """
        select new map(i.produto as produto, sum(i.quantidade) as quant, sum(i.total) as total)
        from ItemNotaComercial i
        where month(i.notaComercial.dataEmissao) = :mes
            and i.notaComercial.tipo != 'BONIFICACAO'
            and i.notaComercial.tipo != 'TROCA'
            and i.notaComercial.cliente.id not in (28399, 30453, 28389)
        group by i.produto
        order by total desc
"""
        def resumoProdutos = ItemNotaComercial.executeQuery(hql, [mes: params.mes as Integer]);
        def resumoProdutosWithBoni = []
        resumoProdutos.each { resumoProd ->
            hql = """
        select new map(sum(i.quantidade) as quant_boni)
        from ItemNotaComercial i
        where month(i.notaComercial.dataEmissao) = :mes
            and i.notaComercial.tipo = 'BONIFICACAO'
            and i.produto = :produto
            and i.notaComercial.cliente.id not in (28399, 30453, 28389)
        group by i.produto
"""
            def bonificados = ItemNotaComercial.executeQuery(hql, [mes: params.mes as Integer, produto: resumoProd.produto])
            if (!bonificados?.getAt(0)) {
                bonificados = [quant_boni: 0]
            }
            resumoProdutosWithBoni << (resumoProd + bonificados)
        }
        resumoProdutosWithBoni
    }

    def biFornecedor(params) {
        def hql = """
        select new map(
            i.produto.fornecedor as fornecedor,
            sum(i.quantidade) as quant,
            sum(i.total) as total)
        from ItemNotaComercial i
        where month(i.notaComercial.dataEmissao) = :mes
            and i.notaComercial.tipo != 'BONIFICACAO'
            and i.notaComercial.tipo != 'TROCA'
            and i.notaComercial.cliente.id not in (28399, 30453, 28389)
        group by i.produto.fornecedor
        order by total desc
"""
        def resumoProdutos = ItemNotaComercial.executeQuery(hql, [mes: params.mes as Integer]);
        resumoProdutos
    }

    def updateNfDevolucao() {
        def itens = ItemNotaComercial.withCriteria {
            notaComercial {
                eq('tipo', TipoNotaFiscal.DEVOLUCAO)
            }
        }
        itens.each { nf ->
            if (nf.total > 0)
                nf.total = -nf.total
            if (nf.quantidade > 0)
                nf.quantidade = -nf.quantidade
            if (nf.notaFiscal.total > 0)
                nf.notaFiscal.total = -nf.notaFiscal.total
        }
    }
}
