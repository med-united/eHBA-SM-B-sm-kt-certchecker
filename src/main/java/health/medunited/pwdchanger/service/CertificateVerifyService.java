package health.medunited.pwdchanger.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class CertificateVerifyService {

    @Inject
    public void CertificateVerifyService() {
        System.out.println("Constructor of CertVerify");
    }

    public String verifyCert() {
        return "certVer";
    }



}
