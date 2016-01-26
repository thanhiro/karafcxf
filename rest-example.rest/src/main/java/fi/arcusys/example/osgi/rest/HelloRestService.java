package fi.arcusys.example.osgi.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("hello")
public interface HelloRestService {

    @GET
    @Path("/{name}")
    String getName(@PathParam("name") String name);

}
