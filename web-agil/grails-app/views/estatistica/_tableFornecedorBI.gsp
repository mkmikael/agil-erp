<div>
    <g:render template="/estatistica/search" model="[viewName: 'biFornecedor']" />
</div>
<br>
<table data-bs-table>
    <thead>
    <th>Fornecedor</th>
    <th>Quant.</th>
    <th>Total</th>
    </thead>
    <tbody>
    <% def total = 0.0 %>
    <g:each in="${resumoFornecedores}">
        <% total += it.total %>
        <tr>
            <td>${it.fornecedor?.participante?.nome}</td>
            <td>${it.quant}</td>
            <td><g:formatNumber number="${it.total}" type="currency" /> </td>
        </tr>
    </g:each>
    <tr>
        <th></th>
        <th></th>
        <th><g:formatNumber number="${total}" type="currency" /> </th>
    </tr>
    </tbody>
</table>
