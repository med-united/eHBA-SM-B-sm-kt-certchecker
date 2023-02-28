package health.medunited.pwdchanger.service;

import de.gematik.ws.conn.cardservice.v8.PinStatusEnum;
import de.gematik.ws.conn.cardservice.wsdl.v8.CardService;
import de.gematik.ws.conn.certificateservice.v6.ReadCardCertificateResponse;
import de.gematik.ws.conn.certificateservice.wsdl.v6.CertificateService;
import de.gematik.ws.conn.certificateservice.wsdl.v6.CertificateServicePortType;
import de.gematik.ws.conn.connectorcommon.v5.Status;
import de.gematik.ws.conn.connectorcontext.v2.ContextType;
import health.medunited.pwdchanger.model.gematik.GetPinStatusResponse;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.TrustManager;
import javax.xml.ws.Holder;
import java.math.BigInteger;

public class CertificateServicePort {


    private final CertificateServicePortType certificateServicePortType;

    private final ContextType context;

    public CertificateServicePort(String endpoint, ContextType context, TrustManager trustManager, HostnameVerifier hostnameVerifier) {
        this.context = context;
        this.certificateServicePortType = new CertificateService(getClass()
                .getResource("/CertificateService.wsdl"))
                .getCertificateServicePort();
    }



    public ReadCardCertificateResponse readCardCertificate() {
        /* Parameters
        CardHandle
        Context
        CertRefList
         */

        Holder<Status> status = new Holder<>();
        Holder<PinStatusEnum> pinResultEnum = new Holder<>();
        Holder<BigInteger> leftTries = new Holder<>();
        /*
        try {
            this.cardServicePortType.getPinStatus(context, cardHandle, "PIN.QES", status, pinResultEnum, leftTries);
            return new GetPinStatusResponse(status.value, pinResultEnum.value, leftTries.value);
        } catch (de.gematik.ws.conn.cardservice.wsdl.v8.FaultMessage e) {
            e.printStackTrace();
        }*/
        return null;

    }

    public ReadCardCertificateResponse verifyCertificate() {
        return null;
    }

    public CertificateServicePortType getCertificateServicePortType() {
        return certificateServicePortType;
    }
}
