package health.medunited.pwdchanger.service;

import de.gematik.ws.conn.cardservicecommon.v2.CardTypeType;
import de.gematik.ws.conn.certificateservice.v6.ReadCardCertificateResponse;
import de.gematik.ws.conn.connectorcontext.v2.ContextType;
import health.medunited.pwdchanger.model.gematik.GetPinStatusResponse;
import health.medunited.pwdchanger.security.FakeHostnameVerifier;
import health.medunited.pwdchanger.security.FakeX509TrustManager;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.TrustManager;

@ApplicationScoped
public class CertificateVerifyService {

    CertificateServicePort certificateServicePort;
    ReadCardCertificateResponse readCardCertificateResponse;

    @Inject
    public void CertificateVerifyService() {
        System.out.println("Constructor of CertVerify");
    }

    public String verifyCertificateFromPort() {
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

        readCardCertificateResponse = certificateServicePort.doReadCardCertificate();

        String returnMessage = readCardCertificateResponse.getX509DataInfoList().toString();
        return returnMessage;
    }



}
