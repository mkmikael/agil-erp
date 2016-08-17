package web.agil.cadastro

import web.agil.cadastro.enums.TipoPreco

import java.text.DecimalFormat
import java.text.NumberFormat

class PrecoComponente {

    TipoPreco tipo = TipoPreco.VENDA
    BigDecimal preco

    static belongsTo = [composicao: PrecoComposicao]
    static constraints = {
        preco scale: 6
    }

    String toString() {
        if (!preco)
            preco = 0.0g
        NumberFormat.currencyInstance.format(preco.doubleValue())
    }
}
