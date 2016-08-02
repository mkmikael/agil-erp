package web.agil.cadastro

class Participante {

    Date dateCreated
    Date lastUpdated
    String nome
    String nomeFantasia
    String email
    String doc
    Endereco endereco
    Telefone telefone

    static transients = ['doc', 'endereco', 'telefone']
    static hasMany = [papeis: Papel, enderecos: Endereco, telefones: Telefone]
    static constraints = {
        dateCreated nullable: true
        lastUpdated nullable: true
        nomeFantasia nullable: true
        email nullable: true
    }

    Endereco getEndereco() {
        enderecos?.getAt(0)
    }

    Telefone getTelefone() {
        telefones?.getAt(0)
    }

    def beforeInsert() {
        if (!dateCreated)
            dateCreated = new Date()
    }

    def beforeUpdate() {
        if (!lastUpdated)
            lastUpdated = new Date()
    }
}
