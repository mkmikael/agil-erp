<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'eventoFinanceiro.label', default: 'EventoFinanceiro')}" />
    <title><g:message code="default.show.label" args="[entityName]" /></title>
</head>
<body>
<div class="panel panel-default">
    <div class="panel-heading">
        <h4><g:message code="default.show.label" args="[entityName]" /></h4>
    </div> <!-- panel-heading -->
    <div class="panel-body">
        <bs:alert />

        <p>
            <g:link controller="lancamento">CAR</g:link>
            <bs:createButton entityName="${entityName}" />
        </p>

        <f:display bean="eventoFinanceiro" />

        <div>
            %{--<bs:editButton resource="${this.eventoFinanceiro}" />--}%
            %{--<bs:deleteButton resource="${this.eventoFinanceiro}" />--}%
        </div>
    </div> <!-- panel-body -->
</div> <!-- panel -->

</body>
</html>
