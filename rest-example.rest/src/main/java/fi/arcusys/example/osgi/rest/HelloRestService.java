package fi.arcusys.example.osgi.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("hello")
public interface HelloRestService {

    @GET
    @Produces("application/json")
    @Path("/{name}")
    String getName(@PathParam("name") String name);

}
