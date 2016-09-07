<%@ page import="web.agil.cadastro.UnidadeMedida" %>
<g:hiddenField name="id" value="${produto?.id}" />
<div class="row">
    <bs:fieldGroup classGroup="col-md-3" name="nome" value="${produto?.nome}" label="Nome do Produto" required="required" />
</div>
%{--<p>--}%
    %{--<a data-toggle="collapse" data-target="#filtros"></a>--}%
%{--</p>--}%
<div class="row">
    <bs:fieldGroup classGroup="col-md-3" name="codigo" value="${produto?.codigo}" label="Código" />
    <bs:fieldGroup classGroup="col-md-3" name="codigoBarra" value="${produto?.codigoBarra}" label="Código de barras (EAN)" />
    <bs:fieldGroup classGroup="col-md-3" name="ncm" value="${produto?.ncm}" label="NCM" />
    <bs:fieldGroup classGroup="col-md-3" name="unidadeMedida.id" type="select"
                   value="${produto?.unidadeMedida?.id}" label="Unidade"
                   from="${UnidadeMedida.list(sort: 'nome')}" optionKey="id"/>
</div>
