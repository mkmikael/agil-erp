<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'arquivo.label', default: 'Arquivo')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
<div class="panel panel-default">
    <div class="panel-heading">
        <h4><g:message code="default.list.label" args="[entityName]" /></h4>
    </div> <!-- panel-heading -->
    <div class="panel-body">
        <p>
            <g:link action="create" class="btn btn-default">
                <span class="glyphicon glyphicon-plus"></span> <g:message code="default.new.label" args="[entityName]" />
            </g:link>
        </p>

        <g:render template="/layouts/message" />

        <table data-bs-table>
            <thead>
                <th>Código</th>
                <th>Nome</th>
                <th>Tipo Conteúdo</th>
                <th>Tamanho</th>
                <g:sortableColumn property="dateCreated" params="${params}" title="Dt. Criação"/>
                <g:sortableColumn property="lastUpdated" params="${params}" title="Dt. Modificação"/>
            </thead>
            <tbody>
                <g:each in="${arquivoList}" var="arquivo">
                    <tr>
                        <td><g:link action="show" id="${arquivo.id}">${arquivo.codigo}</g:link></td>
                        <td>${arquivo.nome}</td>
                        <td>${arquivo.contentType}</td>
                        <td>${arquivo.tamanho}</td>
                        <td><g:formatDate number="${arquivo.dateCreated}" format="dd/MM/yyyy HH:mm" /></td>
                        <td><g:formatDate number="${arquivo.lastUpdated}" format="dd/MM/yyyy HH:mm" /></td>
                    </tr>
                </g:each>
            </tbody>
        </table>

        <ul class="pagination">
            <g:paginate total="${arquivoCount ?: 0}" />
        </ul>
    </div> <!-- panel-body -->
</div> <!-- panel -->
</body>
</html>