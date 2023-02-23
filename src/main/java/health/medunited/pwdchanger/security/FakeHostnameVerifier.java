package health.medunited.pwdchanger.security;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * This class implements a fake hostname verificator, trusting any host name.
 *
 * @author Francis Labrie
 */
public class FakeHostnameVerifier implements HostnameVerifier {

    /**
     * Always return true, indicating that the host name is
     * an acceptable match with the server's authentication scheme.
     *
     * @param hostname the host name.
     * @param session  the SSL session used on the connection to host.
     * @return the true boolean value indicating the host name is trusted.
     */
    public boolean verify(String hostname, SSLSession session) {
        return (true);
    }
}