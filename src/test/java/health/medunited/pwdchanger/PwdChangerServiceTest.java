package health.medunited.pwdchanger;

import de.gematik.ws.conn.connectorcontext.v2.ContextType;
import health.medunited.pwdchanger.model.gematik.ChangePinResponse;
import health.medunited.pwdchanger.model.gematik.GetPinStatusResponse;
import health.medunited.pwdchanger.security.FakeX509TrustManager;
import health.medunited.pwdchanger.service.AmtsServicePort;
import health.medunited.pwdchanger.service.CardServicePort;
import health.medunited.pwdchanger.security.FakeHostnameVerifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.TrustManager;

class PwdChangerServiceTest {

    CardServicePort cardServicePort;
    AmtsServicePort amtsServicePort;

    @BeforeEach
    void init() {
        ContextType contextType = new ContextType();
        contextType.setMandantId("Mandant1");
        contextType.setWorkplaceId("Workplace1");
        contextType.setClientSystemId("ClientID1");
        TrustManager trustManager = new FakeX509TrustManager();
        HostnameVerifier hostnameVerifier = new FakeHostnameVerifier();
        cardServicePort = new CardServicePort("http://localhost/cardservice", contextType, trustManager, hostnameVerifier);
        amtsServicePort = new AmtsServicePort("http://localhost/amtsservice", trustManager, hostnameVerifier);

    }

    @Test
    void verifyPinTest() {
        ChangePinResponse pinStatus = cardServicePort.verifyPin("1137aed3-3a71-4553-87e5-5becf1178019");
        System.out.println(pinStatus.getStatus());
        //Assertions.assertFalse(pinStatus.getStatus().getResult().isEmpty());
    }

    @Test
    void getPinStatusTest() {
        GetPinStatusResponse pinStatus = cardServicePort.getPinStatus("1137aed3-3a71-4553-87e5-5becf1178019");
        System.out.println(pinStatus.getStatus());
        //Assertions.assertFalse(pinStatus.getStatus().getResult().isEmpty());
    }

}
