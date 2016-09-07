<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'produto.label', default: 'Produto')}" />
    <title><g:message code="default.edit.label" args="[entityName]" /></title>
</head>
<body>
<p>
    <bs:listButton entityName="${entityName}" />
    <bs:createButton entityName="${entityName}" />
</p>
<div class="panel panel-default">
    <div class="panel-heading">
        <h4><g:message code="default.edit.label" args="[entityName]" /></h4>
    </div> <!-- panel-heading -->
    <div class="panel-body">
        <g:render template="/layouts/message" />

        <g:hasErrors bean="${this.produto}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.produto}" var="error">
                    <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                </g:eachError>
            </ul>
        </g:hasErrors>

        <g:form resource="${this.produto}" method="PUT">
            <g:hiddenField name="version" value="${this.produto?.version}" />
            <fieldset class="form">
                <g:render template="form" model="[produto: produto]" />
            </fieldset>
            <fieldset class="buttons">
                <bs:updateButton />
            </fieldset>
        </g:form>
    </div> <!-- panel-body -->
</div> <!-- panel -->
</body>
</html>
