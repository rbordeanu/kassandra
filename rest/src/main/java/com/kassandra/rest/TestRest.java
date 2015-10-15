package com.kassandra.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Path("/spring")
@Controller
public class TestRest {

    @Autowired
    TestService testService;

    @GET
    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody String test() {
        String result = testService.test();
        /*return Response.status(200).entity(result).build();*/
        return testService.test();
    }
}
