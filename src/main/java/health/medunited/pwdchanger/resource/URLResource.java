package health.medunited.pwdchanger.resource;
import health.medunited.pwdchanger.service.CertificateReadService;
import health.medunited.pwdchanger.service.PasswordChangerService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Path("/")
public class URLResource {

    @Inject
    PasswordChangerService passwordChangerService;

    @Inject
    CertificateReadService certificateReadService;

    @POST
    @Path("/changePIN")
    @Produces(MediaType.TEXT_PLAIN)
    public String setPin() {
        System.out.println(" ");
        System.out.println("Inside Resource File");
        return passwordChangerService.changePin();
    }

    @GET
    @Path("/getCard")
    @Produces(MediaType.TEXT_PLAIN)
    public String getPinInfo() {
        System.out.println(" ");
        System.out.println("Inside Resource File");
        return passwordChangerService.getCard();
    }

    @GET
    @Path("/getStatus")
    @Produces(MediaType.TEXT_PLAIN)
    public String getPINStatus() {
        System.out.println(" ");
        System.out.println("Inside Resource File");
        return passwordChangerService.getCardDetails();
    }


    @GET
    @Path("/readCert")
    @Produces(MediaType.TEXT_PLAIN)
    public String readCertificate() {
        System.out.print("\033[H\033[2J");
        System.out.flush();

        System.out.println(" ");
        System.out.println("Inside Resource File");

        return certificateReadService.getCardCertificateFromPort();
        //return certificateReadService.readCardCertificate();
    }

    @GET
    @Path("/verifyCert")
    @Produces(MediaType.TEXT_PLAIN)
    public String checkCertificate() {

        System.out.print("\033[H\033[2J");
        System.out.flush();

        System.out.println(" ");
        System.out.println("Inside Resource File");

        certificateReadService.getCardCertificateFromPort();

        return certificateReadService.verifyCertificateFromPort(certificateReadService.getX509Certificate());
    }

}
