package health.medunited.pwdchanger.service;


import de.gematik.ws.conn.certificateservice.wsdl.v6.FaultMessage;
import de.gematik.ws.conn.certificateservicecommon.v2.CertRefEnum;
import health.medunited.pwdchanger.security.BindingProviderConfigurer;

import de.gematik.ws.conn.certificateservice.v6.ReadCardCertificate;
import de.gematik.ws.conn.certificateservice.v6.ReadCardCertificateResponse;
import de.gematik.ws.conn.certificateservice.wsdl.v6.CertificateService;
import de.gematik.ws.conn.certificateservice.wsdl.v6.CertificateServicePortType;
import de.gematik.ws.conn.certificateservicecommon.v2.X509DataInfoListType;
import de.gematik.ws.conn.connectorcommon.v5.Status;
import de.gematik.ws.conn.connectorcontext.v2.ContextType;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.TrustManager;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;

import java.util.logging.Logger;

public class CertificateServicePort {

    private static final Logger log = Logger.getLogger(CertificateServicePort.class.getName());


    CertificateServicePortType certificateServicePortType;

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
