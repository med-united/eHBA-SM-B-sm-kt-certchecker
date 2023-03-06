package health.medunited.pwdchanger.service;


import de.gematik.ws.conn.certificateservice.v6.VerificationResultType;
import de.gematik.ws.conn.certificateservice.wsdl.v6.FaultMessage;
import de.gematik.ws.conn.certificateservicecommon.v2.CertRefEnum;

import health.medunited.pwdchanger.security.BindingProviderConfigurer;

import de.gematik.ws.conn.certificateservice.v6.ReadCardCertificate;
import de.gematik.ws.conn.certificateservice.v6.ReadCardCertificateResponse;
import de.gematik.ws.conn.certificateservice.v6.VerifyCertificateResponse;
import de.gematik.ws.conn.certificateservice.wsdl.v6.CertificateService;
import de.gematik.ws.conn.certificateservice.wsdl.v6.CertificateServicePortType;
import de.gematik.ws.conn.certificateservicecommon.v2.X509DataInfoListType;
import de.gematik.ws.conn.connectorcommon.v5.Status;
import de.gematik.ws.conn.connectorcontext.v2.ContextType;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.TrustManager;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;

import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import org.bouncycastle.cert.ocsp.*;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.math.BigInteger;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.logging.Logger;

public class CertificateServicePort {

    private static final Logger log = Logger.getLogger(CertificateServicePort.class.getName());

    static {
        Security.addProvider(new BouncyCastleProvider());
    }


    CertificateServicePortType certificateServicePortType;

    /* doVerifyCertificate */
    CertificateServicePortType certificateService;
    ContextType contextType;
    /* doVerifyCertificate */

    ContextType context;

    String cardHandle = "f43be851-42c4-4aad-b603-9c2f9e2f94e3";//new

    ReadCardCertificate readCardCertificate;

    ReadCardCertificate.CertRefList certRefList;

    public CertificateServicePort() {

    }

    public CertificateServicePort(String endpoint, ContextType context, TrustManager trustManager, HostnameVerifier hostnameVerifier) {
        this.context = context;
        try {
            certificateServicePortType = new CertificateService(
                    getClass()
                    .getResource("/CertificateService_v6_0_1.wsdl"))
                    .getCertificateServicePort();
            System.out.println("cspt initialized correctly");
        } catch(Exception e) {
            System.out.println("Catching cspt initialization: ");
            System.out.println(e);
        }

        BindingProvider bp = (BindingProvider) certificateServicePortType;
        bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpoint);
        BindingProviderConfigurer.configure(bp, trustManager, hostnameVerifier);
        certRefList = new ReadCardCertificate.CertRefList();
        certRefList.getCertRef().add(CertRefEnum.C_AUT);

        try {
            CertificateFactory certFactory = CertificateFactory.getInstance("X.509", BouncyCastleProvider.PROVIDER_NAME);
            //X509Certificate z = (X509Certificate) certFactory;
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public ReadCardCertificateResponse doReadCardCertificate() {
        System.out.println("Inside readCard");
        Holder<Status> status = new Holder<>();
        Holder<X509DataInfoListType> certList = new Holder<>();

        try {
            log.fine(certificateServicePortType.toString());
            // The following line is failing:
            this.certificateServicePortType
                    .readCardCertificate(cardHandle, context, certRefList, status, certList);

            ReadCardCertificateResponse readCardCertificateResponse = new ReadCardCertificateResponse();

            readCardCertificateResponse.setStatus(status.value);
            readCardCertificateResponse.setX509DataInfoList(certList.value);

            System.out.println("Certificate read correctly:"+
                    readCardCertificateResponse.getX509DataInfoList());

            return readCardCertificateResponse;
            //return "The system was able to read the certificate";
        } catch (Error | Exception e) {
            System.out.println("The system threw an error while trying to read the certificate: \n\n"+e);
            return null;
        }
        //return "intel inside";
    }

    void doVerifyCertificate(X509Certificate z) {

        String fachdienstUrl = "http://localhost";

        Holder<Status> status = new Holder<>();
        Holder<VerifyCertificateResponse.VerificationStatus> verificationStatus = new Holder<>();
        Holder<VerifyCertificateResponse.RoleList> arg5 = new Holder<>();
        XMLGregorianCalendar now = null;;
        try {
            now = DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(TimeZone.getTimeZone("UTC")));
        } catch (DatatypeConfigurationException e) {
            throw new IllegalStateException("Can not verify certificate from VAU", e);
        }
        try {
            certificateService.verifyCertificate(contextType, z.getEncoded(), now, status, verificationStatus, arg5);
        } catch (Exception e) {
            throw new IllegalStateException("Can not verify certificate from VAU", e);
        }
        if(verificationStatus.value.getVerificationResult() != VerificationResultType.VALID) {
            throw new IllegalStateException("VAU certificate is not valid");
        }

        // Code based on: https://github.com/apache/nifi/blob/master/nifi-nar-bundles/nifi-framework-bundle/nifi-framework/nifi-web/nifi-web-security/src/main/java/org/apache/nifi/web/security/x509/ocsp/OcspCertificateValidator.java#L278
        InputStream ocspResponseStream;
        BasicOCSPResp basicOcspResponse;
        try {
            ocspResponseStream = new URL(fachdienstUrl + "/VAUCertificateOCSPResponse").openStream();
            OCSPResp oCSPResp = new OCSPResp(ocspResponseStream);
            basicOcspResponse = (BasicOCSPResp) oCSPResp.getResponseObject();
        } catch (IOException | OCSPException e2) {
            throw new IllegalArgumentException("Could not parse OCSP response", e2);
        }

        BigInteger subjectSerialNumber = z.getSerialNumber();
        // validate the response
        final SingleResp[] responses = basicOcspResponse.getResponses();
        for (SingleResp singleResponse : responses) {
            final CertificateID responseCertificateId = singleResponse.getCertID();
            final BigInteger responseSerialNumber = responseCertificateId.getSerialNumber();

            if (responseSerialNumber.equals(subjectSerialNumber)) {
                Object certStatus = singleResponse.getCertStatus();

                // interpret the certificate status
                if (certStatus instanceof RevokedStatus) {
                    throw new IllegalStateException("VAU certificate status is revoked");
                }
            }
        }
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

    public String getCardHandle() {
        return cardHandle;
    }

    public  ReadCardCertificate.CertRefList getCertRefList() {
        return certRefList;
    }

}
