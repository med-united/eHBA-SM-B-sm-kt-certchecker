package health.medunited.pwdchanger.service;

import java.math.BigInteger;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.xml.ws.Holder;

import health.medunited.pwdchanger.config.RuntimeConfig;
import health.medunited.pwdchanger.connector.ConnectorCardCertificateReadException;
import health.medunited.pwdchanger.crypto.CryptoLoader;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

import de.gematik.ws.conn.cardservicecommon.v2.PinResultEnum;
import de.gematik.ws.conn.certificateservice.v6.ReadCardCertificate;
import de.gematik.ws.conn.certificateservice.v6.ReadCardCertificateResponse;
import de.gematik.ws.conn.certificateservice.wsdl.v6.FaultMessage;
import de.gematik.ws.conn.certificateservicecommon.v2.CertRefEnum;
import de.gematik.ws.conn.certificateservicecommon.v2.X509DataInfoListType;
import de.gematik.ws.conn.connectorcommon.v5.Status;
import org.apache.commons.lang3.ArrayUtils;
import health.medunited.pwdchanger.config.RuntimeConfig;
import health.medunited.pwdchanger.connector.ConnectorCardCertificateReadException;
import health.medunited.pwdchanger.service.MultiConnectorServicesProvider;
import health.medunited.pwdchanger.crypto.CryptoLoader;

@ApplicationScoped
public class CardCertificateReaderService {

    private static final Logger log = Logger.getLogger(CardCertificateReaderService.class.getName());
    private static final String STATUS_OK = "OK";

    @Inject
    MultiConnectorServicesProvider connectorServicesProvider;


    public X509Certificate retrieveSmcbCardCertificate(String cardHandle)
        throws ConnectorCardCertificateReadException {
        return retrieveSmcbCardCertificate(cardHandle, null);
    }

    /**
     * Reads the AUT certificate of a card managed in the connector.
     *
     * @param cardHandle The handle of the card.
     * @return The card's AUT certificate.
     */
    public X509Certificate retrieveSmcbCardCertificate(String cardHandle, RuntimeConfig runtimeConfig)
            throws ConnectorCardCertificateReadException {

        byte[] connector_cert_auth = new byte[0];

        ReadCardCertificateResponse readCardCertificateResponse =
                doReadCardCertificate(cardHandle, runtimeConfig);

        Status status = readCardCertificateResponse.getStatus();
        if (status != null && status.getResult().equals(STATUS_OK)) {
            X509DataInfoListType x509DataInfoList = readCardCertificateResponse.getX509DataInfoList();
            List<X509DataInfoListType.X509DataInfo> x509DataInfos = x509DataInfoList.getX509DataInfo();
            if (CollectionUtils.isNotEmpty(x509DataInfos)) {
                log.log(Level.INFO, "Certificate list size = " + x509DataInfos.size());

                connector_cert_auth = x509DataInfos.get(0).getX509Data().getX509Certificate();
            }
        }

        if (ArrayUtils.isEmpty(connector_cert_auth)) {
            throw new ConnectorCardCertificateReadException("Could not retrieve connector smart " +
                    "card certificate from the connector.");
        }
        X509Certificate x509Certificate;

        try {
            x509Certificate = CryptoLoader.getCertificateFromAsn1DERCertBytes(connector_cert_auth);
        } catch (Throwable e) {
            throw new ConnectorCardCertificateReadException("Error getting X509Certificate", e);
        }

        return x509Certificate;
    }

    /**
     * Reads the AUT certificate of a card.
     *
     * @param cardHandle The handle of the card whose AUT certificate is to be read.
     * @return The read AUT certificate.
     */
    public ReadCardCertificateResponse doReadCardCertificate(String cardHandle, RuntimeConfig runtimeConfig)
            throws ConnectorCardCertificateReadException {

        ReadCardCertificate.CertRefList certRefList = new ReadCardCertificate.CertRefList();
        certRefList.getCertRef().add(CertRefEnum.C_AUT);

        Holder<Status> statusHolder = new Holder<>();
        Holder<X509DataInfoListType> certHolder = new Holder<>();

        try {
            connectorServicesProvider.getCertificateServicePortType(runtimeConfig).readCardCertificate(cardHandle, connectorServicesProvider.getContextType(runtimeConfig), certRefList,
                    statusHolder, certHolder);
        } catch (FaultMessage faultMessage) {
            // Zugriffsbedingungen nicht erfüllt
            boolean code4085 = faultMessage.getFaultInfo().getTrace().stream()
                    .anyMatch(t -> t.getCode().equals(BigInteger.valueOf(4085L)));

            if (code4085) {
                Holder<Status> status = new Holder<>();
                Holder<PinResultEnum> pinResultEnum = new Holder<>();
                Holder<BigInteger> error = new Holder<>();
                try {
                    connectorServicesProvider.getCardServicePortType(runtimeConfig).verifyPin(connectorServicesProvider.getContextType(runtimeConfig), cardHandle, "PIN.SMC", status, pinResultEnum, error);
                    doReadCardCertificate(cardHandle, runtimeConfig);
                } catch (de.gematik.ws.conn.cardservice.wsdl.v8.FaultMessage e) {
                    throw new ConnectorCardCertificateReadException("Could not get certificate", faultMessage);
                }
            } else {
                throw new ConnectorCardCertificateReadException("Could not get certificate", faultMessage);
            }
        }

        ReadCardCertificateResponse readCardCertificateResponse = new ReadCardCertificateResponse();

        readCardCertificateResponse.setStatus(statusHolder.value);
        readCardCertificateResponse.setX509DataInfoList(certHolder.value);

        return readCardCertificateResponse;
    }
}
