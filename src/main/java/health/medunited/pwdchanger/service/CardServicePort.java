package health.medunited.pwdchanger.service;

import de.gematik.ws.conn.cardservice.v8.PinStatusEnum;
import de.gematik.ws.conn.cardservice.wsdl.v8.CardService;
import de.gematik.ws.conn.cardservice.wsdl.v8.CardServicePortType;
import de.gematik.ws.conn.cardservicecommon.v2.PinResultEnum;
import de.gematik.ws.conn.connectorcommon.v5.Status;
import de.gematik.ws.conn.connectorcontext.v2.ContextType;
import health.medunited.pwdchanger.model.gematik.ChangePinResponse;
import health.medunited.pwdchanger.model.gematik.GetPinStatusResponse;
import health.medunited.pwdchanger.security.BindingProviderConfigurer;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.TrustManager;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;
import java.math.BigInteger;

public class CardServicePort {

    private final CardServicePortType cardServicePortType;
    private final ContextType context;

    public CardServicePort(String endpoint, ContextType context, TrustManager trustManager, HostnameVerifier hostnameVerifier) {
        this.context = context;
        cardServicePortType = new CardService(getClass()
                .getResource("/CardService.wsdl"))
                .getCardServicePort();
        BindingProvider bp = (BindingProvider) cardServicePortType;
        bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpoint);
        BindingProviderConfigurer.configure(bp, trustManager, hostnameVerifier);
    }

    public GetPinStatusResponse getPinStatus(String cardHandle) {
        Holder<Status> status = new Holder<>();
        Holder<PinStatusEnum> pinResultEnum = new Holder<>();
        Holder<BigInteger> leftTries = new Holder<>();
        try {
            this.cardServicePortType.getPinStatus(context, cardHandle, "PIN.QES", status, pinResultEnum, leftTries);
            return new GetPinStatusResponse(status.value, pinResultEnum.value, leftTries.value);
        } catch (de.gematik.ws.conn.cardservice.wsdl.v8.FaultMessage e) {
            e.printStackTrace();
        }
        return null;
    }

    public ChangePinResponse verifyPin(String cardHandle) {
        Holder<Status> status = new Holder<>();
        Holder<PinResultEnum> pinResultEnum = new Holder<>();
        Holder<BigInteger> leftTries = new Holder<>();
        try{
            this.cardServicePortType.verifyPin(context, cardHandle, "PIN.QES", status, pinResultEnum, leftTries);
            return new ChangePinResponse(status.value, pinResultEnum.value, leftTries.value);
        } catch (de.gematik.ws.conn.cardservice.wsdl.v8.FaultMessage e) {
            e.printStackTrace();
        }
        return null;
    }

    public ChangePinResponse changePin(String cardHandle) {
        Holder<Status> status = new Holder<>();
        Holder<PinResultEnum> pinResultEnum = new Holder<>();
        Holder<BigInteger> leftTries = new Holder<>();
        try {
            this.cardServicePortType.changePin(context, cardHandle, "PIN.QES", status, pinResultEnum, leftTries);
            return new ChangePinResponse(status.value, pinResultEnum.value, leftTries.value);
        } catch (de.gematik.ws.conn.cardservice.wsdl.v8.FaultMessage e) {
            e.printStackTrace();
        }
        return null;
    }

}
