<g:hiddenField name="participante.id" value="${participante?.id}"  />

<div class="panel panel-default">
    <div class="panel-heading">
        <h5>Dados Gerais</h5>
    </div> <!-- panel-heading -->
    <div class="panel-body">
        <div class="row">
            <bs:fieldGroup name="participante.nome" label="Razão Social" value="${participante?.nome}" classGroup="col-md-3" required="required" />
            <bs:fieldGroup name="participante.nomeFantasia" label="Nome Fantasia" value="${participante?.nomeFantasia}" classGroup="col-md-3" />
            <bs:fieldGroup name="participante.doc" label="CPF / CNPJ" value="${participante?.doc}" classGroup="col-md-3" />
        </div> <!-- .row -->
    </div> <!-- panel-body -->
</div> <!-- panel -->

<div class="panel panel-default">
    <div class="panel-heading">
        <h5>Endereço</h5>
    </div> <!-- panel-heading -->
    <div class="panel-body">
        <g:render template="/participante/formEndereco" model="[endereco: participante?.endereco]" />
    </div> <!-- panel-body -->
</div> <!-- panel -->

<div class="panel panel-default">
    <div class="panel-heading">
        <h5>Telefone</h5>
    </div> <!-- panel-heading -->
    <div class="panel-body">
        <g:render template="/participante/formTelefone" model="[telefone: participante?.telefone]" />
    </div> <!-- panel-body -->
</div> <!-- panel -->
