package health.medunited.pwdchanger;

import de.gematik.ws.conn.connectorcontext.v2.ContextType;
import health.medunited.pwdchanger.model.gematik.GetPinStatusResponse;
import health.medunited.pwdchanger.security.FakeX509TrustManager;
import health.medunited.pwdchanger.service.CardServicePort;
import health.medunited.pwdchanger.security.FakeHostnameVerifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.TrustManager;

class PwdChangerServiceTest {

    CardServicePort cardServicePort;

    @BeforeEach
    void init() {
        ContextType contextType = new ContextType();
        contextType.setMandantId("Mandant1");
        contextType.setWorkplaceId("Workplace1");
        contextType.setClientSystemId("ClientID1");
        TrustManager trustManager = new FakeX509TrustManager();
        HostnameVerifier hostnameVerifier = new FakeHostnameVerifier();
        cardServicePort = new CardServicePort("http://localhost/cardservice", contextType, trustManager, hostnameVerifier);
    }

    @Test
    void getPinStatusTest() {
        GetPinStatusResponse pinStatus = cardServicePort.getPinStatus("00303add-87db-450b-bd18-0ab48c7b4ff9");
        System.out.println(pinStatus.getStatus());
        //Assertions.assertFalse(pinStatus.getStatus().getResult().isEmpty());
    }

}
