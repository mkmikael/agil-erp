package web.agil.cadastro

import web.agil.cadastro.enums.TipoPreco

import java.text.DecimalFormat
import java.text.NumberFormat

class PrecoComponente {

    TipoPreco tipoPreco = TipoPreco.VENDA
    BigDecimal preco
    UnidadeMedida unidadeMedida

    static belongsTo = [produto: Produto]
    static constraints = {
        preco scale: 6
    }

    String toString() {
        if (!preco)
            preco = 0.0g
        NumberFormat.currencyInstance.format(preco.doubleValue())
    }
}
