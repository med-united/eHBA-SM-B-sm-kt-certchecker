package health.medunited.pwdchanger.model.gematik;

import de.gematik.ws.conn.cardservicecommon.v2.PinResultEnum;
import de.gematik.ws.conn.connectorcommon.v5.Status;

import java.io.Serializable;
import java.math.BigInteger;

public class ChangePinResponse implements Serializable {

    Status status;
    PinResultEnum pinResultEnum;
    BigInteger leftTries;

    public ChangePinResponse(Status status, PinResultEnum pinResultEnum, BigInteger leftTries) {
        this.status = status;
        this.pinResultEnum = pinResultEnum;
        this.leftTries = leftTries;
    }
    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public PinResultEnum getpinResultEnum() {
        return this.pinResultEnum;
    }

    public void setpinResultEnum(PinResultEnum pinResultEnum) {
        this.pinResultEnum = pinResultEnum;
    }

    public BigInteger getLeftTries() {
        return this.leftTries;
    }

    public void setLeftTries(BigInteger leftTries) {
        this.leftTries = leftTries;
    }

}
