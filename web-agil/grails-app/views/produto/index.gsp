<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'produto.label', default: 'Produto')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
<p>
    <g:link class="btn btn-default" action="create">
        <span class="glyphicon glyphicon-plus"></span> <g:message code="default.new.label" args="[entityName]" />
    </g:link>
</p>
<div class="panel panel-default">
    <div class="panel-heading">
        <h4><g:message code="default.list.label" args="[entityName]" /></h4>
    </div> <!-- panel-heading -->

    <div class="panel-body">
        <g:render template="/layouts/message" />

        <div class="panel panel-default">
            <div class="panel-heading">
                <a data-toggle="collapse" data-target="#filtros"><span class="glyphicon glyphicon-collapse-down"></span> Filtros</a>
            </div> <!-- panel-heading -->
            <div id="filtros" class="panel-body collapse in">
                <g:form>
                    <div class="row">
                        <bs:fieldGroup label="Id" name="id" classGroup="col-md-1" value="${id}" />
                        <bs:fieldGroup label="Código" name="codigo" classGroup="col-md-2" value="${codigo}" />
                        <bs:fieldGroup label="NCM" name="ncm" classGroup="col-md-2" value="${ncm}" />
                        <bs:fieldGroup label="Nome" name="nome" classGroup="col-md-3" value="${nome}" />
                        %{--<bs:fieldGroup label="Fornecedor" type="select" name="fornecedor"--}%
                                       %{--classGroup="col-md-3" value="${fornecedor}"--}%
                                       %{--from="${fornecedorList}" optionKey="id" noSelection="['':'']" />--}%
                    </div>
                    <button type="submit" class="btn btn-default">
                        <span class="glyphicon glyphicon-search"></span> Procurar
                    </button>
                </g:form>
            </div> <!-- panel-body -->
        </div> <!-- panel -->

        <table data-bs-table>
            <thead>
            <th>Id</th>
            <g:sortableColumn property="nome" params="${params}" title="Nome" />
            <th>Código</th>
            <th>EAN</th>
            <th>NCM</th>
            %{--<g:sortableColumn property="fornecedor.participante.nome" params="${params}" title="Fornecedor" />--}%
            </thead>
            <tbody>
            <g:each in="${produtoList}" var="produto">
                <tr>
                    <td><g:link id="${produto.id}" action="show">${produto.id}</g:link></td>
                    <td>${produto.nome}</td>
                    <td>${produto.codigo}</td>
                    <td>${produto.codigoBarra}</td>
                    <td>${produto.ncm}</td>
                    %{--<td>${produto.fornecedores}</td>--}%
                </tr>
            </g:each>
            </tbody>
        </table>

        <div class="pagination">
            <g:paginate total="${produtoCount ?: 0}" params="${params}" />
        </div>
    </div> <!-- panel-body -->
</div> <!-- panel -->
</body>
</html>