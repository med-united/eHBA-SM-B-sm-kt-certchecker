package health.medunited.pwdchanger.service;

import de.gematik.ws.conn.cardservicecommon.v2.CardTypeType;
import de.gematik.ws.conn.certificateservice.v6.ReadCardCertificate;
import de.gematik.ws.conn.certificateservice.v6.ReadCardCertificateResponse;
import health.medunited.pwdchanger.model.gematik.ChangePinResponse;
import health.medunited.pwdchanger.model.gematik.GetPinStatusResponse;
import health.medunited.pwdchanger.security.FakeHostnameVerifier;
import health.medunited.pwdchanger.security.FakeX509TrustManager;
import de.gematik.ws.conn.connectorcontext.v2.ContextType;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.TrustManager;

@ApplicationScoped
public class PasswordChangerService {

    EventServicePort eventServicePort;
    CardServicePort cardServicePort;
    CertificateServicePort certificateServicePort;

    @Inject
    public void PasswordChangerService() {
        //System.out.println("Constructor");
    }

    public String setSomething() {
        return "hola";
    }


    public String getCard() {

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
        eventServicePort = new EventServicePort("http://localhost/eventservice", contextType, trustManager,
                hostnameVerifier);

        return eventServicePort.getFirstCardHandleOfType(CardTypeType.HBA);
    }

    public String getCardDetails(String mnCardHandle) {

        ContextType contextType = new ContextType();
        contextType.setMandantId("Mandant1");
        contextType.setWorkplaceId("Workplace1");
        contextType.setClientSystemId("ClientID1");

        TrustManager trustManager = new FakeX509TrustManager();
        HostnameVerifier hostnameVerifier = new FakeHostnameVerifier();//TODO: In order to discover the endpoints on the connector, parse the connector.sds file
        cardServicePort = new CardServicePort("http://localhost/cardservice", contextType, trustManager, hostnameVerifier);
        GetPinStatusResponse pinStatus = cardServicePort.getPinStatus(mnCardHandle);
        return String.valueOf(pinStatus.getPinStatusEnum());

    }

    public String changePin(String mnCardHandle) {

        ContextType contextType = new ContextType();
        contextType.setMandantId("Mandant1");
        contextType.setWorkplaceId("Workplace1");
        contextType.setClientSystemId("ClientID1");

        TrustManager trustManager = new FakeX509TrustManager();
        HostnameVerifier hostnameVerifier = new FakeHostnameVerifier();

        cardServicePort = new CardServicePort("http://localhost/cardservice", contextType, trustManager, hostnameVerifier);
        ChangePinResponse pinStatus;
        pinStatus = cardServicePort.changePin(mnCardHandle);
        return String.valueOf(pinStatus.getStatus());


    }

}
