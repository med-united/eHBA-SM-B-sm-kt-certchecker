
package health.medunited.pwdchanger.model.gematik;

import de.gematik.ws.conn.certificateservice.v6.VerificationResultType;
import de.gematik.ws.conn.connectorcommon.v5.Status;
import de.gematik.ws.tel.error.v2.Error;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


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
 *         &lt;element name="VerificationStatus"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="VerificationResult" type="{http://ws.gematik.de/conn/CertificateService/v6.0}VerificationResultType"/&gt;
 *                   &lt;element ref="{http://ws.gematik.de/tel/error/v2.0}Error" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="RoleList"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Role" maxOccurs="unbounded"&gt;
 *                     &lt;simpleType&gt;
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *                         &lt;maxLength value="100"/&gt;
 *                       &lt;/restriction&gt;
 *                     &lt;/simpleType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
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
    "verificationStatus",
    "roleList"
})
@XmlRootElement(name = "VerifyCertificateResponse")
public class VerifyCertificateResponse {

    @XmlElement(name = "Status", namespace = "http://ws.gematik.de/conn/ConnectorCommon/v5.0", required = true)
    protected Status status;
    @XmlElement(name = "VerificationStatus", required = true)
    protected VerificationStatus verificationStatus;
    @XmlElement(name = "RoleList", required = true)
    protected RoleList roleList;

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
     * Gets the value of the verificationStatus property.
     * 
     * @return
     *     possible object is
     *     {@link VerificationStatus }
     *     
     */
    public VerificationStatus getVerificationStatus() {
        return verificationStatus;
    }

    /**
     * Sets the value of the verificationStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link VerificationStatus }
     *     
     */
    public void setVerificationStatus(VerificationStatus value) {
        this.verificationStatus = value;
    }

    /**
     * Gets the value of the roleList property.
     * 
     * @return
     *     possible object is
     *     {@link RoleList }
     *     
     */
    public RoleList getRoleList() {
        return roleList;
    }

    /**
     * Sets the value of the roleList property.
     * 
     * @param value
     *     allowed object is
     *     {@link RoleList }
     *     
     */
    public void setRoleList(RoleList value) {
        this.roleList = value;
    }


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
     *         &lt;element name="Role" maxOccurs="unbounded"&gt;
     *           &lt;simpleType&gt;
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
     *               &lt;maxLength value="100"/&gt;
     *             &lt;/restriction&gt;
     *           &lt;/simpleType&gt;
     *         &lt;/element&gt;
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
        "role"
    })
    public static class RoleList {

        @XmlElement(name = "Role", required = true)
        protected List<String> role;

        /**
         * Gets the value of the role property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the role property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getRole().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getRole() {
            if (role == null) {
                role = new ArrayList<String>();
            }
            return this.role;
        }

    }


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
     *         &lt;element name="VerificationResult" type="{http://ws.gematik.de/conn/CertificateService/v6.0}VerificationResultType"/&gt;
     *         &lt;element ref="{http://ws.gematik.de/tel/error/v2.0}Error" minOccurs="0"/&gt;
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
        "verificationResult",
        "error"
    })
    public static class VerificationStatus {

        @XmlElement(name = "VerificationResult", required = true)
        @XmlSchemaType(name = "token")
        protected VerificationResultType verificationResult;
        @XmlElement(name = "Error", namespace = "http://ws.gematik.de/tel/error/v2.0")
        protected Error error;

        /**
         * Gets the value of the verificationResult property.
         * 
         * @return
         *     possible object is
         *     {@link VerificationResultType }
         *     
         */
        public VerificationResultType getVerificationResult() {
            return verificationResult;
        }

        /**
         * Sets the value of the verificationResult property.
         * 
         * @param value
         *     allowed object is
         *     {@link VerificationResultType }
         *     
         */
        public void setVerificationResult(VerificationResultType value) {
            this.verificationResult = value;
        }

        /**
         * 
         * 							Beschreibt für den Fall Result == "ERROR"
         * 							den Fehler für die gesamte Operation.
         * 						
         * 
         * @return
         *     possible object is
         *     {@link Error }
         *     
         */
        public Error getError() {
            return error;
        }

        /**
         * Sets the value of the error property.
         * 
         * @param value
         *     allowed object is
         *     {@link Error }
         *     
         */
        public void setError(Error value) {
            this.error = value;
        }

    }

}
