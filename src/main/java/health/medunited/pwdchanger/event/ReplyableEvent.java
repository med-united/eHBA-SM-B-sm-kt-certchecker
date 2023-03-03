package health.medunited.pwdchanger.event;

import java.io.Serializable;

public interface ReplyableEvent {
    public String getType();
    public Serializable getPayload();
    public String getReplyToMessageId();
}
