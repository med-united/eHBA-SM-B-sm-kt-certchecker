package health.medunited.pwdchanger;

import de.gematik.ws.conn.connectorcontext.v2.ContextType;
import health.medunited.pwdchanger.security.FakeHostnameVerifier;
import health.medunited.pwdchanger.security.FakeX509TrustManager;
import health.medunited.pwdchanger.service.AmtsServicePort;
import health.medunited.pwdchanger.service.CardServicePort;
import health.medunited.pwdchanger.service.CertificateServicePort;
import health.medunited.pwdchanger.service.EventServicePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.TrustManager;

@Disabled
public class CertificateServicePortTest {
    CertificateServicePort certificateServicePort;

    @BeforeEach
    void init() {
        ContextType contextType = new ContextType();
        contextType.setMandantId("Mandant1");
        contextType.setWorkplaceId("Workplace1");
        contextType.setClientSystemId("ClientID1");
        TrustManager trustManager = new FakeX509TrustManager();
        HostnameVerifier hostnameVerifier = new FakeHostnameVerifier();
    }

}
