package health.medunited.pwdchanger.service;

import de.gematik.ws.conn.cardservicecommon.v2.CardTypeType;
import de.gematik.ws.conn.connectorcontext.v2.ContextType;
import de.gematik.ws.conn.eventservice.v7.GetCards;
import de.gematik.ws.conn.eventservice.wsdl.v7.EventService;
import de.gematik.ws.conn.eventservice.wsdl.v7.EventServicePortType;
import health.medunited.pwdchanger.security.BindingProviderConfigurer;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.TrustManager;
import javax.xml.ws.BindingProvider;


public class EventServicePort {

    private final EventServicePortType eventServicePortType;
    private final ContextType context;

    public EventServicePort(String endpoint, ContextType context, TrustManager trustManager, HostnameVerifier hostnameVerifier) {
        this.context = context;
        eventServicePortType = new EventService(getClass()
                .getResource("/EventService.wsdl"))
                .getEventServicePort();
        BindingProvider bp = (BindingProvider) eventServicePortType;
        bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpoint);
        BindingProviderConfigurer.configure(bp, trustManager, hostnameVerifier);
    }

    public String getFirstCardHandleOfType(CardTypeType cardType) {
        GetCards parameter = new GetCards();
        parameter.setContext(this.context);
        parameter.setCardType(cardType);

        try{
            return this.eventServicePortType
                    .getCards(parameter)
                    .getCards()
                    .getCard()
                    .get(0)
                    .getCardHandle();
        } catch (IndexOutOfBoundsException | de.gematik.ws.conn.eventservice.wsdl.v7.FaultMessage e) {
            throw new IllegalArgumentException(String.format("There is no card of type %s in the reader!", cardType.name()), e);
        }

    }

}
