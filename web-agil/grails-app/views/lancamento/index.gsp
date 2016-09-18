<%@ page import="web.agil.financeiro.enums.TipoLancamento; web.agil.financeiro.enums.StatusLancamento" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="Lançamento" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
    <asset:javascript src="web-agil/lancamento/index.js" />
</head>

<body>
<div class="panel panel-default">
    <div class="panel-heading">
        <h4><g:message code="default.list.label" args="[entityName]" /></h4>
    </div> <!-- panel-heading -->
    <div class="panel-body">
        <bs:alert />

        <bs:panelFilter>
            <g:form>
                <bs:fieldGroup name="papel" label="Cliente" value="${papel}" inline="${true}" />
                <bs:fieldGroup name="status" inline="${true}" type="select" label="Status" from="${StatusLancamento.values()}"
                               keys="${StatusLancamento.values()*.name()}" value="${status}" />
                <div class="form-inline">
                    <bs:fieldGroup name="dataPrevista_inicio" class="datepicker" label="Data Prevista" value="${dataPrevista_inicio}" inline="${true}" /> Até
                    <bs:fieldGroup name="dataPrevista_fim" class="datepicker" value="${dataPrevista_fim}" inline="${true}" />
                </div>
                <br>
            </g:form>
        </bs:panelFilter>

        <g:if test="${eventoList}">
            <div id="dados" style="position: fixed; background-color: #ddd; bottom: 10px; left: 10px; padding: 7px; opacity: .9">
                <g:render template="dados" model="[valorTotalAberto: valorTotalAberto,
                                                   valorTotalPago: valorTotalPago,
                                                   valorTotalAtrasado: valorTotalAtrasado]" />
            </div>

            <table class="table table-bordered">
                <thead>
                    <tr>
                        <g:sortableColumn property="codigo" title="Código" params="${params}" />
                        <th colspan="7">Cliente</th>
                    </tr>
                    <tr>
                        <g:sortableColumn property="dateCreated" title="Criado em" params="${params}" />
                        <g:sortableColumn property="dataPrevista" title="Dt Prevista" params="${params}" />
                        <g:sortableColumn property="valor" title="Valor" params="${params}" />
                        <th>Status</th>
                        <th>Tipo</th>
                        <th></th>
                    </tr>
                </thead>

                <tbody>
                <g:each in="${eventoList}" var="e">
                    <tr style="background-color: #f9f9f9">
                        <td>${e.codigo}</td>
                        <td colspan="5">${e.papel}</td>
                    </tr>
                    <g:each in="${e.lancamentos}" var="l">
                        <tr>
                            <td>${l.dateCreated.format('dd/MM/yyyy HH:mm')}</td>
                            <td>${l.dataPrevista.format('dd/MM/yyyy')}</td>
                            <td><g:formatNumber number="${l.valor}" type="currency" locale="pt_BR" /></td>
                            <td class="status">
                                <g:if test="${l.status == StatusLancamento.PAGO}">
                                    <span class="label label-success">PAGO</span>
                                </g:if>
                                <g:elseif test="${l.status == StatusLancamento.ABERTO}">
                                    <span class='label label-warning'>ABERTO</span>
                                </g:elseif>
                                <g:elseif test="${l.status == StatusLancamento.ATRASADO}">
                                    <span class='label label-danger'>ATRASADO</span>
                                </g:elseif>
                            </td>
                            <td>${l.tipo}</td>
                            <td class="text-center">
                                <g:if test="${l.status != StatusLancamento.CANCELADO && l.tipo != TipoLancamento.CANCELAMENTO}">
                                    <button type="button" class="btn btn-xs btn-success" data-status="<span class='label label-success'>PAGO</span>"
                                            data-url="${createLink(action: 'pago', id: l.id)}">PAGO</button>
                                    <button type="button" class="btn btn-xs btn-warning" data-status="<span class='label label-warning'>ABERTO</span>"
                                            data-url="${createLink(action: 'aberto', id: l.id)}">ABERTO</button>
                                    <button type="button" class="btn btn-xs btn-danger" data-status="<span class='label label-danger'>ATRASADO</span>"
                                            data-url="${createLink(action: 'atrasado', id: l.id)}">ATRASADO</button>
                                </g:if>
                            </td>
                        </tr>
                    </g:each>
                    </g:each>
                </tbody>
            </table>
        </g:if>
        <g:else>
            <div class="well">Sem registros</div>
        </g:else>

        <ul class="pagination">
            <g:paginate total="${eventoCount ?: 0}" params="${params}" />
        </ul>
    </div> <!-- panel-body -->
</div> <!-- panel -->
</body>
</html>
