<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="\${message(code: '${propertyName}.label', default: '${className}')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
    <p>
        <g:link class="btn btn-default" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link>
        <g:link class="btn btn-default" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link>
    </p>

    <div class="panel panel-default">
        <div class="panel-heading">
            <h4><g:message code="default.show.label" args="[entityName]" /></h4>
        </div> <!-- panel-heading -->
        <div class="panel-body">
            <g:render template="/layouts/message" />

            <f:display bean="${propertyName}" />

            <g:form resource="\${this.${propertyName}}" method="DELETE">
                <p>
                    <g:link class="btn btn-default" action="edit" resource="\${this.${propertyName}}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
                    <input class="btn btn-default" type="submit" value="\${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('\${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                </p>
            </g:form>
        </div> <!-- panel-body -->
    </div> <!-- panel -->

    </body>
</html>
