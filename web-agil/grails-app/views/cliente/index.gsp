<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'cliente.label', default: 'Cliente')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="container-fluid" role="main">

            <div class="navbar navbar-default" role="navigation">
                <div class="container">
                    <ul class="nav navbar-nav navbar-left">
                        <li>
                            <a href="${createLink(uri: '/')}">
                                <span class="glyphicon glyphicon-home"></span> <g:message code="default.home.label"/>
                            </a>
                        </li>
                        <li>
                            <g:link action="create">
                                <span class="glyphicon glyphicon-plus"></span> <g:message code="default.new.label" args="[entityName]" />
                            </g:link>
                        </li>
                    </ul>
                </div>
            </div>

            <h2><g:message code="default.list.label" args="[entityName]" /></h2>

            <g:if test="${flash.message}">
                <div class="alert alert-info alert-dismissible" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    ${flash.message}
                </div>
            </g:if>

            <f:table collection="${clienteList}" />

            <ul class="pagination">
                <g:paginate total="${clienteCount ?: 0}" />
            </ul>
        </div>
    </body>
</html>