package health.medunited.pwdchanger.resource;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Path("/fruits")
public class FruitResource {

    @GET
    @Path("/status")
    @Produces(MediaType.TEXT_PLAIN)
    public String getPinInfo() {
        return "Hola!!!";
    }

}
