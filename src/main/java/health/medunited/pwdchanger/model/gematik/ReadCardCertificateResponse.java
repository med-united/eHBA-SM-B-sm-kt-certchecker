
package health.medunited.pwdchanger.model.gematik;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import de.gematik.ws.conn.certificateservicecommon.v2.X509DataInfoListType;
import de.gematik.ws.conn.connectorcommon.v5.Status;


/**
 * <p>Java class for anonymous complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://ws.gematik.de/conn/ConnectorCommon/v5.0}Status"/&gt;
 *         &lt;element ref="{http://ws.gematik.de/conn/CertificateServiceCommon/v2.0}X509DataInfoList"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "status",
        "x509DataInfoList"
})
@XmlRootElement(name = "ReadCardCertificateResponse")
public class ReadCardCertificateResponse {

    @XmlElement(name = "Status", namespace = "http://ws.gematik.de/conn/ConnectorCommon/v5.0", required = true)
    protected Status status;
    @XmlElement(name = "X509DataInfoList", namespace = "http://ws.gematik.de/conn/CertificateServiceCommon/v2.0", required = true)
    protected X509DataInfoListType x509DataInfoList;

    /**
     * Gets the value of the status property.
     *
     * @return
     *     possible object is
     *     {@link Status }
     *
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     *
     * @param value
     *     allowed object is
     *     {@link Status }
     *
     */
    public void setStatus(Status value) {
        this.status = value;
    }

    /**
     * Gets the value of the x509DataInfoList property.
     *
     * @return
     *     possible object is
     *     {@link X509DataInfoListType }
     *
     */
    public X509DataInfoListType getX509DataInfoList() {
        return x509DataInfoList;
    }

    /**
     * Sets the value of the x509DataInfoList property.
     *
     * @param value
     *     allowed object is
     *     {@link X509DataInfoListType }
     *
     */
    public void setX509DataInfoList(X509DataInfoListType value) {
        this.x509DataInfoList = value;
    }

}
