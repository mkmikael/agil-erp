<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'arquivo.label', default: 'Arquivo')}" />
    <title><g:message code="default.edit.label" args="[entityName]" /></title>
</head>
<body>

<div class="panel panel-default">
    <div class="panel-heading">
        <h3><g:message code="default.edit.label" args="[entityName]" /></h3>
    </div> <!-- panel-heading -->
    <div class="panel-body">
        <p>
            <g:link class="btn btn-default" action="index">
                <span class="glyphicon glyphicon-list"></span> <g:message code="default.list.label" args="[entityName]" />
            </g:link>
            <g:link class="btn btn-default" action="create">
                <span class="glyphicon glyphicon-plus"></span> <g:message code="default.new.label" args="[entityName]" />
            </g:link>
        </p>

        <g:if test="${flash.message}">
            <div class="alert alert-${flash.message.type}" role="alert">
                ${flash.message.text}
            </div>
        </g:if>

        <g:hasErrors bean="${this.arquivo}">
            <div class="alert alert-danger">
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <ul class="list-unstyled" role="alert">
                    <g:eachError bean="${this.arquivo}" var="error">
                        <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                    </g:eachError>
                </ul>
            </div>
        </g:hasErrors>

        <g:form resource="${this.arquivo}" method="PUT">
            <g:hiddenField name="version" value="${this.arquivo?.version}" />
            <f:all bean="arquivo"/>
            <p>
                <input type="submit" class="btn btn-default" value="${message(code: 'default.button.update.label', default: 'Update')}" />
            </p>
        </g:form>
    </div> <!-- panel-body -->
</div> <!-- panel -->
</body>
</html>
