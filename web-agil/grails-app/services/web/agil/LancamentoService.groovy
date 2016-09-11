package web.agil

import grails.transaction.Transactional
import org.springframework.util.Assert
import web.agil.cadastro.Cliente
import web.agil.cadastro.Papel
import web.agil.financeiro.EventoFinanceiro
import web.agil.financeiro.Lancamento
import web.agil.financeiro.NotaComercial
import web.agil.financeiro.enums.StatusLancamento
import web.agil.financeiro.enums.TipoEventoFinanceiro
import web.agil.financeiro.planoPagamento.ConjuntoIntervaloPgto

@Transactional
class LancamentoService {

    ContaService contaService

    def lancamentoListAndCount(Map params) {
        def criteria = {
            conta {
                papel {
                    participante {
                        if (params.papel) {
                            or {
                                eq('nome', "%${params.papel}%")
                                eq('nomeFantasia', "%${params.papel}%")
                            }
                        }
                    } // participante
                } // papel
            } // conta
            if (params.status)
                eq('status', StatusLancamento.valueOf(params.status))
            if (params.dataPrevista_inicio)
                ge('dataPrevista', Date.parse('dd/MM/yyyy', params.dataPrevista_inicio))
            if (params.dataPrevista_fim)
                le('dataPrevista', Date.parse('dd/MM/yyyy', params.dataPrevista_fim))
        }
        def model = [:]
        model.lancamentoList = Lancamento.createCriteria().list(params, criteria)
        model.lancamentoCount = Lancamento.createCriteria().count(criteria)
        model
    }

    def criarLancamentos(NotaComercial nota, List intervaloIds) {
        def evento = new EventoFinanceiro()
        evento.papel = nota.cliente
        evento.tipo = TipoEventoFinanceiro.NOTA_AVULSA
        evento.notaComercial = nota
        evento.valor = nota.total
        evento.addAllToIntervalo(intervaloIds)
        evento.save(failOnError: true)
        criarLancamentos(evento, nota.dataEmissao)
    }

    /**
     *
     * Este método persiste lançamentos a partir de um evento
     *
     * @param evento
     * @param dataReferencia
     * @return
     */
    def criarLancamentos(EventoFinanceiro evento, Date dataReferencia) {
        ConjuntoIntervaloPgto conjunto = evento.planoPagamento
        def datas = conjunto.getDatasPrevistas(dataReferencia)
        def parcelas = datas.size()
        datas.each { dataPrevista ->
            def valor = evento.valor / parcelas
            criarLancamento(evento: evento, valor: valor, papel: evento.papel, dataOriginal: dataPrevista)
        }
    }

    def criarEvento(Map params) {
        def evento = new EventoFinanceiro()
        evento.tipo = params.tipo
    }

    /**
     *
     * Este persiste um lancamento com os seguintes parâmetros:
     * <ul>
     *     <li>evento REQUIRED, EventoFinanceiro</li>
     *     <li>papel REQUIRED, Papel</li>
     *     <li>valor REQUIRED, BigDecimal</li>
     *     <li>dataOriginal default: new Date, Papel</li>
     *     <li>dataPrevista default: dataOriginal, Papel</li>
     *     <li>dataEfetivacao OPTIONAL, Papel</li>
     *     <li>status default: ABERTO, StatusLancamento</li>
     * </ul>
     *
     * @param params
     * @return
     */
    Lancamento criarLancamento(Map params) {
        StatusLancamento status = params.status ?: StatusLancamento.ABERTO
        BigDecimal valor        = params.valor
        EventoFinanceiro evento = params.evento
        Papel papel             = params.papel
        Date dataOriginal       = params.dataOriginal ?: new Date()
        Date dataPrevista       = params.dataPrevista ?: dataOriginal
        Date dataEfetivacao     = params.dataEfetivacao

        if (!papel) {
            papel = Cliente.createCriteria().get {
                participante {
                    eq('nome', 'OUTROS')
                }
            }
        }

        Assert.notNull(evento, "O campo 'evento' é obrigatório.")
        Assert.notNull(papel, "O campo 'papel' é obrigatório.")
        Assert.notNull(valor, "O campo 'valor' é obrigatório.")

        def lanc = new Lancamento()
        lanc.conta = contaService.getConta(papel)
        lanc.dataOriginal = dataOriginal
        lanc.dataPrevista = dataPrevista
        lanc.dataEfetivacao = dataEfetivacao
        lanc.status = status
        lanc.evento = evento
        lanc.valor = valor
        lanc.save(failOnError: true)
    }
}
