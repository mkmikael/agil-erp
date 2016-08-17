package web.agil.cadastro

class Vendedor extends Papel {

    def beforeInsert() {
        super.beforeInsert()
        if (!codigo)
            codigo = "${sprintf('V%05d', Vendedor.count() + 1)}"
    }

    static constraints = {
    }
}
