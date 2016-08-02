<%@ page import="web.agil.cadastro.Produto; web.agil.cadastro.UnidadeMedida" %>
<tr>
    <td>
        <g:if test="${item?.id}">
            <g:hiddenField name="notaAvulsa.itens[$i].id" value="${item?.id}" />
        </g:if>
        <g:select name="notaAvulsa.itens[$i].produto.id" class="select2"
                  from="${Produto.list(sort: 'nome')}" optionKey="id" optionValue="nome"
                  value="${item?.produto?.id}"/>
    </td>
    <td>
        <g:select name="notaAvulsa.itens[$i].unidadeMedida.id" class="form-control"
                  from="${UnidadeMedida.list(sort: 'nome')}" optionKey="id" optionValue="nome"
                  value="${item?.unidadeMedida?.id}"/>
    </td>
    <td>
        <g:textField name="notaAvulsa.itens[$i].preco" value="${item?.preco}"
                     class="form-control" data-mask="money" data-field="vlUnitario" required="${true}" />
    </td>
    <td>
        <g:textField name="notaAvulsa.itens[$i].quantidade" value="${item?.quantidade}"
                     class="form-control" data-field="quantidade" required="${true}" />
    </td>
    <td data-field="total">
        <g:formatNumber type="currency" number="${(item?.quantidade ?: 0) * (item?.preco ?: 0)}" />
    </td>
    <td>
        <a class="btn btn-default" data-remover="${item?.id}">
            <span class="glyphicon glyphicon-trash"></span>
        </a>
    </td>
</tr>