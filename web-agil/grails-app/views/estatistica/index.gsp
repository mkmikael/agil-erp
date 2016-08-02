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

<div class="navbar navbar-default" role="navigation">
    <ul class="nav navbar-nav navbar-left">
        <li>
            <a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
        </li>
        <li>
            <a id="produtoCmpMensal" class="list">Produto Comparativo Mensal</a>
        </li>
        <li>
            <a id="fornecedorBI" class="list">BI Fornecedor</a>
        </li>
        <li>
            <a id="produtoBI" class="list">BI Produto</a>
        </li>
    </ul>
</div>


<br>

<div id="views">

</div>
</body>
</html>