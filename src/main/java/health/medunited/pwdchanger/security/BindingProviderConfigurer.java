package health.medunited.pwdchanger.security;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.xml.ws.BindingProvider;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public class BindingProviderConfigurer {

    public static void configure(BindingProvider bindingProvider, TrustManager trustManager, HostnameVerifier hostnameVerifier) {
//        SSLContext sslContext;
//        try {
//            sslContext = SSLContext.getInstance("TLS");
//        } catch (NoSuchAlgorithmException e) {
//            throw new IllegalStateException("Unable to load SSLcontext TLS", e);
//        }
//
//        if (sslContext != null) {
//            try {
//                sslContext.init(null, new TrustManager[] { trustManager }, null);
//            } catch (KeyManagementException e) {
//                throw new IllegalStateException("Unable to configure TrustManager", e);
//            }
//            bindingProvider.getRequestContext()
//                    .put("com.sun.xml.ws.transport.https.client.SSLSocketFactory",
//                            sslContext.getSocketFactory());
//        }

        bindingProvider.getRequestContext()
                .put("com.sun.xml.ws.transport.https.client.hostname.verifier", hostnameVerifier);
    }
}
