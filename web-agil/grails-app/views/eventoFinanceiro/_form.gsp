<%@ page import="web.agil.financeiro.enums.TipoEventoFinanceiro; web.agil.financeiro.planoPagamento.ConjuntoIntervaloPgto; web.agil.financeiro.planoPagamento.IntervaloPagamento" %>

<bs:fieldGroup name="tipo" type="select" label="Tipo"
               from="[TipoEventoFinanceiro.NOTA_FISCAL]" />

<bs:fieldGroup name="dataReferencia" class="datepicker" label="Data de Referência"
               value="${new Date().format('dd/MM/yyyy')}" required="${true}"/>

<bs:fieldGroup name="valor" label="Valor" value="${evento?.valor}" class="money selectpicker" required="${true}" />

<g:set var="planoPGTO" value="${evento?.planoPagamento as ConjuntoIntervaloPgto}" />
<bs:fieldGroup name="intervalos" label="Prazo" type="multiple"
               class="selectpicker" value="${planoPGTO?.intervalos?.id}"
               from="${IntervaloPagamento.findAllByAtivo(true, [sort: 'dias'])}"
               optionKey="id" optionValue="dias" required="${true}" />