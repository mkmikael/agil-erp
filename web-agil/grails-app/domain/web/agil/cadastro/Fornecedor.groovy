package web.agil.cadastro

class Fornecedor extends Papel {

    static hasMany = [produtos: Produto]
    static constraints = {
    }
}
