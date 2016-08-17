package web.agil

import grails.transaction.Transactional
import web.agil.cadastro.*
import web.agil.util.Util

@Transactional
class ImportClienteService {

    /**
     *
     * @param map java.util.Map
     * <p>Os seguintes parâmetros do map deve seguir estes nomes:</p>
     * <ul>
     *     <li>nome</li>
     *     <li>nomeFantasia</li>
     *     <li>doc</li>
     *     <li>inscricaoEstadual</li>
     *     <li>
     *         endereco <br>
     *         <ul>
     *             <li>cidade</li>
     *             <li>uf</li>
     *             <li>logradouro</li>
     *             <li>numero</li>
     *             <li>bairro</li>
     *             <li>cep</li>
     *             <li>fone</li>
     *         </ul>
     *     </li>
     *     <li>
     *         telefone <br>
     *         <ul>
     *             <li>ddd</li>
     *             <li>fone</li>
     *         </ul>
     *     </li>
     * </ul>
     * @return web.agil.cadastro.Cliente
     *
     */
    Cliente createClienteFull(Map map) {
        def cliente = createCliente(map)
        if (cliente)
            createEnderecoAndContato(cliente.participante, map)
        cliente
    }

    /**
     *
     * @param map java.util.Map
     * <p>Os seguintes parâmetros do map deve seguir estes nomes:</p>
     * <ul>
     *     <li>nome</li>
     *     <li>nomeFantasia</li>
     *     <li>doc</li>
     *     <li>inscricaoEstadual</li>
     * </ul>
     *
     * @return web.agil.cadastro.Cliente
     *
     */
    Cliente createCliente(Map map) {
        map.nome = Util.removeSpecialCaracter(map.nome)?.trim()
        def clienteInstance = Cliente.createCriteria().get {
            participante {
                eq('nome', map.nome)
            }
        }
        if (!clienteInstance) {
            map.doc = Util.onlyNumber(map.doc?.trim())
            map.nomeFantasia = Util.removeSpecialCaracter(map.nomeFantasia)?.trim()
            map.inscricaoEstadual = map.inscricaoEstadual?.trim()

            def participante
            clienteInstance = new Cliente()
            clienteInstance.vendedor = map.vendedor
            def docSize = map.doc?.length()
            if (docSize == 14) {
                participante = new Organizacao()
                participante.inscricaoEstadual = map.inscricaoEstadual
            } else {
                participante = new Pessoa()
            }
            participante.doc = map.doc
            participante.nome = map.nome
            participante.nomeFantasia = map.nomeFantasia
            if (participante.validate()) {
                participante.save()
            } else {
                log.debug("Validator Error $participante")
                return
            }

            clienteInstance.participante = participante
            clienteInstance.save()
        }
        clienteInstance
    }

    /**
     *
     * @param map java.util.Map
     * <p>Os seguintes parâmetros do map deve seguir estes nomes:</p>
     * <ul>
     *     <li>
     *         endereco <br/>
     *         <ul>
     *              <li>cidade</li>
     *              <li>uf</li>
     *              <li>logradouro</li>
     *              <li>numero</li>
     *              <li>bairro</li>
     *              <li>cep</li>
     *         </ul>
     *     </li>
     *     <li>
     *         fone <br/>
     *         <ul>
     *              <li>fone</li>
     *              <li>ddd</li>
     *         </ul>
     *     </li>
     * </ul>
     *
     * @return Void
     *
     */
    void createEnderecoAndContato(Participante participanteInstance, Map map) {
        createEndereco(participanteInstance, map.endereco as Map)
        createTelefone(participanteInstance, map.telefone as Map)
    }

    /**
     *
     * @param telefone java.util.Map
     * <p>Os seguintes parâmetros do telefone deve seguir estes nomes:</p>
     * <ul>
     *     <li>fone</li>
     *     <li>ddd</li>
     * </ul>
     *
     * @return web.agil.cadastro.Telefone
     *
     */
    Telefone createTelefone(Participante participanteInstance, Map telefone) {
        def telefoneInstance = new Telefone()
        telefoneInstance.ddd = telefone.ddd ?: '91'
        telefoneInstance.numero = telefone.fone?.trim()
        telefoneInstance.participante = participanteInstance
        telefoneInstance.save()
    }

    /**
     *
     * @param endereco java.util.Map
     * <p>Os seguintes parâmetros do endereco deve seguir estes nomes:</p>
     * <ul>
     *     <li>cidade</li>
     *     <li>uf</li>
     *     <li>logradouro</li>
     *     <li>numero</li>
     *     <li>bairro</li>
     *     <li>cep</li>
     * </ul>
     *
     * @return web.agil.cadastro.Endereco
     *
     */
    Endereco createEndereco(Participante participanteInstance, Map endereco) {
        if (!participanteInstance)
            throw new IllegalArgumentException('O campo participante é requerido.')
        def estadoInstance = Estado.findBySigla(endereco.uf)
        if (!estadoInstance) {
            estadoInstance = new Estado()
            estadoInstance.sigla = endereco.uf
            if (endereco.uf == 'PA')
                estadoInstance.nome = "Pará"
            else
                estadoInstance.nome = endereco.uf
            estadoInstance.save()
        }

        endereco.cidade = Util.removeSpecialCaracter(endereco.cidade)
        def cidadeInstance = Cidade.findByNome(endereco.cidade)
        if (!cidadeInstance) {
            cidadeInstance = new Cidade()
            cidadeInstance.estado = estadoInstance
            cidadeInstance.nome = endereco.cidade
            cidadeInstance.save()
        }

        endereco.cep = Util.onlyNumber(endereco.cep)
        def enderecoInstance = new Endereco()
        enderecoInstance.with {
            logradouro = endereco.logradouro
            numero = endereco.numero
            bairro = endereco.bairro
            cep = endereco.cep
            cidade = cidadeInstance
            participante = participanteInstance
            save()
        }
        println enderecoInstance.errors.fieldErrors.field

    }

    /**
     * Este métido quebra os nome recebido em dois: nome e nomeFantasia
     *
     * @param _nome
     * @return [nome: '', nomeFantasia]
     */
    Map splitName(_nome) {
        _nome = _nome.split('-')
        def nome
        def nomeFantasia = null
        if (_nome.size() == 2) {
            nomeFantasia = _nome[1]
        } else if (_nome.size() == 1) {
            nomeFantasia = _nome[0]
        }
        nome = _nome[0]
        [nome: nome?.trim(), nomeFantasia: nomeFantasia?.trim()]
    }

}
