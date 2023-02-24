package health.medunited.pwdchanger.resource;

import health.medunited.pwdchanger.service.PasswordChangerService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Path("/password")
public class PasswordChangerResource {

    @Inject
    PasswordChangerService passwordChangerService;

    @GET
    @Path("/getCard")
    @Produces(MediaType.TEXT_PLAIN)
    public String getPinInfo() {
        return passwordChangerService.getCard();
    }

}
