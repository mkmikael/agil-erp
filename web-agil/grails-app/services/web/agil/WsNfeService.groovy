package web.agil

import com.fincatto.nfe310.classes.NFAmbiente
import com.fincatto.nfe310.classes.NFModelo
import com.fincatto.nfe310.classes.NFUnidadeFederativa
import com.fincatto.nfe310.classes.evento.NFEnviaEventoRetorno
import com.fincatto.nfe310.classes.lote.envio.NFLoteEnvio
import com.fincatto.nfe310.classes.lote.envio.NFLoteEnvioRetorno
import com.fincatto.nfe310.classes.nota.NFNota
import com.fincatto.nfe310.classes.nota.NFNotaInfo
import com.fincatto.nfe310.classes.statusservico.consulta.NFStatusServicoConsultaRetorno
import com.fincatto.nfe310.utils.NFGeraCadeiaCertificados
import com.fincatto.nfe310.webservices.WSFacade
import grails.transaction.Transactional
import org.apache.commons.io.FileUtils
import web.agil.nfe.NFeConfigImpl

@Transactional
class WsNfeService {

    def getPublicKey() {
        FileUtils.writeByteArrayToFile(new File("/home/mikael/Documentos/producao.cacerts"),
                NFGeraCadeiaCertificados.geraCadeiaCertificados(NFAmbiente.PRODUCAO, "nfeproducao"));
        FileUtils.writeByteArrayToFile(new File("/home/mikael/Documentos//homologacao.cacerts"),
                NFGeraCadeiaCertificados.geraCadeiaCertificados(NFAmbiente.HOMOLOGACAO, "nfehomologacao"));
    }

    def checkStatusWebService() {
        def config = new NFeConfigImpl()
        NFStatusServicoConsultaRetorno retorno = new WSFacade(config)
                .consultaStatus(NFUnidadeFederativa.SC, NFModelo.NFE);
        log.debug "Status: $retorno.status"
        log.debug "Motivo: $retorno.motivo"
    }

    def enviarLote() {
        NFLoteEnvio lote = new NFLoteEnvio();
        def nota = new NFNota()
        def info = new NFNotaInfo()

        nota.info = info
        def config = new NFeConfigImpl()
        final NFLoteEnvioRetorno retorno = new WSFacade(config).enviaLote(lote);
    }

    def corigirNota() {
        def config = new NFeConfigImpl()
        final NFEnviaEventoRetorno retorno = new WSFacade(config).corrigeNota(chaveDeAcessoDaNota, textoCorrecao, sequencialEventoDaNota);
    }

    def cancelarsNota() {
        def config = new NFeConfigImpl()
        final NFEnviaEventoRetorno retorno = new WSFacade(config).cancelaNota(chaveDeAcessoDaNota, protocoloDaNota, motivoCancelaamento);
    }
}
