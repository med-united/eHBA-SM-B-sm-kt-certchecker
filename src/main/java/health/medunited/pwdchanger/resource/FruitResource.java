package health.medunited.pwdchanger.resource;

import health.medunited.pwdchanger.service.Fruit;

import java.util.Set;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Path("/fruits")
public class FruitResource {

    @GET
    @Path("/status")
    @Produces(MediaType.TEXT_PLAIN)
    public String getPinInfo(String medkationsPlan) {

        return "Hola!!!";
    }

    @POST
    public Set<Fruit> add(Fruit fruit) {
        return null;
    }

    @DELETE
    public Set<Fruit> delete(Fruit fruit) {
        return null;
    }
}
