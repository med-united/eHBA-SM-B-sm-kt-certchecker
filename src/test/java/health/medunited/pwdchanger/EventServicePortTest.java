package health.medunited.pwdchanger;

import de.gematik.ws.conn.cardservicecommon.v2.CardTypeType;
import de.gematik.ws.conn.connectorcontext.v2.ContextType;
import health.medunited.pwdchanger.security.FakeHostnameVerifier;
import health.medunited.pwdchanger.security.FakeX509TrustManager;
//import health.medunited.pwdchanger.service.AmtsServicePort;
import health.medunited.pwdchanger.service.EventServicePort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.TrustManager;

@Disabled
class EventServicePortTest {

    EventServicePort eventServicePort;
    //AmtsServicePort amtsServicePort;

    @BeforeEach
    void init() {
        ContextType contextType = new ContextType();
        contextType.setMandantId("Mandant1");
        contextType.setWorkplaceId("Workplace1");
        contextType.setClientSystemId("ClientID1");
        TrustManager trustManager = new FakeX509TrustManager();
        HostnameVerifier hostnameVerifier = new FakeHostnameVerifier();
        eventServicePort = new EventServicePort("http://localhost/eventservice", contextType, trustManager, hostnameVerifier);
        //amtsServicePort = new AmtsServicePort("http://localhost/amtsservice", trustManager, hostnameVerifier);

    }

    @Test
    void getFirstCardHandleOfTypeTest() {
        String cardHandle = eventServicePort.getFirstCardHandleOfType(CardTypeType.HBA);
        Assertions.assertFalse(cardHandle.isEmpty());
    }
}
