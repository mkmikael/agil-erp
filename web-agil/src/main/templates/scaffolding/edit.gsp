<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="\${message(code: '${propertyName}.label', default: '${className}')}" />
    <title><g:message code="default.edit.label" args="[entityName]" /></title>
</head>
<body>
<div class="panel panel-default">
    <div class="panel-heading">
        <h4><g:message code="default.edit.label" args="[entityName]" /></h4>
    </div> <!-- panel-heading -->
    <div class="panel-body">
        <bs:alert />
        <p>
            <bs:listButton entityName="\${entityName}" />
            <bs:createButton entityName="\${entityName}" />
        </p>

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

        <g:form resource="\${this.${propertyName}}" method="PUT">
            <g:hiddenField name="version" value="\${this.${propertyName}?.version}" />
            <f:all bean="${propertyName}"/>
            <p>
                <bs:updateButton />
            </p>
        </g:form>
    </div> <!-- panel-body -->
</div> <!-- panel -->
</body>
</html>
