//package health.medunited.pwdchanger.service;
//
//import de.gematik.ws.conn.amts.amtsservice.v1.AMTSService;
//import de.gematik.ws.conn.amts.amtsservice.v1.AMTSServicePortType;
//import health.medunited.pwdchanger.security.BindingProviderConfigurer;
//
//import javax.net.ssl.HostnameVerifier;
//import javax.net.ssl.TrustManager;
//import javax.xml.ws.BindingProvider;
//
//public class AmtsServicePort {
//    private AMTSServicePortType amtsServicePort;
//
//    public AmtsServicePort(String endpoint, TrustManager trustManager, HostnameVerifier hostnameVerifier) {
//        amtsServicePort = new AMTSService(getClass()
//                .getResource("/AMTSService.wsdl"))
//                .getAMTSServicePort();
//        BindingProvider bp = (BindingProvider) amtsServicePort;
//        bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpoint);
//        BindingProviderConfigurer.configure(bp, trustManager, hostnameVerifier);
//    }
//
//    public AMTSServicePortType getPort() {
//        return this.amtsServicePort;
//    }
//
//}
