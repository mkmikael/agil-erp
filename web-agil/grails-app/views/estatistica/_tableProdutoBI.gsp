<div>
    <g:render template="/estatistica/search" model="[viewName: 'biProduto']" />
</div>
<br>
<table data-bs-table>
    <thead>
    <th>Fornecedor</th>
    <th>Produto</th>
    <th>Quant. Boni.</th>
    <th>Quantidade</th>
    <th>Total</th>
    </thead>
    <tbody>
    <% total = 0.0 %>
    <g:each in="${itemNfList}">
        <% total += it.total %>
        <tr>
            <td>${it.produto?.fornecedor?.participante?.nome}</td>
            <td>${it.produto.nome}</td>
            <td>${it.quant_boni}</td>
            <td>${it.quant}</td>
            <td><g:formatNumber number="${it.total}" type="currency" /> </td>
        </tr>
    </g:each>
    <tr>
        <th></th>
        <th></th>
        <th></th>
        <th><g:formatNumber number="${total}" type="currency" /> </th>
    </tr>
    </tbody>
</table>
