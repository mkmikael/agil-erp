<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'cliente.label', default: 'Cliente')}" />
    <title><g:message code="default.show.label" args="[entityName]" /></title>
</head>
<body>
<p>
    <g:link class="btn btn-default" action="index">
        <span class="glyphicon glyphicon-list"></span> <g:message code="default.list.label" args="[entityName]" />
    </g:link>
    <g:link class="btn btn-default" action="create">
        <span class="glyphicon glyphicon-plus"></span> <g:message code="default.new.label" args="[entityName]" />
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
                <span class="p-label">Id</span>
                <span class="p-value">${cliente.id}</span>
            </li>
            <li>
                <span class="p-label">Código</span>
                <span class="p-value">${cliente.codigo}</span>
            </li>
            <li>
                <span class="p-label">Status</span>
                <span class="p-value">${cliente.statusPapel}</span>
            </li>
            <g:render template="/participante/show" model="[participante: cliente.participante]" />
        </ul>

        <g:link class="btn btn-default" action="edit" resource="${this.cliente}">
            <span class="glyphicon glyphicon-edit"></span> <g:message code="default.button.edit.label" default="Edit" />
        </g:link>
        <g:form controller="papel" action="inativar" id="${cliente.id}" method="POST" style="display: inline-block">
            <button class="btn btn-default" type="submit" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" >
                <span class="glyphicon glyphicon"></span> Inativar
            </button>
        </g:form>
        <g:form controller="papel" action="ativar" id="${cliente.id}" method="POST" style="display: inline-block">
            <button class="btn btn-default" type="submit" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" >
                <span class="glyphicon glyphicon"></span> Ativar
            </button>
        </g:form>
        <g:form></g:form>
    </div> <!-- panel-body -->
</div> <!-- panel -->

</body>
</html>
