package web.agil.nfe

import com.fincatto.nfe310.NFeConfig
import com.fincatto.nfe310.classes.NFUnidadeFederativa
import grails.util.Holders

import javax.security.cert.CertificateException
import java.security.KeyStore
import java.security.KeyStoreException
import java.security.NoSuchAlgorithmException

/**
 * Created by mikael on 25/09/16.
 */
class NFeConfigImpl extends NFeConfig {

    KeyStore keyStoreCertificado
    KeyStore keyStoreCadeia


    @Override
    NFUnidadeFederativa getCUF() {
        NFUnidadeFederativa.PA
    }

    @Override
    KeyStore getCertificadoKeyStore() throws KeyStoreException {
        if (keyStoreCertificado == null) {
            keyStoreCertificado = KeyStore.getInstance("PKCS12");
            String filePath = getRealPath('/certificados/agildistribuicoes2016.pfx')
            def certificadoStream = new FileInputStream(filePath)
            keyStoreCertificado.load(certificadoStream, this.getCertificadoSenha().toCharArray());
        }
        keyStoreCertificado;
    }

    @Override
    String getCertificadoSenha() {
        "1092"
    }

    @Override
    KeyStore getCadeiaCertificadosKeyStore() throws KeyStoreException {
        if (keyStoreCadeia == null) {
            keyStoreCadeia = KeyStore.getInstance("JKS");
            String filePath = getRealPath('/certificados/homologacao.cacerts')
            def cadeia = new FileInputStream(filePath)
            keyStoreCadeia.load(cadeia, this.getCadeiaCertificadosSenha().toCharArray());
        }
        keyStoreCadeia;
    }

    @Override
    String getCadeiaCertificadosSenha() {
        "nfehomologacao"
    }

    String getRealPath(String path) {
        Holders.servletContext.getRealPath(path)
    }
}
