package com.kassandra.rest;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kassandra.rest.bean.TestBean;

@Component
@Path("/spring")
public class TestRest {


    @Inject
    TestBean testBean;

    @GET
    @Path("/test")
    public Response test(){
        String result = testBean.test();
        return Response.status(200).entity(result).build();
    }
}
