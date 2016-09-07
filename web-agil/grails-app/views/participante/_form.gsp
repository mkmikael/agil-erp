<%@ page import="web.agil.cadastro.Organizacao; web.agil.cadastro.Pessoa" %>

<g:hiddenField name="participante.id" value="${participante?.id}"  />

<div class="panel panel-default">
    <div class="panel-heading">
        <h5>Dados Gerais</h5>
    </div> <!-- panel-heading -->
    <div class="panel-body">
        <div class="row">
            <bs:fieldGroup name="participante.nome" label="Razão Social" value="${participante?.nome}" classGroup="col-md-3" required="required" />
            <bs:fieldGroup name="participante.nomeFantasia" label="Nome Fantasia" value="${participante?.nomeFantasia}" classGroup="col-md-3" />
            <g:if test="${!participante}">
                <div class="col-md-3">
                    <label class="control-label" for="tipoPessoa">Tipo Pessoa</label>
                    <select id="tipoPessoa" name="tipoPessoa" class="form-control">
                        <option value="PF">Pessoa Física</option>
                        <option value="PJ">Pessoa Juridica</option>
                    </select>
                </div>
            </g:if>
        </div> <!-- .row -->
        <br>
        <g:if test="${participante}">
            <g:if test="${participante.instanceOf(Pessoa)}">
                <div id="pf">
                    <div class="row">
                        <bs:fieldGroup name="participante.cpf" label="CPF" class="cpf" value="${participante?.doc}" classGroup="col-md-3" />
                    </div>
                </div>
            </g:if>
            <g:if test="${participante.instanceOf(Organizacao)}">
                <div id="pj">
                    <div class="row">
                        <bs:fieldGroup name="participante.cnpj" label="CNPJ" class="cnpj" value="${participante?.doc}" classGroup="col-md-3" />
                        <bs:fieldGroup name="participante.inscricaoEstadual" label="Inscrição Estadual" value="${participante?.inscricaoEstadual}" classGroup="col-md-3" />
                    </div>
                </div>
            </g:if>
        </g:if>
        <g:else>
            <div id="pf">
                <div class="row">
                    <bs:fieldGroup name="participante.cpf" label="CPF" class="cpf" value="${participante?.doc}" classGroup="col-md-3" />
                </div>
            </div>
            <div id="pj" hidden>
                <div class="row">
                    <bs:fieldGroup name="participante.cnpj" label="CNPJ" class="cnpj" value="${participante?.doc}" classGroup="col-md-3" />
                    <bs:fieldGroup name="participante.inscricaoEstadual" label="Inscrição Estadual" value="${participante?.inscricaoEstadual}" classGroup="col-md-3" />
                </div>
            </div>
        </g:else>
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
