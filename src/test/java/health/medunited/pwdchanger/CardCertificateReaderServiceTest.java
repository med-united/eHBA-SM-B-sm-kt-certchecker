package health.medunited.pwdchanger;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.logging.LogManager;

import javax.inject.Inject;

import health.medunited.pwdchanger.service.CardCertificateReaderService;
import org.bouncycastle.crypto.CryptoException;
import org.junit.jupiter.api.*;

import health.medunited.pwdchanger.exception.ConnectorCardCertificateReadException;
import health.medunited.pwdchanger.exception.ConnectorCardsException;
//import health.ere.ps.profile.TitusTestProfile;
import health.medunited.pwdchanger.service.ConnectorCardsService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;

@QuarkusTest
//@TestProfile(TitusTestProfile.class)
@Disabled
class CardCertificateReaderServiceTest {

    @Inject
    ConnectorCardsService connectorCardsService;
    @Inject
    CardCertificateReaderService cardCertificateReaderService;

    @BeforeEach
    void init() {
        try {
            // https://community.oracle.com/thread/1307033?start=0&tstart=0
            LogManager.getLogManager().readConfiguration(
                CardCertificateReaderServiceTest.class
                            .getResourceAsStream("/logging.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.setProperty("com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump", "true");
        System.setProperty("com.sun.xml.internal.ws.transport.http.client.HttpTransportPipe.dump", "true");
        System.setProperty("com.sun.xml.ws.transport.http.HttpAdapter.dump", "true");
        System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dump", "true");
        System.setProperty("com.sun.xml.ws.transport.http.HttpAdapter.dumpTreshold", "999999");
    }

    @Test
    @Tag("titus")
    void test_Successful_X509Certificate_Creation_From_ReadCardCertificate_API_Call()
            throws ConnectorCardCertificateReadException, IOException, CertificateException,
            CryptoException, ConnectorCardsException {

        String smcbHandle = connectorCardsService.getConnectorCardHandle(
                ConnectorCardsService.CardHandleType.SMC_B);

        X509Certificate x509Certificate = null;
        try {
            x509Certificate = cardCertificateReaderService.retrieveSmcbCardCertificate(smcbHandle);
        } catch (health.medunited.pwdchanger.connector.ConnectorCardCertificateReadException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertNotNull(x509Certificate,
                "Smart card certificate was retrieved");

        x509Certificate.checkValidity();
    }
}