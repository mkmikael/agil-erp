<div class="row">
    <div class="form-group col-md-6">
        <label class="control-label" for="participante.cep">CEP</label>
        <g:textField class="form-control" name="participante.cep" value="${endereco.cep}" />
    </div>
</div>

<div class="row">
    <div class="form-group col-md-6">
        <label class="control-label" for="participante.endereco">Endereço</label>
        <g:textField class="form-control" name="participante.endereco" value="${endereco.logradouro}" />
    </div>

    <div class="form-group col-md-6">
        <label class="control-label" for="participante.bairro">Bairro</label>
        <g:textField class="form-control" name="participante.bairro" value="${endereco.bairro}" />
    </div>
</div>

<div class="row">
    <div class="form-group col-md-6">
        <label class="control-label" for="participante.cidade">Cidade</label>
        <g:textField class="form-control" name="participante.cidade" value="${endereco.cidade}" />
    </div>

    <div class="form-group col-md-6">
        <label class="control-label" for="participante.uf">UF</label>
        <g:textField class="form-control" name="participante.uf" value="${endereco.uf}" />
    </div>
</div>