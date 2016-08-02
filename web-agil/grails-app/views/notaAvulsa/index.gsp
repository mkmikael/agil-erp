<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'notaAvulsa.label', default: 'NotaAvulsa')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
<h1><g:message code="default.list.label" args="[entityName]" /></h1>
<g:if test="${flash.message}">
    <div class="message" role="status">${flash.message}</div>
</g:if>

<div class="navbar navbar-default" role="navigation">
    <ul class="nav navbar-nav navbar-left">
        <li>
            <g:link action="create">
                <span class="glyphicon glyphicon-plus"></span> <g:message code="default.new.label" args="[entityName]" />
            </g:link>
        </li>
    </ul>
</div>

<table data-bs-table>
    <thead>
    <g:sortableColumn property="codigo" params="${params}" title="Número Doc" />
    <g:sortableColumn property="doc" params="${params}" title="CPF/CNPJ" />
    <g:sortableColumn property="nome" params="${params}" title="Nome/Razão Social" />
    <g:sortableColumn property="dataEmissao" params="${params}" title="Dt Emissão" />
    <g:sortableColumn property="dataVencimento" params="${params}" title="Dt Vencimento" />
    <g:sortableColumn property="valorTotal" params="${params}" title="Valor Total" />
    </thead>
    <tbody>
    <g:each in="${notaAvulsaList}" var="notaAvulsa">
        <tr>
            <td><g:link action="show" id="${notaAvulsa.id}">${notaAvulsa.codigo}</g:link></td>
            <td>${notaAvulsa.cliente.participante.doc}</td>
            <td>${notaAvulsa.cliente}</td>
            <td><g:formatDate date="${notaAvulsa.dataEmissao}" format="dd/MM/yyyy HH:mm" /></td>
            <td><g:formatDate date="${notaAvulsa.dataVencimento}" format="dd/MM/yyyy" /></td>
            <td><g:formatNumber number="${notaAvulsa.total}" type="currency" /></td>
        </tr>
    </g:each>
    </tbody>
</table>
<div class="pagination">
    <g:paginate total="${notaAvulsaCount ?: 0}" />
</div>
</body>
</html>