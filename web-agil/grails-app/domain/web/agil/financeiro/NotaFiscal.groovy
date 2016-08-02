package web.agil.financeiro

import web.agil.cadastro.Cliente
import web.agil.financeiro.enums.TipoNotaFiscal

class NotaFiscal extends NotaComercial {

    String arquivo
    String arquivoNome
    Date dataAutorizacao
    String doc
    String clienteNome

    static constraints = {
        total scale: 6
        dataAutorizacao nullable: true
    }
}
