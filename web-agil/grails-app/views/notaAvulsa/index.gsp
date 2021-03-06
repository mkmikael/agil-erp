<%@ page import="web.agil.financeiro.enums.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'notaAvulsa.label', default: 'NotaAvulsa')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>

<p>
    <g:link class="btn btn-default" action="create">
        <span class="glyphicon glyphicon-plus"></span> <g:message code="default.new.label" args="[entityName]" />
    </g:link>
</p>

<div class="panel panel-default">
    <div class="panel-heading">
        <h4><g:message code="default.list.label" args="[entityName]" /></h4>
    </div> <!-- panel-heading -->

    <div class="panel-body">
        <g:render template="/layouts/message" />

        <bs:panelFilter>
            <g:form>
                <bs:fieldGroup inline="${true}" type="select" label="Status Nota" name="statusEvento" value="${statusEvento}"
                     from="${StatusEventoFinanceiro.values()}" noSelection="['': 'TODOS']" />
            </g:form>
        </bs:panelFilter>

        <export:formats params="${params}" action="index" formats="['excel']" />
        <br>
        <table data-bs-table>
            <thead>
            <g:sortableColumn property="codigo" params="${params}" title="Número Doc" />
            <g:sortableColumn property="doc" params="${params}" title="CPF/CNPJ" />
            <g:sortableColumn property="nome" params="${params}" title="Nome/Razão Social" />
            <g:sortableColumn property="dataEmissao" params="${params}" title="Dt Emissão" />
            <g:sortableColumn property="valorTotal" params="${params}" title="Valor Total" />
            </thead>
            <tbody>
            <g:each in="${notaAvulsaList}" var="notaAvulsa">
                <tr>
                    <td><g:link action="show" id="${notaAvulsa.id}">${notaAvulsa.codigo}</g:link></td>
                    <td>${notaAvulsa.cliente.participante.doc}</td>
                    <td>${notaAvulsa.cliente}</td>
                    <td><g:formatDate date="${notaAvulsa.dataEmissao}" format="dd/MM/yyyy" /></td>
                    <td><g:formatNumber number="${notaAvulsa.total}" type="currency" /></td>
                </tr>
            </g:each>
            </tbody>
        </table>
        <div class="pagination">
            <g:paginate total="${notaAvulsaCount ?: 0}" />
        </div>
    </div> <!-- panel-body -->
</div> <!-- panel -->
</body>
</html>
