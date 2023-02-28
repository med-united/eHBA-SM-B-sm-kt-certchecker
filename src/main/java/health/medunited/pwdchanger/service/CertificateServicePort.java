package health.medunited.pwdchanger.service;

import de.gematik.ws.conn.certificateservice.v6.ReadCardCertificateResponse;
import de.gematik.ws.conn.certificateservice.wsdl.v6.CertificateServicePortType;
import de.gematik.ws.conn.connectorcontext.v2.ContextType;

public class CertificateServicePort {

    private final ContextType context;

    private final CertificateServicePortType certificateServicePortType;
    public CertificateServicePort(ContextType context, CertificateServicePortType certificateServicePortType, CertificateServicePortType certificateServicePortType1) {
        this.context = context;
        this.certificateServicePortType = certificateServicePortType1;
    }

    public ReadCardCertificateResponse verifyCertificate() {
        return null;
    }
}
