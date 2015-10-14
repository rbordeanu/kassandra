package com.kassandra.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Path("/spring")
@Component
public class TestRest {

    @Autowired
    TestService testService;

    @GET
    @Path("/test")
    public String test() {
        String result = testService.test();
        /*return Response.status(200).entity(result).build();*/
        return testService.test();
    }
}
