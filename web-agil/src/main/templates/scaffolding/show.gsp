<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="\${message(code: '${propertyName}.label', default: '${className}')}" />
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
            <bs:listButton entityName="\${entityName}" />
            <bs:createButton entityName="\${entityName}" />
        </p>

        <f:display bean="${propertyName}" />

        <div>
            <bs:editButton resource="\${this.${propertyName}}" />
            <bs:deleteButton resource="\${this.${propertyName}}" />
        </div>
    </div> <!-- panel-body -->
</div> <!-- panel -->

</body>
</html>
