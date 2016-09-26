<%@ page import="web.agil.financeiro.planoPagamento.IntervaloPagamento; web.agil.financeiro.planoPagamento.ConjuntoIntervaloPgto; web.agil.cadastro.Vendedor; web.agil.financeiro.enums.TipoNotaFiscal; web.agil.cadastro.enums.FormaPagamento; web.agil.cadastro.Cliente" %>
<asset:javascript src="web-agil/notaAvulsa/form" />

<g:hiddenField name="itemSize" value="${notaAvulsa.itens?.size()}" />
<g:hiddenField name="oldId" value="${oldId}" />


<div class="panel panel-default">
    <div class="panel-heading">Dados Gerais</div>
    <div class="panel-body">
        <div class="row">
            <div class="col-md-6">
                <div class="form-group">
                    <label class="control-label" for="notaAvulsa.tipo">Natureza da Operação</label>
                    <g:select name="notaAvulsa.tipo" class="form-control" from="${TipoNotaFiscal.values()}" value="${notaAvulsa.tipo}" />
                </div>

                <div class="form-group">
                    <label class="control-label" for="notaAvulsa.dataEmissao">Data Emissão</label>
                    <div>
                        <g:datePicker name="notaAvulsa.dataEmissao" value="${notaAvulsa.dataEmissao}" precision="day" />
                    </div>
                </div>

                <div class="form-group">
                    <label class="control-label" for="notaAvulsa.formaPagamento">Forma de PGTO</label>
                    <g:select name="notaAvulsa.formaPagamento" class="form-control" from="${FormaPagamento.values()}" value="${notaAvulsa.formaPagamento}" />
                </div>

                <bs:fieldGroup name="intervalos" label="Prazo" type="multiple"
                               class="selectpicker" classGroup="intervalosGroup"
                               from="${IntervaloPagamento.findAllByAtivo(true, [sort: 'dias'])}"
                               optionKey="id" optionValue="dias" noSelection="['': '']" />
            </div>

            <div class="col-md-6">
                <div class="form-group">
                    <label class="control-label" for="notaAvulsa.cliente.id">Cliente</label>
                    <g:select name="notaAvulsa.cliente.id" class="form-control select2"
                              from="${clienteList}" value="${notaAvulsa.cliente?.id}"
                              optionKey="id" optionValue="participante"
                              noSelection="['': '']" required=""/>
                </div>

                <div class="form-group">
                    <label class="control-label" for="notaAvulsa.tipo">Vendedor</label>
                    <g:select name="notaAvulsa.vendedor.id" class="form-control"
                              from="${Vendedor.list()}" value="${notaAvulsa.tipo}"
                              optionKey="id" optionValue="${{it.participante.nome}}" noSelection="['':'']"/>
                </div>
            </div>
        </div> <!-- row -->
    </div> <!-- panel-body -->
</div>  <!-- panel -->

<div class="panel panel-default">
    <div class="panel-heading">Itens</div>
    <div class="panel-body">
        <button id="add" type="button" class="btn btn-default">Adicionar</button>

        <table id="tableItens" class="table">
            <thead>
            <th>Descrição Prod.</th>
            <th>Unidade</th>
            <th>Vl Unitário</th>
            <th>Quantidade</th>
            <th>Vl Total</th>
            <th></th>
            </thead>
            <tbody>
            <g:each in="${notaAvulsa.itens?.sort { it.id }}" var="item" status="i">
                <g:render template="itemLinha" model="[i: i, item: item]" />
            </g:each>
            </tbody>
        </table>
    </div> <!-- panel-body -->
</div> <!-- panel -->
