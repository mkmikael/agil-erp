<%@ page import="web.agil.financeiro.enums.TipoNotaFiscal" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'estatistica.label', default: 'Estatística')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
    <asset:javascript src="web-agil/estatistica/index" />
</head>
<body>
<h1>${entityName}</h1>

<g:if test="${flash.message}">
    <div class="message" role="status">${flash.message}</div>
</g:if>

<p>
    <a id="produtoCmpMensal" class="btn btn-default">
        <span class="glyphicon glyphicon-stats"></span> Produto Comparativo Mensal</a>
    <a id="fornecedorBI" class="btn btn-default">
        <span class="glyphicon glyphicon-stats"></span> BI Fornecedor</a>
    <a id="produtoBI" class="btn btn-default">
        <span class="glyphicon glyphicon-stats"></span> BI Produto</a>
</p>


<br>

<div class="panel panel-default">
    <div id="views" class="panel-body">
    </div> <!-- panel-body -->
</div> <!-- panel -->

</body>
</html>