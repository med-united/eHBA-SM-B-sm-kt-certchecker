package health.medunited.pwdchanger.service;

import de.gematik.ws.conn.certificateservice.v6.ReadCardCertificate;
import de.gematik.ws.conn.certificateservice.v6.ReadCardCertificateResponse;
import de.gematik.ws.conn.connectorcontext.v2.ContextType;
import health.medunited.pwdchanger.security.FakeHostnameVerifier;
import health.medunited.pwdchanger.security.FakeX509TrustManager;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.TrustManager;

@ApplicationScoped
public class CertificateReadService {


    CertificateServicePort certificateServicePort;

    @Inject
    public void CertificateReadService() {
        System.out.println("Constructor of CertRead");
    }

    public String getCert() {
        //called from URLResource

        /* intialization */
        String endpoint = "http://localhost/certificateservice";
        ContextType contextType = new ContextType();
        contextType.setMandantId("Mandant1");
        contextType.setWorkplaceId("Workplace1");
        contextType.setClientSystemId("ClientID1");
        TrustManager trustManager = new FakeX509TrustManager();
        HostnameVerifier hostnameVerifier = new FakeHostnameVerifier();

        certificateServicePort = new CertificateServicePort(
                endpoint,
                contextType,
                trustManager,
                hostnameVerifier
        );
        /* end of intialization */

        String returnMessage = certificateServicePort.readCard();
        return returnMessage;
    }
}
