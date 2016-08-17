<%@ page import="web.agil.cadastro.Organizacao; web.agil.financeiro.enums.TipoNotaFiscal" %>
<!DOCTYPE html>
<html>
<head>
    <title>Nota Avulsa</title>
    <asset:stylesheet src="bootstrap" />
</head>
<body>
<style>
.page {
    font-family: Arial;
    margin: 0 auto;
    width: 912px;
    padding: 0;
}
table {
    width: 100%;
    border: 2px solid #666;
    border-spacing: 0;
    border-collapse: collapse;
}
table th {
    background-color: #bcbcbc;
    border-top: 2px solid #555;
    border-bottom: 2px solid #555;
    font-weight: bolder;
    text-align: left;
    padding-left: .5em;
    font-size: .7em;
}

table td { text-align: left; }

.field { display: inline-block; margin: 2px 6px 2px 0  }

.label {
    border: none;
    display: block;
    color: inherit;
    text-align: left;
    font-size: .8em;
    padding-left: .5em;
}
.value {
    display: block;
    font-size: 1.1em;
    font-weight: bolder;
    padding-left: .6em;
}

.header { font-size: 1em; }
.header td { border: 2px solid #666; }

.linha { font-size: 1em; }
.linha td {
    border-right: 2px solid #666;
    border-left: 2px solid #666;
}
.linha:nth-of-type(odd) { background-color: #ddd; }
</style>
<div class="page">
    <span style="font-size: 1.4em; font-weight: bolder">Ágil Distribuições</span>
    <span style="font-size: .9em; display: block">TV. Santo Antônio, 507 - Centro - Barcarena - PA - CEP: 68445-000</span>
    <br>
    <table id="dadosGerais">
        <tr>
            <th colspan="4">Destinatário / Remetente</th>
        </tr>
        <tr>
            <g:set var="cliente" value="${notaAvulsa.cliente}" />
            <td>
                <span class="label">Nome / Razão Social</span>
                <span id="_nome" class="value">${cliente?.participante?.nome}</span>
            </td>
            <td>
                <span class="label">CNPJ / CPF</span>
                <span id="_doc" class="value">${cliente?.participante?.doc}</span>
            </td>
            <td colspan="2">
                <span class="label">Data de Emissão</span>
                <span class="value">${notaAvulsa.dataEmissao?.format('dd/MM/yyyy')}</span>
            </td>
        </tr>
        <tr>
            <g:set var="endereco" value="${cliente?.participante?.endereco}" />
            <td>
                <span class="label">Endereço</span>
                <span id="_endereco.logradouro" class="value">${endereco?.logradouro}</span>
            </td>
            <td>
                <span class="label">Bairro / Distrito</span>
                <span id="_endereco.bairro" class="value">${endereco?.bairro}</span>
            </td>
            <td colspan="2">
                <span class="label">CEP</span>
                <span id="_endereco.cep" class="value">${endereco?.cep}</span>
            </td>
        </tr>
        <tr>
            <td>
                <span class="label">Município</span>
                <span id="_cidade.nome" class="value">${endereco?.cidade}</span>
            </td>
            <td>
                <span class="label">Fone / Fax</span>
                <span id="_fone" class="value">${cliente?.participante?.telefone}</span>
            </td>
            <g:if test="${cliente?.participante?.instanceOf(Organizacao)}">
                <td>
                    <span class="label">UF</span>
                    <span id="_uf" class="value">${endereco?.cidade?.estado}</span>
                </td>
                <td>
                    <span class="label">Inscrição Estadual</span>
                    <span id="_inscricaoEstadual" class="value">${cliente?.participante?.inscricaoEstadual}</span>
                </td>
            </g:if>
            <g:else>
                <td colspan="2">
                    <span class="label">UF</span>
                    <span id="_uf" class="value">${endereco?.cidade?.estado}</span>
                </td>
            </g:else>
        </tr>
        <tr>
            <td>
                <span class="label">Forma PGTO</span>
                <span id="_formaPagamento" class="value">${notaAvulsa.formaPagamento}</span>
            </td>
            <td colspan="3">
                <span class="label">Natureza Op.</span>
                <span id="_formaPagamento" class="value">${notaAvulsa.tipo?.nome}</span>
            </td>
        </tr>
        <tr>
            <th colspan="4">Fatura(s)</th>
        </tr>
        <tr>
            <td>
                <span class="field">
                    <span class="label">Nº do Documento</span>
                    <span class="value">${notaAvulsa.codigo}</span>
                </span>
                <span class="field">
                    <span class="label">Data Vencimento</span>
                    <span class="value">${notaAvulsa.dataVencimento?.format('dd/MM/yyyy')}</span>
                </span>
                <span class="field">
                    <span class="label">Valor</span>
                    <span class="value"><g:formatNumber number="${notaAvulsa.total}" type="currency" /></span>
                </span>
            </td>
            <td colspan="3"></td>
        </tr>
    </table>
    <table id="itens" style="border-top: none">
        <tr>
            <th style="border-top: none" colspan="6">Dados do(s) Produto(s)</th>
        </tr>
        <tr class="header">
            <td>Descrição Produto</td>
            <td>Unidade</td>
            <td>Quant</td>
            <td>Vl Unitário</td>
            <td>Vl Total</td>
        </tr>
        <g:each in="${notaAvulsa.itens?.sort { it.id }}" var="item" status="i">
            <tr class="linha">
                <td>${item.produto}</td>
                <td>${item.unidadeMedida}</td>
                <td>${item.quantidade}</td>
                <td><g:formatNumber number="${item.preco}" type="currency" currencySymbol="" /></td>
                <td><g:formatNumber number="${item.total}" type="currency" currencySymbol="" /></td>
            </tr>
        </g:each>
    </table>
    <p style="font-size: .6em">**Documento sem valor fiscal</p>

    <br><br><br><br>

    <div style="border: 2px solid #666">
        <span class="field">
            <span class="label">DATA RECEBIMENTO</span>
            <span class="value">__/__/____</span>
        </span>
        <span class="field">
            <span class="label">IDENTIFICAÇÃO E ASSINATURA DO RECEBEDOR</span>
            <span class="value">*</span>
        </span>
        <div style="margin: 6px; font-weight: bold">
            Nº do Documento: ${notaAvulsa.codigo}
        </div>
        <div style="margin: 6px; font-weight: bold">
            Nome / Razão Social: ${cliente?.participante?.nome}
        </div>
        <div style="margin: 6px; font-weight: bold">
            CNPJ / CPF: ${cliente?.participante?.doc}
        </div>
        <div style="margin: 6px; font-weight: bold">
            Valor: <g:formatNumber number="${notaAvulsa.total}" type="currency" />
        </div>
    </div>
</div>

%{--<div id="formNotaAvulsa" class="modal fade" tabindex="-1" role="dialog">--}%
    %{--<div class="modal-dialog modal-lg">--}%
        %{--<div class="modal-content">--}%
            %{--<div class="modal-header">--}%
                %{--<button type="button" class="close" data-dismiss="modal">--}%
                    %{--<span>&times;</span>--}%
                %{--</button>--}%
                %{--<h4>Nota Avulsa</h4>--}%
            %{--</div>--}%
            %{--<div class="modal-body">--}%
                %{--<div class="row">--}%
                    %{--<div class="form-group col-md-3">--}%
                        %{--<label class="control-label">CNPJ/CPF</label>--}%
                        %{--<div class="form-inline">--}%
                            %{--<g:textField name="doc" class="form-control" />--}%
                        %{--</div>--}%
                    %{--</div>--}%
                    %{--<div class="form-group col-md-3">--}%
                        %{--<label class="control-label">Inscrição Estadual</label>--}%
                        %{--<div class="form-inline">--}%
                            %{--<g:textField name="inscricaoEstadual" class="form-control" />--}%
                        %{--</div>--}%
                    %{--</div>--}%
                    %{--<div class="form-group col-md-3">--}%
                        %{--<label class="control-label">Fone/Fax</label>--}%
                        %{--<div class="form-inline">--}%
                            %{--<g:textField name="fone" class="form-control" />--}%
                        %{--</div>--}%
                    %{--</div>--}%
                %{--</div>--}%
                %{--<div class="form-group">--}%
                    %{--<label class="control-label">Nome/Razão Social</label>--}%
                    %{--<g:textField name="nome" class="form-control" />--}%
                %{--</div>--}%

                %{--<div class="form-group">--}%
                    %{--<label class="control-label">CEP</label>--}%
                    %{--<div class="form-inline">--}%
                        %{--<g:textField name="endereco.cep" class="form-control" />--}%
                    %{--</div>--}%
                %{--</div>--}%
                %{--<div class="row">--}%
                    %{--<div class="form-group col-md-8">--}%
                        %{--<label class="control-label">Endereço</label>--}%
                        %{--<g:textField name="endereco.logradouro" class="form-control" />--}%
                    %{--</div>--}%
                    %{--<div class="form-group col-md-4">--}%
                        %{--<label class="control-label">Bairro/Distrito</label>--}%
                        %{--<g:textField name="endereco.bairro" class="form-control" />--}%
                    %{--</div>--}%
                %{--</div>--}%
                %{--<div class="row">--}%
                    %{--<div class="form-group col-md-10">--}%
                        %{--<label class="control-label">Cidade</label>--}%
                        %{--<g:textField name="cidade.nome" class="form-control" />--}%
                    %{--</div>--}%
                    %{--<div class="form-group col-md-2">--}%
                        %{--<label class="control-label">Estado</label>--}%
                        %{--<g:textField name="uf" class="form-control" size="3" />--}%
                    %{--</div>--}%
                %{--</div>--}%
            %{--</div>--}%
            %{--<div class="modal-footer">--}%
                %{--<button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>--}%
                %{--<button type="button" class="btn btn-default" data-dismiss="modal" onclick="saveDadosGerais()">Salvar</button>--}%
            %{--</div>--}%
        %{--</div> <!-- modal-content -->--}%
    %{--</div> <!-- modal-dialog -->--}%
%{--</div> <!-- modal -->--}%

%{--<div id="formItemNotaAvulsa" class="modal fade" tabindex="-1" role="dialog">--}%
    %{--<div class="modal-dialog modal-lg">--}%
        %{--<div class="modal-content">--}%
            %{--<div class="modal-header">--}%
                %{--<button type="button" class="close" data-dismiss="modal">--}%
                    %{--<span>&times;</span>--}%
                %{--</button>--}%
                %{--<h4>Nota Avulsa</h4>--}%
            %{--</div>--}%
            %{--<div class="modal-body">--}%
                %{--<div class="row">--}%
                    %{--<div class="form-group col-md-8">--}%
                        %{--<label class="control-label">Produto</label>--}%
                        %{--<g:textField name="produto.nome" class="form-control" />--}%
                    %{--</div>--}%
                    %{--<div class="form-group col-md-4">--}%
                        %{--<label class="control-label">Unidade</label>--}%
                        %{--<g:textField name="unidadeMedida.nome" class="form-control" />--}%
                    %{--</div>--}%
                %{--</div>--}%

                %{--<div class="row">--}%
                    %{--<div class="form-group col-md-3">--}%
                        %{--<label class="control-label">Quantidade</label>--}%
                        %{--<g:textField name="quantidade" class="form-control" />--}%
                    %{--</div>--}%
                    %{--<div class="form-group col-md-3">--}%
                        %{--<label class="control-label">Valor Unitário</label>--}%
                        %{--<g:textField name="valorUnitario" class="form-control" />--}%
                    %{--</div>--}%
                    %{--<div class="form-group col-md-3">--}%
                        %{--<label class="control-label">Valor Desconto</label>--}%
                        %{--<g:textField name="valorDesconto" class="form-control" />--}%
                    %{--</div>--}%
                %{--</div>--}%
                %{--<div class="form-group">--}%
                    %{--<label class="control-label">Total</label>--}%
                    %{--<span id="subtotal">R$ 0,00</span>--}%
                %{--</div>--}%
            %{--</div>--}%
            %{--<div class="modal-footer">--}%
                %{--<button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>--}%
                %{--<button type="button" class="btn btn-default" data-dismiss="modal" onclick="addItem()">Salvar</button>--}%
            %{--</div>--}%
        %{--</div> <!-- modal-content -->--}%
    %{--</div> <!-- modal-dialog -->--}%
%{--</div> <!-- modal -->--}%

<asset:javascript src="application" />

<script>

    function addItem() {
        var produto = $('#produto\\.nome').val();
        var unidade = $('#unidadeMedida\\.nome').val();
        var quant = $('#quantidade').val();
        var valor = $('#valorUnitario').val().replace(',', '.');
        var total = parseFloat(quant) * parseFloat(valor);
        total = total.toFixed(2).toString().replace('.', ',');
        $('#itens').append("<tr class='linha'>" +
                "<td>"+ produto +"</td>" +
                "<td>"+ unidade +"</td>" +
                "<td>"+ quant +"</td>" +
                "<td>"+ valor +"</td>" +
                "<td>0,00</td>" +
                "<td>"+ total +"</td>" +
                "</tr>")
    }

    function saveDadosGerais() {
        $('#formNotaAvulsa input').each(function (i, e) {
            $('#_' + e.name.replace('.', '\\.')).html(e.value);
        });
    }

    $(function () {
        $('.linha').click(function (event) {
            if (event.ctrlKey)
                $(this).remove();
        });

        $('#itens').click(function (event) {
            if (!event.ctrlKey)
                $('#formItemNotaAvulsa').modal()
        });

        $('#dadosGerais').click(function (event) {
            $('span[id]').each(function (i, e) {
                var selector = '#' + e.id
                                .replace('.', '\\.')
                                .replace('_', '');
                $(selector).val(e.innerHTML);
            });
            $('#formNotaAvulsa').modal();
        });

        $('#quantidade, #valorUnitario').keyup(function () {
            var quant = $('#quantidade').val().replace(',', '.');
            var valor = $('#valorUnitario').val().replace(',', '.');
            console.log(valor);
            console.log(quant);
            if (quant && valor) {
                quant = parseFloat(quant);
                valor = parseFloat(valor);
                var total = quant * valor;
                total = total.toFixed(2).toString().replace('.', ',');
                $('#subtotal').html("R$ " + total);
            } else {
                $('#subtotal').html("R$ 0,00");
            }
        });
    });
</script>

</body>
</html>
