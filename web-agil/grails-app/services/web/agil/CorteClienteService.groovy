package web.agil

import grails.transaction.Transactional
import web.agil.cadastro.Cliente
import web.agil.cadastro.enums.StatusPapel
import web.agil.financeiro.Lancamento
import web.agil.financeiro.enums.StatusFinanceiro
import web.agil.financeiro.enums.StatusLancamento

@Transactional
class CorteClienteService {

    BatchService batchService

    def processar(Date dataReferencia = new Date()) {
        corteLancamentosAtrasados(dataReferencia)
        corteClientesInadiplentes(dataReferencia)
    }

    def corteClientesInadiplentes(Date dataReferencia = new Date()) {
        log.debug "Iniciando corte clientes..."
        def clienteIdList = Cliente.withCriteria {
            projections {
                property 'id'
            }
            eq('statusPapel', StatusPapel.ATIVO)
        }

        clienteIdList.eachWithIndex { Long id, int i ->
            def cliente = Cliente.get(id)
            def lancCount = Lancamento.createCriteria().count {
                conta {
                    eq('papel', cliente)
                }
                eq('status', StatusLancamento.ATRASADO)
            }
            if (lancCount) {
                cliente.statusFinanceiro = StatusFinanceiro.INADIPLENTE
            } else {
                cliente.statusFinanceiro = StatusFinanceiro.EM_DIA
            }
            log.debug "Cliente: ${cliente}, ${cliente.statusFinanceiro}"
            batchService.gormClean(i, 50)
        }

        log.debug "...Corte clientes"
    }

    def corteLancamentosAtrasados(Date dataReferencia = new Date()) {
        log.debug "Iniciando corte de lancamentos atrasados..."
        def lancIds = Lancamento.withCriteria {
            projections {
                property 'id'
            }
            lt('dataPrevista', dataReferencia)
        }
        lancIds.eachWithIndex { Long id, int i ->
            def lanc = Lancamento.get(id)
            if (lanc.dataPrevista < dataReferencia && lanc.status == StatusLancamento.ABERTO) {
                lanc.status = StatusLancamento.ATRASADO
                lanc.save()
            }
            batchService.gormClean(i, 50)
        }
        log.debug "...Corte de lancamentos atrasados"
    }

}
