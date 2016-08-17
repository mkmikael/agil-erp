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
            <div class="row">
                <bs:fieldGroup name="searchCodigo" label="Código" value="${searchCodigo}" classGroup="col-md-2" />
                <bs:fieldGroup name="searchCliente" label="Cliente" value="${searchCliente}" classGroup="col-md-3" />
                <bs:fieldGroup name="searchTipo" label="Tipo" type="select"
                               from="${TipoNotaFiscal.values()}" value="${searchTipo}"
                               noSelection="['': 'TODOS']" classGroup="col-md-2" />
                <bs:fieldGroup name="searchTotal" label="Total" value="${searchTotal}" classGroup="col-md-1" />
            </div>
            <div class="form-group">
                <label for="searchDtEmissaoInicio">Data Emissão</label>
                <div class="form-inline">
                    <g:textField class="form-control datepicker" name="searchDtEmissaoInicio" value="${searchDtEmissaoInicio}" /> até
                    <g:textField class="form-control datepicker" name="searchDtEmissaoFim" value="${searchDtEmissaoFim}" />
                </div>
            </div>
            <div class="form-group">
                <label for="searchDtAuthInicio">Data Autorização</label>
                <div class="form-inline">
                    <g:textField name="searchDtAuthInicio" class="form-control datepicker" value="${searchDtAuthInicio}" /> até
                    <g:textField name="searchDtAuthFim" class="form-control datepicker" value="${searchDtAuthFim}" />
                </div>
                <br>
                <button class="btn btn-default" type="submit">
                    <span class="glyphicon glyphicon-search"></span> Procurar
                </button>
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