<%@ page import="web.agil.financeiro.enums.TipoNotaFiscal" %>

<div id="table" class="scrollable-horizontal">
    <table data-bs-table>
        <thead>
        <tr>
            <th colspan="23">Produto</th>
        </tr>
        <tr>
            <th>Janeiro</th>
            <th style="background-color: #eee">%</th>
            <th>Fevereiro</th>
            <th style="background-color: #eee">%</th>
            <th>Março</th>
            <th style="background-color: #eee">%</th>
            <th>Abril</th>
            <th style="background-color: #eee">%</th>
            <th>Maio</th>
            <th style="background-color: #eee">%</th>
            <th>Junho</th>
            <th style="background-color: #eee">%</th>
            <th>Julho</th>
            <th style="background-color: #eee">%</th>
            <th>Agosto</th>
            <th style="background-color: #eee">%</th>
            <th>Setembro</th>
            <th style="background-color: #eee">%</th>
            <th>Outubro</th>
            <th style="background-color: #eee">%</th>
            <th>Novembro</th>
            <th style="background-color: #eee">%</th>
            <th>Dezembro</th>
        </tr>
        </thead>
        <tbody>
        <g:each in="${comparativoAnualList}" var="produtoCmpAnual">
            <tr>
                <td colspan="${produtoCmpAnual.size()}">${produtoCmpAnual[0].produto}</td>
            </tr>
            <tr>
                <g:each in="${produtoCmpAnual}" var="cmpAnual">
                    <g:if test="${cmpAnual instanceof Map}">
                        <td><g:formatNumber number="${cmpAnual.quant}" /></td>
                    </g:if>
                    <g:else>
                        <td style="background-color: #eee"><g:formatNumber number="${cmpAnual}" type="percent" maxFractionDigits="2"  /></td>
                    </g:else>
                </g:each>
            </tr>
        </g:each>
        </tbody>
    </table>
</div>
