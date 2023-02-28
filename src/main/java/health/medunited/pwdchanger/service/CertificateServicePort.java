package health.medunited.pwdchanger.service;

import de.gematik.ws.conn.cardservice.v8.PinStatusEnum;
import de.gematik.ws.conn.certificateservice.v6.ReadCardCertificate;
import de.gematik.ws.conn.certificateservice.v6.ReadCardCertificateResponse;
import de.gematik.ws.conn.certificateservice.wsdl.v6.CertificateService;
import de.gematik.ws.conn.certificateservice.wsdl.v6.CertificateServicePortType;
import de.gematik.ws.conn.certificateservicecommon.v2.X509DataInfoListType;
import de.gematik.ws.conn.connectorcommon.v5.Status;
import de.gematik.ws.conn.connectorcontext.v2.ContextType;
import health.medunited.pwdchanger.model.gematik.GetPinStatusResponse;
import health.medunited.pwdchanger.security.BindingProviderConfigurer;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.TrustManager;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;
import java.math.BigInteger;

public class CertificateServicePort {


    private final CertificateServicePortType certificateServicePortType;

    private final ContextType context;

    private final String cardHandle = "abc1";

    private final ReadCardCertificate.CertRefList certRefList;

    public CertificateServicePort(String endpoint, ContextType context, TrustManager trustManager, HostnameVerifier hostnameVerifier) {
        this.context = context;
        this.certificateServicePortType = new CertificateService(getClass()
                .getResource("/CertificateService.wsdl"))
                .getCertificateServicePort();
        BindingProvider bp = (BindingProvider) certificateServicePortType;
        bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpoint);
        BindingProviderConfigurer.configure(bp, trustManager, hostnameVerifier);
        certRefList = null;
    }

    public ReadCardCertificateResponse readCardCertificate(ContextType context, String cardHandle,  ReadCardCertificate.CertRefList certRefList) {

        /*
        Return Parameters:
        Status
        CertRef
        X509Data
        X509IssuerName
        509SerialNumber
        X509SubjectName
        X509BaseCertificateId
        X509Certificate
         */

        Holder<Status> status = new Holder<>();
        Holder<X509DataInfoListType> certList = new Holder<>();

        try {
            this.certificateServicePortType.readCardCertificate(cardHandle, context, certRefList, status, certList);
            return new ReadCardCertificateResponse();
        } catch (de.gematik.ws.conn.certificateservice.wsdl.v6.FaultMessage e) {
            e.printStackTrace();
        }
        return null;

    }

    public ReadCardCertificateResponse verifyCertificate() {
        return null;
    }

    public CertificateServicePortType getCertificateServicePortType() {
        return certificateServicePortType;
    }

    public ContextType getContext() {
        return context;
    }
}
