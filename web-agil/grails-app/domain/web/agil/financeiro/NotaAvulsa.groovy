package web.agil.financeiro

import web.agil.cadastro.enums.FormaPagamento

class NotaAvulsa extends NotaComercial {

    Date dataVencimento
    FormaPagamento formaPagamento = FormaPagamento.AVISTA

    static constraints = {
        formaPagamento nullable: true
        dataVencimento nullable: true
    }

    def calcularTotal() {
        total = 0
        itens?.each { item ->
            item.total = item.preco * item.quantidade
            total += item.total
        }
        total
    }

    def afterInsert() {
        if (!codigo)
            codigo = sprintf("%08d", NotaAvulsa.count())
    }

}
