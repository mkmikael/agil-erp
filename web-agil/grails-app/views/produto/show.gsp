<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'produto.label', default: 'Produto')}" />
    <title><g:message code="default.show.label" args="[entityName]" /></title>
</head>
<body>
<p>
    <bs:listButton entityName="${entityName}" />
    <bs:createButton entityName="${entityName}" />
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

        <fieldset class="buttons">
            <bs:editButton resource="${this.produto}" />
            <bs:deleteButton resource="${this.produto}" />
        </fieldset>
    </div> <!-- panel-body -->
</div> <!-- panel -->
</body>
</html>
