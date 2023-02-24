package health.medunited.pwdchanger.resource;

import health.medunited.pwdchanger.service.Fruit;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Path("/fruits")
public class FruitResource {

    @GET
    @Path("/status")
    @Produces(MediaType.TEXT_PLAIN)
    public String getPinInfo() {
        System.out.println(" ");
        System.out.println("Inside Fruit Resource");
        Fruit f = new Fruit();
        return "Hola!!!" + f.returnString;
    }

}
