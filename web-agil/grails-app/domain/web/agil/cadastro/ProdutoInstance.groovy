package web.agil.cadastro

class ProdutoInstance {

    BigDecimal preco
    Produto produto

    static constraints = {
        preco scale: 6
    }
}
