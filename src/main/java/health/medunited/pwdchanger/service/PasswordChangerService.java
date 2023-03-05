package health.medunited.pwdchanger.service;

import de.gematik.ws.conn.cardservicecommon.v2.CardTypeType;
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

    public String getCert() {
        //called from URLResource

        /* intialization */
        ContextType contextType = new ContextType();
        contextType.setMandantId("Mandant1");
        contextType.setWorkplaceId("Workplace1");
        contextType.setClientSystemId("ClientID1");
        TrustManager trustManager = new FakeX509TrustManager();
        HostnameVerifier hostnameVerifier = new FakeHostnameVerifier();

        certificateServicePort = new CertificateServicePort();

        /* end of intialization */

        String sth = certificateServicePort.getSomething().toString();
        return sth;
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

    public String getCardDetails() {

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
        cardServicePort = new CardServicePort("http://localhost/cardservice", contextType, trustManager, hostnameVerifier);
        GetPinStatusResponse pinStatus = cardServicePort.getPinStatus("00303add-87db-450b-bd18-0ab48c7b4ff9");
        return String.valueOf(pinStatus.getPinStatusEnum());

    }

    public String changePin() {

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
        cardServicePort = new CardServicePort("http://localhost/cardservice", contextType, trustManager, hostnameVerifier);
        ChangePinResponse pinStatus = cardServicePort.changePin("abc0f624-7e15-4a4d-9006-d7637883a341");
        return String.valueOf(pinStatus.getStatus());

    }

}
