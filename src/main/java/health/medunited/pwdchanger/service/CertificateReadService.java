package health.medunited.pwdchanger.service;

import de.gematik.ws.conn.certificateservice.v6.ReadCardCertificate;
import de.gematik.ws.conn.certificateservice.v6.ReadCardCertificateResponse;
import de.gematik.ws.conn.connectorcontext.v2.ContextType;
import health.medunited.pwdchanger.security.FakeHostnameVerifier;
import health.medunited.pwdchanger.security.FakeX509TrustManager;
import org.bouncycastle.jce.provider.BouncyCastleProvider;


import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.TrustManager;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

@ApplicationScoped
public class CertificateReadService {


    CertificateServicePort certificateServicePort;
    ReadCardCertificateResponse readCardCertificateResponse;

    X509Certificate x509Certificate;

    public X509Certificate getX509Certificate() {
        return x509Certificate;
    }

    @Inject
    public void CertificateReadService() {
        System.out.println("Constructor of CertRead");
    }

    public String getCardCertificateFromPort() {
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

        InputStream bayIS = new ByteArrayInputStream(
                readCardCertificateResponse
                        .getX509DataInfoList().getX509DataInfo().get(0)
                        .getX509Data().getX509Certificate()
        );

        try {
            CertificateFactory certFactory = CertificateFactory
                    .getInstance("X.509", BouncyCastleProvider.PROVIDER_NAME);
            x509Certificate = (X509Certificate) certFactory.generateCertificate(bayIS);
            System.out.println("The cert has been read from the server");
        } catch (Exception e) {
            System.out.println("Error reading the cert from the server");
        }

        String returnMessage = x509Certificate.toString();

        return "The certificate is: \n\n:"+returnMessage;
    }

    public String verifyCertificateFromPort(X509Certificate theCert) {
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

        //readCardCertificateResponse = certificateServicePort.doReadCardCertificate();

        String returnMessage = certificateServicePort.doVerifyCertificate(theCert);

        return returnMessage;
    }
}
