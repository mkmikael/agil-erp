<%@ page import="web.agil.cadastro.Organizacao" %>
<li>
    <span class="p-label">Razão Social</span>
    <span class="p-value">${participante?.nome}</span>
</li>
<li>
    <span class="p-label">Nome Fantasia</span>
    <span class="p-value">${participante?.nomeFantasia}</span>
</li>
<li>
    <span class="p-label">CPF / CNPJ</span>
    <span class="p-value">${participante?.doc}</span>
</li>
<li>
    <span class="p-label">Telefone</span>
    <span class="p-value">${participante?.telefone}</span>
</li>
<g:if test="${participante instanceof Organizacao}">
    <li>
        <span class="p-label">Inscrição Estadual</span>
        <span class="p-value">${participante?.inscricaoEstadual}</span>
    </li>
</g:if>
<li>
    <span class="p-label">Endereço</span>
    <span class="p-value">
        <g:set var="endereco" value="${participante?.endereco}" />
        CEP: ${endereco?.cep}, <br>
        ${endereco?.logradouro}${endereco?.complemento ? " - ${endereco?.complemento}" : ''}, Nº ${endereco?.numero} <br>
        ${endereco?.cidade}
    </span>
</li>