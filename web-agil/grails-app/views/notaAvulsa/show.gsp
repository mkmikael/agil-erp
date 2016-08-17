<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'notaAvulsa.label', default: 'NotaAvulsa')}" />
    <title><g:message code="default.show.label" args="[entityName]" /></title>
</head>
<body>

<div class="panel panel-default">
    <div class="panel-heading">
        <h3><g:message code="default.show.label" args="[entityName]" /></h3>
    </div> <!-- panel-heading -->
    <div class="panel-body">
        <p>
            <g:link class="btn btn-default" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link>
            <g:link class="btn btn-default" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link>
        </p>

        <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
        </g:if>

        <table class="table table-striped">
            <tr>
                <td class="property-label">Id</td>
                <td class="property-value">${notaAvulsa.id}</td>
            </tr>
            <tr>
                <td class="property-label">Número Doc</td>
                <td class="property-value">${notaAvulsa.codigo}</td>
            </tr>
            <tr>
                <td class="property-label">Natureza Op.</td>
                <td class="property-value">${notaAvulsa.tipo}</td>
            </tr>
            <tr>
                <td class="property-label">Forma PGTO</td>
                <td class="property-value">${notaAvulsa.formaPagamento}</td>
            </tr>
            <tr>
                <td class="property-label">Data Emissão</td>
                <td class="property-value"><g:formatDate date="${notaAvulsa.dataEmissao}" format="dd/MM/yyyy HH:mm" /> </td>
            </tr>
            <g:set var="cliente" value="${notaAvulsa.cliente}" />
            <tr>
                <td class="property-label">CPF / CNPJ</td>
                <td class="property-value">${cliente.participante.doc}</td>
            </tr>
            <tr>
                <td class="property-label">Nome / Razão Social</td>
                <td class="property-value">${cliente.participante}</td>
            </tr>
            <tr>
                <td class="property-label">Vendedor</td>
                <td class="property-value">${notaAvulsa.vendedor}</td>
            </tr>
            <tr>
                <td class="property-label">Data de Vencimento</td>
                <td class="property-value"><g:formatDate date="${notaAvulsa.dataVencimento}" format="dd/MM/yyyy" /> </td>
            </tr>
            <tr>
                <td class="property-label">Valor Total</td>
                <td class="property-value"><g:formatNumber number="${notaAvulsa.total}" type="currency" /></td>
            </tr>
        </table>
        <table data-bs-table>
            <thead>
            <th>Descrição Produto</th>
            <th>Unidade</th>
            <th>Quant</th>
            <th>Vl Unitário</th>
            <th>Vl Total</th>
            </thead>
            <tbody>
            <g:each in="${notaAvulsa.itens?.sort { it.id }}" var="item" status="i">
                <tr>
                    <td>${item?.produto}</td>
                    <td>${item?.unidadeMedida?.nome}</td>
                    <td>${item?.quantidade}</td>
                    <td>${g.formatNumber(number: item?.preco, type: 'currency', currencySymbol: '')}</td>
                    <td>${g.formatNumber(number: item?.total, type: 'currency', currencySymbol: '')}</td>
                </tr>
            </g:each>
            </tbody>
        </table>
    </div> <!-- panel-body -->
    <div class="panel-footer">
        <g:form resource="${this.notaAvulsa}" method="DELETE">
            <g:link class="btn btn-default" action="edit" resource="${this.notaAvulsa}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
            <input class="btn btn-default" type="submit" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
            <g:link class="btn btn-default" resource="${this.notaAvulsa}" action="emissao"><span class="glyphicon glyphicon-print"></span> Imprimir</g:link>
        </g:form>
    </div> <!-- panel-footer -->
</div> <!-- panel -->
</body>
</html>
