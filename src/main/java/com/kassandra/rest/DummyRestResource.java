package com.kassandra.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/dummy")
public class DummyRestResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDummyResponse() {
        return Response.ok().build();
    }
}