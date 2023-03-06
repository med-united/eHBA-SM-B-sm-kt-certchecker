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

import java.io.ByteArrayInputStream;
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

    String cardHandle;

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

    }


    String doVerifyCertificate(X509Certificate theC) {

        try {
            System.out.println("TBSCERT" +theC.getTBSCertificate());
            System.out.println("ENCODED" +theC.getEncoded());
            System.out.println("SIGNATURE" +theC.getSignature());
            System.out.println("SIGALGPARAM" +theC.getSigAlgParams());
        } catch(Exception e) {
            System.out.println("ENCODING ERROR OF THE CERT: "+e);
        }

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


    public ReadCardCertificateResponse doReadCardCertificate(String mnCardHandle) {
        this.cardHandle = mnCardHandle;
        System.out.println("Inside readCard");
        Holder<Status> status = new Holder<>();
        Holder<X509DataInfoListType> certList = new Holder<>();

        try {
            log.fine(certificateServicePortType.toString());
            // The following line is failing:
            this.certificateServicePortType
                    .readCardCertificate(mnCardHandle, contextType, certRefList, status, certList);

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
