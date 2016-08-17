<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="\${message(code: '${propertyName}.label', default: '${className}')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
<div class="panel panel-default">
    <div class="panel-heading">
        <h4><g:message code="default.list.label" args="[entityName]" /></h4>
    </div> <!-- panel-heading -->
    <div class="panel-body">
        <p>
            <g:link action="create" class="btn btn-default">
                <span class="glyphicon glyphicon-plus"></span> <g:message code="default.new.label" args="[entityName]" />
            </g:link>
        </p>

        <g:render template="/layouts/message" />

        <g:if test="\${${propertyName}List}">
            <f:table collection="\${${propertyName}List}" />
        </g:if>
        <g:else>
            <div class="well">Sem registros</div>
        </g:else>

        <ul class="pagination">
            <g:paginate total="\${${propertyName}Count ?: 0}" />
        </ul>
    </div> <!-- panel-body -->
</div> <!-- panel -->
</body>
</html>