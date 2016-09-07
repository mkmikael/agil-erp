#! /bin/bash
# Script para deploy do projeto Vida Melhor. Visão geral: Faz o undeploy da
# aplicação, reinicia o Tomcat, gera o novo war da aplicação, faz o deploy.

# Precisamos de acesso root.
#if [ "$EUID" -ne 0 ] ; then
#  echo "Por favor, execute o deploy como root."
#  exit
#fi

TOMCAT_DEPLOY_URL="http://localhost:8080/manager/text/deploy?path="
TOMCAT_UNDEPLOY_URL="http://localhost:8080/manager/text/undeploy?path="
TOMCAT_LIST_URL="http://localhost:8080/manager/text/list"
TOMCAT_SESSIONS_URL="http://localhost:8080/manager/text/sessions"

APP_PATH="web-vidamelhor"
WAR_FILE="web-vidamelhor.war"
GRAILS_ENV="homologation"
GRAILS_CLEAN=false
RESTART_TOMCAT=false

function deploy {
  # Diretório completo deste script.
  DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
  curl --user script:script "$TOMCAT_DEPLOY_URL/$APP_PATH&war=file:$DIR/$WAR_FILE"
}

function list {
  echo "Aplicações em execução:"
  curl --user script:script $TOMCAT_LIST_URL
}

function sessions {
  curl --user tom:tomcat "$TOMCAT_SESSIONS_URL?path=/$APP_PATH"
}

function undeploy {
  curl --user script:script "$TOMCAT_UNDEPLOY_URL/$APP_PATH"
}

function make_war {
  export JAVA_HOME="/usr/lib/jvm/java-7-openjdk-amd64"

  if [ "$GRAILS_CLEAN" = true ] ; then
    echo "Grails clean..."
    grails clean
  fi

  grails -Dgrails.env=$GRAILS_ENV war $WAR_FILE
}

function restart_tomcat {
    service tomcat7 restart
}

# Leitura dos argumentos.
for i in "$@"; do
case $i in
   -r|--restart)
    RESTART_TOMCAT=true
    shift
  ;;
  -p=*|--path=*)
    APP_PATH="${i#*=}"
    shift
  ;;
  -w=*|--war=*)
    WAR_FILE="${i#*=}"
    shift
  ;;
  -e=*|--env=*)
    GRAILS_ENV="${i#*=}"
    shift
  ;;
  -c|--clean)
    GRAILS_CLEAN=true
    shift
  ;;
  -h|--help)
    echo "Utilitário para deploy do Vida Melhor."
    echo "USO:"
    echo "$0 -p|--path=[PATH] -w|--war=[WAR_FILE] -e|--env=[ENV] -c|--clean -r|--restart"
    echo ""
    echo "-p|--path: Path da aplicação no Tomcat. Ex.: localhost:8080/<PATH>"
    echo "-w|--war: Nome do arquivo WAR a ser gerado e usado no deploy."
    echo "-e|--env: Ambiente do Grails."
    echo "-c|--clean: Executa o comando \`grails clean\` antes de gerar o WAR."
    echo "-r|--restart: Reinicia o Tomcat."
    echo ""
    echo "VALORES PADRÃO:"
    echo "PATH = $APP_PATH"
    echo "WAR_FILE = $WAR_FILE"
    echo "ENV = $GRAILS_ENV"
    echo "-c|--clean: Não é usado por padrão."
    echo "-r|--restart: Não é reiniciado por padrão."
    exit 0
  ;;
  *)
    echo "Argumento inválido: ${i}. Use $0 -h para ajuda."
    exit -1
  ;;
esac
done

echo "Iniciando deploy do projeto Vida Melhor."

echo "Undeploy..."
undeploy

if [ "$RESTART_TOMCAT" = true ] ; then
    echo "Reiniciando Tomcat..."
    restart_tomcat
fi

echo "Gerando o WAR..."
make_war

echo "Realizando o deploy..."
deploy

list

echo "FIM"
