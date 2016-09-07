package web.agil.cadastro

class Produto {

    String nome
    String ncm
    String codigo
    String codigoBarra
    UnidadeMedida unidadeMedida

    static hasMany = [composicoes: PrecoComposicao]
    static belongsTo = [fornecedor: Fornecedor]
    static constraints = {
        ncm             nullable: true
        codigo          nullable: true
        codigoBarra     nullable: true
        unidadeMedida   nullable: true
        fornecedor      nullable: true
    }

    @Override
    String toString() {
        if (codigo && nome)
            "$codigo - $nome"
        else
            nome
    }

}
