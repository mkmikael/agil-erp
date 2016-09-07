<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'vendedor.label', default: 'Vendedor')}" />
    <title><g:message code="default.show.label" args="[entityName]" /></title>
</head>
<body>
<div class="panel panel-default">
    <div class="panel-heading">
        <h4><g:message code="default.show.label" args="[entityName]" /></h4>
    </div> <!-- panel-heading -->
    <div class="panel-body">
        <bs:alert />

        <p>
            <bs:listButton entityName="${entityName}" />
            <bs:createButton entityName="${entityName}" />
        </p>

        <f:display bean="vendedor" />

        <div>
            <bs:editButton resource="${this.vendedor}" />
            <bs:deleteButton resource="${this.vendedor}" />
        </div>
    </div> <!-- panel-body -->
</div> <!-- panel -->

</body>
</html>
