<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'notaFiscal.label', default: 'NotaFiscal')}" />
    <title><g:message code="default.show.label" args="[entityName]" /></title>
</head>
<body>
<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
    </ul>
</div>
<h1><g:message code="default.show.label" args="[entityName]" /></h1>
<g:if test="${flash.message}">
    <div class="message" role="status">${flash.message}</div>
</g:if>
<ol class="property-list">
    <li class="fieldcontain">
        <span class="property-label">Arquivo</span>
        <div class="property-value">${notaFiscal.arquivo}</div>
    </li>
    <li class="fieldcontain">
        <span class="property-label">Id</span>
        <div class="property-value">${notaFiscal.id}</div>
    </li>
    <li class="fieldcontain">
        <span class="property-label">Código</span>
        <div class="property-value">${notaFiscal.codigo}</div>
    </li>
    <li class="fieldcontain">
        <span class="property-label">Tipo</span>
        <div class="property-value">${notaFiscal.tipo}</div>
    </li>
    <li class="fieldcontain">
        <span class="property-label">CPF/CNPJ</span>
        <div class="property-value">${notaFiscal.doc}</div>
    </li>
    <li class="fieldcontain">
        <span class="property-label">Cliente</span>
        <div class="property-value">${notaFiscal.cliente}</div>
    </li>
    <li class="fieldcontain">
        <span class="property-label">Data Emissão</span>
        <div class="property-value"><g:formatDate date="${notaFiscal.dataEmissao}" format="dd/MM/yyyy" /></div>
    </li>
    <li class="fieldcontain">
        <span class="property-label">Data Autorização</span>
        <div class="property-value"><g:formatDate date="${notaFiscal.dataAutorizacao}" format="dd/MM/yyyy" /></div>
    </li>
    <li class="fieldcontain">
        <span class="property-label">Total</span>
        <div class="property-value"><g:formatNumber number="${notaFiscal.total}" type="currency" /> </div>
    </li>
</ol>

<h1>Itens</h1>
<table data-bs-table    >
    <thead>
    <th>Id</th>
    <th>Produto</th>
    <th>Unidade</th>
    <th>Preço</th>
    <th>Quantidade</th>
    <th>Total Nf</th>
    <th>Total</th>
    </thead>
    <tbody>
    <% def total = 0.0 %>
    <% def totalNf = 0.0 %>
    <g:each in="${notaFiscal.itens?.sort { it.produto?.nome }}" var="item">
        <% total += item.preco * item.quantidade %>
        <% totalNf += item.total %>
        <tr>
            <td><g:link>${item.id}</g:link></td>
            <td>${item.produto?.nome}</td>
            <td>${item.unidadeMedida?.nome}</td>
            <td><g:formatNumber number="${item.preco}" type="currency" /></td>
            <td>${item.quantidade}</td>
            <td><g:formatNumber number="${item.total}" type="currency" /></td>
            <td><g:formatNumber number="${item.preco * item.quantidade}" type="currency" /></td>
        </tr>
    </g:each>
    </tbody>
    <tfooter>
        <th></th>
        <th></th>
        <th></th>
        <th></th>
        <th></th>
        <th><g:formatNumber number="${totalNf}" type="currency" /> </th>
        <th><g:formatNumber number="${total}" type="currency" /> </th>
    </tfooter>
</table>
<g:form resource="${this.notaFiscal}" method="DELETE">
    <fieldset class="buttons">
        <g:link class="edit" action="edit" resource="${this.notaFiscal}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
        <input class="delete" type="submit" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
    </fieldset>
</g:form>
</body>
</html>
