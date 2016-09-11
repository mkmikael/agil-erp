<%@ page import="org.springframework.validation.FieldError" %>
<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'notaAvulsa.label', default: 'NotaAvulsa')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>

            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>

            <g:hasErrors bean="${this.notaAvulsa}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.notaAvulsa}" var="error">
                <li <g:if test="${error in FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                </g:eachError>
            </ul>
            </g:hasErrors>

            <g:form action="save">
                <g:render template="form" bean="notaAvulsa" model="[clienteList: clienteList]" />
                <g:submitButton name="create" class="btn btn-default" value="${message(code: 'default.button.create.label', default: 'Create')}" />
            </g:form>
    </body>
</html>
