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

    @Inject
    public void CertificateVerifyService() {
        System.out.println("Constructor of CertVerify");
    }

    public String verifyCert() {

        //TODO: create a provider that is able to construct the context type from http headers automatically
        ContextType contextType = new ContextType();
        contextType.setMandantId("Mandant1");
        contextType.setWorkplaceId("Workplace1");
        contextType.setClientSystemId("ClientID1");

        //TODO: at the end we must not use a fake verifier
        TrustManager trustManager = new FakeX509TrustManager();
        HostnameVerifier hostnameVerifier = new FakeHostnameVerifier();

        //TODO: the correct EventServicePort should be automatically injected7

        //TODO: In order to discover the endpoints on the connector, parse the connector.sds file
        certificateServicePort = new CertificateServicePort("http://localhost/cardservice", contextType, trustManager, hostnameVerifier);
        ReadCardCertificateResponse cardResp = certificateServicePort.verifyCertificate();
        return String.valueOf(cardResp);

    }



}
