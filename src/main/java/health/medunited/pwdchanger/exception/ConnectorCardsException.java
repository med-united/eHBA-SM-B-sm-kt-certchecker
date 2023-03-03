package health.medunited.pwdchanger.exception;

public class ConnectorCardsException extends Exception {

    public ConnectorCardsException(String msg) {
        super(msg);
    }

    public ConnectorCardsException(String msg, Throwable e) {
        super(msg, e);
    }
}
