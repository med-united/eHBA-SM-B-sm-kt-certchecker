package health.ere.ps.event;


import javax.json.JsonObject;
import javax.websocket.Session;


public class ActivateComfortSignatureEvent extends AbstractEvent {

    public ActivateComfortSignatureEvent(JsonObject jsonObject) {
        parseRuntimeConfig(jsonObject);
    }

    public ActivateComfortSignatureEvent(JsonObject object, Session replyTo, String id) {
        this(object);
        this.replyTo = replyTo;
        this.id = id;
    }
}
