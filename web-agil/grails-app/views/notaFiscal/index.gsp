<%@ page import="web.agil.financeiro.enums.TipoNotaFiscal" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'notaFiscal.label', default: 'NotaFiscal')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
<h2><g:message code="default.list.label" args="[entityName]" /></h2>

<g:if test="${flash.message}">
    <div class="alert alert-info alert-dismissible" role="alert">
        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
        ${flash.message}
    </div>
</g:if>

<div class="panel panel-default">
    <div class="panel-heading">
        <a data-toggle="collapse" data-target="#filtros"><span class="glyphicon glyphicon-collapse-down"></span> Filtros</a>
    </div>
    <div id="filtros" class="panel-body collapse in">
        <g:form>
            <div class="fieldcontain">
                <label for="searchCodigo">Código</label>
                <g:textField name="searchCodigo" value="${searchCodigo}" />
            </div>
            <div class="fieldcontain">
                <label for="searchCliente">Cliente</label>
                <g:textField name="searchCliente" value="${searchCliente}" size="50" />
            </div>
            <div class="fieldcontain">
                <label for="searchTotal">Total</label>
                <g:textField name="searchTotal" value="${searchTotal}" />
            </div>
            <div class="fieldcontain">
                <label for="searchTipo">Tipo</label>
                <g:select name="searchTipo" from="${TipoNotaFiscal.values()}" value="${searchTipo}" noSelection="['': 'TODOS']" />
            </div>
            <div class="fieldcontain">
                <label for="searchCodigo">Data Emissão</label>
                <g:field type="date" name="searchDtEmissaoInicio" value="${searchDtEmissaoInicio}" /> até <g:field type="date" name="searchDtEmissaoFim" value="${searchDtEmissaoFim}" />
            </div>
            <div class="fieldcontain">
                <label for="searchCodigo">Data Autorização</label>
                <g:field type="date" name="searchDtAuthInicio" value="${searchDtAuthInicio}" /> até <g:field type="date" name="searchDtAuthFim" value="${searchDtAuthFim}" />
                <br>
                <input type="submit" value="Procurar">
            </div>
        </g:form>
    </div>
</div>

<div class="navbar navbar-default" role="navigation" hidden>
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
    <th>Id</th>
    <th>Código</th>
    <th>CPF/CNPJ</th>
    <th>Cliente</th>
    <g:sortableColumn params="${params}" property="dataEmissao" title="Data Emissão" />
    %{--<g:sortableColumn params="${params}" property="dataAutorizacao" title="Data Autorização" />--}%
    <th>Tipo</th>
    <th>Total</th>
    <th></th>
    </thead>
    <tbody>
    <g:each in="${notaFiscalList}" var="nf">
        <tr>
            <td><g:link action="show" id="${nf.id}">${nf.id}</g:link></td>
            <td>${nf.codigo}</td>
            <td>${nf.doc}</td>
            <td>${nf.cliente}</td>
            <td><g:formatDate date="${nf.dataEmissao}" format="dd/MM/yyyy" /></td>
            %{--<td><g:formatDate date="${nf.dataAutorizacao}" format="dd/MM/yyyy" /></td>--}%
            <td>${nf.tipo}</td>
            <td><g:formatNumber number="${nf.total}" type="currency" /></td>
            <td><g:link action="show" id="${nf.id}"><span class="glyphicon glyphicon-search"></span> Visualizar</g:link></td>
        </tr>
    </g:each>
    </tbody>
</table>

<ul class="pagination">
    <g:paginate total="${notaFiscalCount ?: 0}" params="${params}" />
</ul>
</body>
</html>