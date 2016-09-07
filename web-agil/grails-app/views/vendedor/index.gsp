<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'vendedor.label', default: 'Vendedor')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
<div class="panel panel-default">
    <div class="panel-heading">
        <h4><g:message code="default.list.label" args="[entityName]" /></h4>
    </div> <!-- panel-heading -->
    <div class="panel-body">
        <bs:alert />

        <p>
            <bs:createButton entityName="${entityName}" />
        </p>

        <g:if test="${vendedorList}">
            <f:table collection="${vendedorList}"
                     properties="['codigo', 'participante', 'statusPapel']" />
        </g:if>
        <g:else>
            <div class="well">Sem registros</div>
        </g:else>

        <ul class="pagination">
            <g:paginate total="${vendedorCount ?: 0}" />
        </ul>
    </div> <!-- panel-body -->
</div> <!-- panel -->
</body>
</html>