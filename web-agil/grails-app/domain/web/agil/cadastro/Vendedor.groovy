package web.agil.cadastro

class Vendedor extends Papel {

    static hasMany = [clientes: Cliente]
    static constraints = {
    }
}
