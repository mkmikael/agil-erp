package web.agil.cadastro

class Produto {

    String ncm
    String codigo
    String nome

    static hasMany = [precos: PrecoComponente]
    static belongsTo = [fornecedor: Fornecedor]
    static constraints = {
    }

    @Override
    String toString() {
        "$codigo - $nome"
    }
}
