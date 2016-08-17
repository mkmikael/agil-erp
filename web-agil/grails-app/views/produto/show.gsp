<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'produto.label', default: 'Produto')}" />
    <title><g:message code="default.show.label" args="[entityName]" /></title>
</head>
<body>
<p>
    <g:link class="btn btn-default" action="index">
        <span class="glyphicon glyphicon-list"></span> <g:message code="default.list.label" args="[entityName]" /></g:link>
    <g:link class="btn btn-default" action="create">
        <span class="glyphicon glyphicon-plus"></span> <g:message code="default.new.label" args="[entityName]" /></g:link>
</p>
<div class="panel panel-default">
    <div class="panel-heading">
        <h4><g:message code="default.show.label" args="[entityName]" /></h4>
    </div> <!-- panel-heading -->
    <div class="panel-body">
        <g:render template="/layouts/message" />

        <ul class="properties">
            <li>
                <span class="p-label">Id</span>
                <span class="p-value">${produto.id}</span>
            </li>
            <li>
                <span class="p-label">Nome do Produto</span>
                <span class="p-value">${produto.nome}</span>
            </li>
            <li>
                <span class="p-label">Código</span>
                <span class="p-value">${produto.codigo}</span>
            </li>
            <li>
                <span class="p-label">Código de barra (EAN)</span>
                <span class="p-value">${produto.codigoBarra}</span>
            </li>
            <li>
                <span class="p-label">NCM</span>
                <span class="p-value">${produto.ncm}</span>
            </li>
        </ul>

        <g:form resource="${this.produto}" method="DELETE">
            <fieldset class="buttons">
                <g:link class="btn btn-default" action="edit" resource="${this.produto}">
                    <span class="glyphicon glyphicon-edit"></span> <g:message code="default.button.edit.label" default="Edit" /></g:link>
                <button class="btn btn-default" type="submit" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" >
                    <span class="glyphicon glyphicon-trash"></span> ${message(code: 'default.button.delete.label', default: 'Delete')}
                </button>
            </fieldset>
        </g:form>
    </div> <!-- panel-body -->
</div> <!-- panel -->
</body>
</html>
