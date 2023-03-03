package health.medunited.pwdchanger.exception;

public class ConnectorCardCertificateReadException extends ConnectorException {
    public ConnectorCardCertificateReadException() {

    }

    public ConnectorCardCertificateReadException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectorCardCertificateReadException(String message) {
        super(message);
    }
}
