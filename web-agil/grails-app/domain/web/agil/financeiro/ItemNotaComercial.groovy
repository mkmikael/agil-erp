package web.agil.financeiro

import web.agil.cadastro.Produto
import web.agil.cadastro.UnidadeMedida

class ItemNotaComercial {

    Produto produto
    UnidadeMedida unidadeMedida
    BigDecimal preco
    Integer quantidade
    BigDecimal total
    Integer itemOrder

    static belongsTo = [notaComercial: NotaComercial]
    static constraints = {
        preco scale: 6
        total scale: 6, nullable: true
        itemOrder nullable: true
    }
}
