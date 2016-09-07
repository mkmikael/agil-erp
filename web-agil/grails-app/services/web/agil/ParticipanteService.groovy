package web.agil

import grails.transaction.Transactional
import web.agil.cadastro.Cidade
import web.agil.cadastro.Organizacao
import web.agil.cadastro.Participante
import web.agil.cadastro.Pessoa

@Transactional
class ParticipanteService {

    Participante save(Class<? extends Participante> clazz, Map params) {
        def participanteId = params.participante?.id as Long
        def participante
        if (clazz == Pessoa) {
            participante = participanteId ? Pessoa.get(participanteId) : new Pessoa()
        } else if (clazz == Organizacao) {
            participante = participanteId ? Organizacao.get(participanteId) : new Organizacao()
        } else {
            throw new IllegalArgumentException('O argumento clazz é inválido')
        }

        participante.with {
            if (params.participante?.nome)
                nome           = params.participante.nome
            if (params.participante?.nomeFantasia)
                nomeFantasia   = params.participante.nomeFantasia
            if (params.participante?.doc)
                doc            = params.participante.doc
        }

        def endereco = participante.endereco
        if (endereco) {
            endereco.with {
                if (params.endereco?.cep)
                    cep            = params.endereco.cep
                if (params.endereco?.logradouro)
                    logradouro     = params.endereco.logradouro
                if (params.endereco?.numero)
                    numero         = params.endereco.numero
                if (params.endereco?.complemento)
                    complemento    = params.endereco.complemento
                if (params.endereco?.bairro)
                    bairro         = params.endereco.bairro
                def cidadeId = params.endereco?.cidade?.id
                if (cidadeId)
                    cidade         = Cidade.get(cidadeId as Long)
            }
        }

        def telefone = participante.telefone
        if (telefone) {
            telefone.with {
                if (params.telefone?.ddd)
                    ddd    = params.telefone?.ddd
                if (params.telefone?.numero)
                    numero = params.telefone?.numero
            }
        }
        participante
    }

}
