<style>
    table td b { padding-left: 8px }
</style>
<table>
    <tr>
        <td>Valor Total Aberto</td>
        <td>
            <b>${g.formatNumber(number: valorTotalAberto ?: 0, locale: 'pt_BR', type: 'currency')}</b>
        </td>
    </tr>
    <tr>
        <td>Valor Total Atrasado</td>
        <td>
            <b>${g.formatNumber(number: valorTotalAtrasado ?: 0, locale: 'pt_BR', type: 'currency')}</b>
        </td>
    </tr>
    <tr>
        <td>Valor Total Pago</td>
        <td>
            <b>${g.formatNumber(number: valorTotalPago ?: 0, locale: 'pt_BR', type: 'currency')}</b>
        </td>
    </tr>
</table>
