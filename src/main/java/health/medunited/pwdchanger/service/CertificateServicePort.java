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

import java.io.FileInputStream;
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

    ContextType contextType;

    String cardHandle = "f43be851-42c4-4aad-b603-9c2f9e2f94e3";

    X509Certificate x509Certificate;

    ReadCardCertificate.CertRefList certRefList;

    public CertificateServicePort() {

    }

    public CertificateServicePort(String endpoint, ContextType context, TrustManager trustManager, HostnameVerifier hostnameVerifier) {
        this.contextType = context;
        try {
            certificateServicePortType = new CertificateService(
                    getClass()
                    .getResource("/CertificateService_v6_0_1.wsdl"))
                    .getCertificateServicePort();
            System.out.println("certificateServicePort initialized correctly: "+certificateServicePortType.toString());
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
            //InputStream was added by myself
            InputStream is = new FileInputStream("cacert.crt");
            CertificateFactory certFactory = CertificateFactory.getInstance("X.509", BouncyCastleProvider.PROVIDER_NAME);
            x509Certificate = (X509Certificate) certFactory.generateCertificate(is);
            System.out.println("certFactory initialized correctly: "+certFactory.toString());
            System.out.println("generated x509Cert: "+x509Certificate.toString());
        } catch (Exception e) {
            System.out.println("There has been an error with the Certificates Intialization: \n"+e);
        }
    }


    String doVerifyCertificate(X509Certificate theC) {

        String answer = "No certificate processing yet";

        Holder<Status> status = new Holder<>();
        Holder<VerifyCertificateResponse.VerificationStatus> verificationStatus = new Holder<>();
        Holder<VerifyCertificateResponse.RoleList> arg5 = new Holder<>();
        XMLGregorianCalendar now = null;
        try {
            now = DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(TimeZone.getTimeZone("UTC")));
            System.out.println("Creating a timestamp for 'now'");
        } catch (DatatypeConfigurationException e) {
            System.out.println("Failed to create timestamp for vertification: "+ e);
            answer = "Initial timestamp creation system error";
        }
        try {
            certificateServicePortType.verifyCertificate(contextType, theC.getEncoded(), now, status, verificationStatus, arg5);
            System.out.println("Core verification process in progress...");
        } catch (Exception e) {
            System.out.println("Core verification process has failed with error: "+e);
            answer = "Core Verification Proecess system error";
        }
        if(verificationStatus.value.getVerificationResult() != VerificationResultType.VALID) {
            System.out.println("Error: Verification process finished. Result Status: "
            +verificationStatus.value.getVerificationResult());
            answer = "Certifiate was checked. Result: "+verificationStatus.value.getVerificationResult();
        }
        return answer;
    }


    public ReadCardCertificateResponse doReadCardCertificate() {
        System.out.println("Inside readCard");
        Holder<Status> status = new Holder<>();
        Holder<X509DataInfoListType> certList = new Holder<>();

        try {
            log.fine(certificateServicePortType.toString());
            // The following line is failing:
            this.certificateServicePortType
                    .readCardCertificate(cardHandle, contextType, certRefList, status, certList);

            ReadCardCertificateResponse readCardCertificateResponse = new ReadCardCertificateResponse();

            readCardCertificateResponse.setStatus(status.value);
            readCardCertificateResponse.setX509DataInfoList(certList.value);

            System.out.println("Certificate read correctly: "+
                    readCardCertificateResponse.getX509DataInfoList());

            return readCardCertificateResponse;
            //return "The system was able to read the certificate";
        } catch (Error | Exception e) {
            System.out.println("The system threw an error while trying to read the certificate: \n\n"+e);
            return null;
        }
        //return "intel inside";
    }


    public ReadCardCertificateResponse verifyCertificate() {
        return null;
    }

    public CertificateServicePortType getCertificateServicePortType() {
        return certificateServicePortType;
    }

    public ContextType getContext() {
        return contextType;
    }

    public String getCardHandle() {
        return cardHandle;
    }

    public  ReadCardCertificate.CertRefList getCertRefList() {
        return certRefList;
    }

}
