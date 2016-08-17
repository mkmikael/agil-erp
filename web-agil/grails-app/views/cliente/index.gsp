<%@ page import="web.agil.cadastro.Organizacao; web.agil.cadastro.Pessoa; web.agil.cadastro.enums.StatusPapel" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'cliente.label', default: 'Cliente')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
<p>
    <a class="btn btn-default" href="${createLink(uri: '/')}">
        <span class="glyphicon glyphicon-home"></span> <g:message code="default.home.label"/>
    </a>
    <g:link class="btn btn-default" action="create">
        <span class="glyphicon glyphicon-plus"></span> <g:message code="default.new.label" args="[entityName]" />
    </g:link>
</p>

<div class="panel panel-default">
    <div class="panel-heading">
        <h4><g:message code="default.list.label" args="[entityName]" /></h4>
    </div> <!-- panel-heading -->

    <div class="panel-body">

        <div class="panel panel-default">
            <div class="panel-heading">
                <a data-toggle="collapse" data-target="#filtros"><span class="glyphicon glyphicon-collapse-down"></span> Filtros</a>
            </div> <!-- panel-heading -->
            <div id="filtros" class="panel-body collapse in">
                <g:form>
                    <div class="row">
                        <bs:fieldGroup label="Id" name="id" classGroup="col-md-1" value="${id}" />
                        <bs:fieldGroup label="Código" name="codigo" classGroup="col-md-2" value="${codigo}" />
                        <bs:fieldGroup label="Razão Social" name="nome" classGroup="col-md-3" value="${nome}" />
                        <bs:fieldGroup label="Nome Fantasia" name="nomeFantasia" classGroup="col-md-3" value="${nomeFantasia}" />
                        <bs:fieldGroup label="CPF/CNPJ" name="doc" classGroup="col-md-3" value="${doc}" />
                    </div>
                    <div class="row">
                        <bs:fieldGroup type="select" label="Status" name="statusPapel" classGroup="col-md-2"
                                       value="${statusPapel}" from="${StatusPapel.values()}" />
                    </div>
                    <button type="submit" class="btn btn-default">
                        <span class="glyphicon glyphicon-search"></span> Procurar
                    </button>
                </g:form>
            </div> <!-- panel-body -->
        </div> <!-- panel -->

        <g:render template="/layouts/message" />

        <table data-bs-table>
            <thead>
            <th>Id</th>
            <g:sortableColumn title="Dt Criação" property="dateCreated" params="${params}" />
            <g:sortableColumn title="Código" property="codigo" params="${params}" />
            <th>CPF/CNPJ</th>
            <g:sortableColumn title="Razão Social" property="participante.nome" params="${params}" />
            <g:sortableColumn title="Nome Fantasia" property="participante.nomeFantasia" params="${params}" />
            <th>Endereço</th>
            <g:sortableColumn title="Status" property="statusPapel" params="${params}" />
            </thead>
            <tbody>
            <g:each in="${clienteList}" var="c">
                <tr>
                    <td>
                        <g:link action="show" id="${c.id}">${c.id}</g:link>
                    </td>
                    <td><g:formatDate number="${c.dateCreated}" format="dd/MM/yyyy" /></td>
                    <td>${c.codigo}</td>
                    <g:set var="p" value="${c.participante}" />
                    <g:if test="${p instanceof Pessoa}">
                        <td class="cpf">${p.doc}</td>
                    </g:if>
                    <g:elseif test="${p instanceof Organizacao}">
                        <td class="cnpj">${p.doc}</td>
                    </g:elseif>
                    <g:else>
                        <td></td>
                    </g:else>
                    <td>${p.nome}</td>
                    <td>${p.nomeFantasia}</td>
                    <td>${p.endereco}</td>
                    <td>${c.statusPapel}</td>
                </tr>
            </g:each>
            </tbody>
        </table>

        <ul class="pagination">
            <g:paginate total="${clienteCount ?: 0}" />
        </ul>
    </div> <!-- panel-body -->
    <div class="panel-footer">
    </div> <!-- panel-footer -->
</div> <!-- panel -->
</body>
</html>