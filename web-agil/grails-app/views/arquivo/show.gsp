<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'arquivo.label', default: 'Arquivo')}" />
    <title><g:message code="default.show.label" args="[entityName]" /></title>
</head>
<body>
<p>
    <g:link class="btn btn-default" action="index">
        <span class="glyphicon glyphicon-list"></span> <g:message code="default.list.label" args="[entityName]" />
    </g:link>
    <g:link class="btn btn-default" action="create">
        <span class="glyphicon glyphicon-plus"></span>  <g:message code="default.new.label" args="[entityName]" />
    </g:link>
</p>
<div class="panel panel-default">
    <div class="panel-heading">
        <h4><g:message code="default.show.label" args="[entityName]" /></h4>
    </div> <!-- panel-heading -->
    <div class="panel-body">
        <g:render template="/layouts/message" />

        <ul class="properties">
            <li>
                <span class="p-label">Código</span>
                <span class="p-value">${arquivo.codigo}</span>
            </li>
            <li>
                <span class="p-label">Nome</span>
                <span class="p-value">${arquivo.nome}</span>
            </li>
            <li>
                <span class="p-label">Tipo de Conteúdo</span>
                <span class="p-value">${arquivo.contentType}</span>
            </li>
            <li>
                <span class="p-label">Tamanho</span>
                <span class="p-value">${arquivo.tamanho}</span>
            </li>
            <g:if test="${arquivo.caminho}">
                <li>
                    <span class="p-label">Caminho</span>
                    <span class="p-value">${arquivo.caminho}</span>
                </li>
            </g:if>
            <li>
                <span class="p-label">Data de Criação</span>
                <span class="p-value"><g:formatDate number="${arquivo.dateCreated}" format="dd/MM/yyyy HH:mm" /></span>
            </li>
            <li>
                <span class="p-label">Data de Modificação</span>
                <span class="p-value"><g:formatDate number="${arquivo.lastUpdated}" format="dd/MM/yyyy HH:mm" /></span>
            </li>
        </ul>

        %{--<g:if test="${arquivo.contentType in ['application/octet-stream']}">--}%
            %{--<h4>Conteúdo</h4>--}%
            %{--<textarea class="form-control" style="height: 300px">--}%
               %{--${arquivo.text}--}%
            %{--</textarea>--}%
        %{--</g:if>--}%

        <br>
        <g:form resource="${this.arquivo}" method="DELETE">
            <p>
                %{--<g:link class="btn btn-default" action="edit" resource="${this.arquivo}"><g:message code="default.button.edit.label" default="Edit" /></g:link>--}%
                %{--<input class="btn btn-default" type="submit" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />--}%
                <g:link class="btn btn-default" action="download" id="${arquivo.id}" target="_blank">
                    <span class="glyphicon glyphicon-download"></span> Download
                </g:link>
                <g:if test="${arquivo.contentType in ['application/pdf']}">
                    <g:link class="btn btn-default" action="visualizarConteudo" id="${arquivo.id}" target="_blank">
                        <span class="glyphicon glyphicon-search"></span> Visualizar Conteúdo
                    </g:link>
                </g:if>
            </p>
        </g:form>
    </div> <!-- panel-body -->
</div> <!-- panel -->

</body>
</html>
