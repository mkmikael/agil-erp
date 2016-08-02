<!doctype html>
<html lang="en" class="no-js">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>
        <g:layoutTitle default="Ágil Distribuições"/>
    </title>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <asset:stylesheet src="application.css"/>
    <asset:javascript src="application.js"/>

    <g:layoutHead/>
</head>
<body>

    <div class="navbar navbar-default navbar-static-top" role="navigation">
        <div class="container">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
            </div>
            <div class="navbar-collapse collapse" aria-expanded="false" style="height: 0.8px;">
                <ul class="nav navbar-nav navbar-left">
                    <li>
                        <a href="${createLink(uri: '/')}">
                            <span class="glyphicon glyphicon-home"></span> <g:message code="default.home.label"/>
                        </a>
                    </li>
                    <li><a href="${createLink(controller: 'notaAvulsa')}" class="navbar-link">Notas Avulsas</a></li>
                    <li><a href="${createLink(controller: 'notaFiscal')}" class="navbar-link">Notas Fiscais</a></li>
                    <li>
                        <a href="${createLink(controller: 'estatistica')}" class="navbar-link">Estatística</a>
                    </li>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                            Cadastros <span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu">
                            <li><a href="${createLink(controller: 'produto')}">Produtos</a></li>
                        </ul>
                    </li>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                            Sistema <span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu">
                            <li><a href="${createLink(controller: 'console')}">Console</a></li>
                            <li><a href="${createLink(controller: 'sistema')}">Central de Processamento</a></li>
                        </ul>
                    </li>
                    <g:pageProperty name="page.nav" />
                </ul>
            </div>
        </div>
    </div>

    <div class="container-fluid" role="main">
        <g:layoutBody/>
        <br><br>
    </div>

    <g:hiddenField name="baseUrl" value="${g.createLink(uri: '/')}" />

</body>
</html>
