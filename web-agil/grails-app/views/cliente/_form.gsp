<g:hiddenField name="id" value="${cliente?.id}" />
<g:render template="/participante/form" model="[participante: cliente?.participante]" />