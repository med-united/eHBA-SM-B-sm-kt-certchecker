package health.medunited.pwdchanger.service;


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

public class CertificateServicePort {


    CertificateServicePortType certificateServicePortType;

    ContextType context;

    String cardHandle = "f76283c2-ef56-4273-8e23-1c187a7b95b6";//new

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

        /*
        BindingProvider bp = (BindingProvider) certificateServicePortType;
        bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpoint);
        BindingProviderConfigurer.configure(bp, trustManager, hostnameVerifier);
        certRefList = new ReadCardCertificate.CertRefList();
        certRefList.getCertRef().add(CertRefEnum.C_AUT);
         */

    }

    public String getSomething() {
        return "intel inside";
    }

    public ReadCardCertificateResponse readCardCertificate(ContextType context, String cardHandle,  ReadCardCertificate.CertRefList certRefList) {

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

    public String getCardHandle() {
        return cardHandle;
    }

    public  ReadCardCertificate.CertRefList getCertRefList() {
        return certRefList;
    }

}
