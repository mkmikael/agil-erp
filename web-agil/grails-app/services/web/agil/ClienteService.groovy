package web.agil

import grails.transaction.Transactional
import web.agil.cadastro.Cliente
import web.agil.cadastro.enums.StatusPapel
import web.agil.util.Util

@Transactional
class ClienteService {

    /**
     * Este método retorna clienteList e clienteCount dos clientes e também podem ser adicionados parametros para criteria query
     * @param params Map
     * @return [clienteList: ArrayList, clienteCount: Long]
     */
    Map clienteListAndCount(Map params) {
        def criteria = {
            if (params.statusPapel)
                eq('statusPapel', StatusPapel.valueOf(params.statusPapel))
            if (params.id)
                eq('id', params.id as Long)
            if (params.codigo)
                ilike('codigo', "%${params.codigo}%")
            participante {
                if (params.nome)
                    ilike('nome', "%${params.nome}%")
                if (params.nomeFantasia)
                    ilike('nomeFantasia', "%${params.nomeFantasia}%")
                or {
                    def doc = Util.onlyNumber(params.doc)
                    if (doc) {
                        ilike('cpf', "%$doc%")
                        ilike('cnpj', "%$doc%")
                    }
                }
            } // participante

        } // criteria

        def clienteList = Cliente.createCriteria().list(params) {
            criteria.delegate = delegate
            criteria()
        }
        def clienteCount = Cliente.createCriteria().count(criteria)
        [clienteList: clienteList, clienteCount: clienteCount]
    }

}
