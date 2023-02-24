package health.medunited.pwdchanger.service;

import de.gematik.ws.conn.cardservicecommon.v2.CardTypeType;
import health.medunited.pwdchanger.security.FakeHostnameVerifier;
import health.medunited.pwdchanger.security.FakeX509TrustManager;
import de.gematik.ws.conn.connectorcontext.v2.ContextType;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.TrustManager;

public class Fruit {

    EventServicePort eventServicePort;

    public String returnString = "a1";

    public  Fruit() {
        this.returnString = "initial";
        System.out.println("Fruit constructor");

        ContextType contextType = new ContextType();
        contextType.setMandantId("Mandant1");
        contextType.setWorkplaceId("Workplace1");
        contextType.setClientSystemId("ClientID1");
        TrustManager trustManager = new FakeX509TrustManager();
        HostnameVerifier hostnameVerifier = new FakeHostnameVerifier();
        eventServicePort = new EventServicePort("http://localhost/eventservice", contextType, trustManager, hostnameVerifier);
        String cardHandle = eventServicePort.getFirstCardHandleOfType(CardTypeType.HBA);

    }

}
