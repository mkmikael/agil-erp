package web.agil

import grails.transaction.Transactional
import web.agil.cadastro.Cliente
import web.agil.cadastro.Produto
import web.agil.financeiro.ItemNotaComercial
import web.agil.financeiro.NotaComercial
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
            and i.notaComercial.cliente.id not in (:ids)
			and i.produto = :produto
        group by month(i.notaComercial.dataEmissao)
        order by total desc
"""
        def resumo = ItemNotaComercial.executeQuery(hql, [produto: p, ids: fornecedoresIds()])
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
            and i.notaComercial.cliente.id not in (:ids)
        group by i.produto
        order by total desc
"""
        def paramsResumo = [mes: params.mes as Integer, ids: fornecedoresIds()]
        def resumoProdutos = ItemNotaComercial.executeQuery(hql, paramsResumo);
        def resumoProdutosWithBoni = []
        resumoProdutos.eachWithIndex { resumoProd, i ->
            hql = """
        select new map(sum(i.quantidade) as quant_boni)
        from ItemNotaComercial i
        where month(i.notaComercial.dataEmissao) = :mes
            and i.notaComercial.tipo = 'BONIFICACAO'
            and i.produto = :produto
            and i.notaComercial.cliente.id not in (:ids)
        group by i.produto
"""
            def bonificados = ItemNotaComercial.executeQuery(hql, [mes: params.mes as Integer, produto: resumoProd.produto, ids: fornecedoresIds()])
            bonificados = bonificados?.getAt(0)
            if (!bonificados) {
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
            and i.notaComercial.cliente.id not in (:ids)
        group by i.produto.fornecedor
        order by total desc
"""
        def resumoProdutos = ItemNotaComercial.executeQuery(hql, [mes: params.mes as Integer, ids: fornecedoresIds()]);
        resumoProdutos
    }

    def fornecedoresIds() {
        def fors = ['DORI', 'EMPRESA BRASILEIRA DE DISTRIB', 'RICLAN', 'POMPEIA', 'PANTERA', 'DOLOMITA']
        def ids = []
        fors.each { f ->
            def c = Cliente.createCriteria().get {
                participante {
                    ilike('nome', "%$f%")
                }
            }
            if (c?.id)
                ids << c?.id
        }
        ids
    }

    def positivacao(Integer month) {
        if (!month)
            throw IllegalArgumentException('require month argument.')
        def hql = "select distinct n.cliente from NotaComercial n where month(n.dataEmissao) = :month"
        def result = NotaComercial.executeQuery(hql, [month: month])
        result
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
