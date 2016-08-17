<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="\${message(code: '${propertyName}.label', default: '${className}')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>

    <p>
        <g:link class="btn btn-default" action="index">
            <span class="glyphicon glyphicon-list"></span> <g:message code="default.list.label" args="[entityName]" />
        </g:link>
    </p>
    <div class="panel panel-default">
        <div class="panel-heading">
            <h4><g:message code="default.create.label" args="[entityName]" /></h4>
        </div> <!-- panel-heading -->
        <div class="panel-body">

        <g:render template="/layouts/message" />

        <g:hasErrors bean="\${this.${propertyName}}">
            <div class="alert alert-danger">
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <ul class="list-unstyled" role="alert">
                    <g:eachError bean="\${this.${propertyName}}" var="error">
                        <li <g:if test="\${error in org.springframework.validation.FieldError}">data-field-id="\${error.field}"</g:if>><g:message error="\${error}"/></li>
                    </g:eachError>
                </ul>
            </div>
        </g:hasErrors>

        <g:form action="save">
            <f:all bean="${propertyName}"/>
            <p>
                <g:submitButton name="save" class="btn btn-default" value="\${message(code: 'default.button.create.label', default: 'Create')}" />
            </p>
        </g:form>
        </div> <!-- panel-body -->
    </div> <!-- panel -->

    </body>
</html>
