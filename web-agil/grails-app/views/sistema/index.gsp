<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
</head>
<body>
<h1>Processos</h1>
<g:if test="${resultProc}">
    <div class="alert alert-info alert-dismissible" role="alert">
        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        ${resultProc}
    </div>
</g:if>

<div class="panel panel-default">
    <div class="panel-heading">
        Processamento de Notas Fiscais SEFA
    </div> <!-- panel-heading -->
    <g:form>
        <div class="panel-body">
            <h4>Parâmetros</h4>
            <div class="form-group">
                <label class="control-label">Caminho da Pasta das NFs</label>
                <g:textField class="form-control" name="arquivo.nfs" value="${config.arquivo?.nfs}" />
            </div>
            <g:actionSubmit class="btn btn-default" value="Salvar" action="save" />
        </div> <!-- panel-body -->
        <div class="panel-footer">
            <g:actionSubmit class="btn btn-lg btn-primary" value="Processar" action="processarNfs" />
        </div> <!-- panel-footer -->
    </g:form>
</div> <!-- panel -->

<div class="panel panel-default">
    <div class="panel-heading">
        Processamento de Produtos SEFA
    </div> <!-- panel-heading -->
    <g:form>
        <div class="panel-body">
            <h4>Parâmetros</h4>
            <div class="form-group">
                <label class="control-label">Caminho da Pasta dos Produtos</label>
                <g:textField class="form-control" name="arquivo.produtos" value="${config.arquivo?.produtos}" />
            </div>
            <g:actionSubmit class="btn btn-default" value="Salvar" action="save" />
        </div> <!-- panel-body -->
        <div class="panel-footer">
            <g:actionSubmit class="btn btn-lg btn-primary" value="Processar" action="processarProdutos" />
        </div> <!-- panel-footer -->
    </g:form>
</div> <!-- panel -->

</body>
</html>