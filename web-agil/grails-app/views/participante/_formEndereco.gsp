<%@ page import="web.agil.cadastro.Cidade" %>
<div class="row">
    <bs:fieldGroup name="endereco.cep" label="CEP" value="${endereco?.cep}" classGroup="col-md-2" />
</div>

<div class="row">
    <bs:fieldGroup name="endereco.numero" label="Nº" value="${endereco?.numero}" classGroup="col-md-1" />
    <bs:fieldGroup name="endereco.logradouro" label="Logradouro" value="${endereco?.logradouro}" classGroup="col-md-5" required="required" />
</div>

<div class="row">
    <bs:fieldGroup name="endereco.complemento" label="Complemento" value="${endereco?.complemento}" classGroup="col-md-5" />
</div>

<div class="row">
    <bs:fieldGroup name="endereco.cidade.id" type="select" label="Cidade"
                   value="${endereco?.cidade?.id}" classGroup="col-md-2" required="required"
                   from="${Cidade.list(sort: 'nome')}" optionKey="id" noSelection="['':'']"  />
    <bs:fieldGroup name="endereco.bairro" label="Bairro" value="${endereco?.bairro}" classGroup="col-md-2" required="required" />
</div>
