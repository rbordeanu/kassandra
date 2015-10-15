package com.kassandra.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/test")
public class TestRest {

    @Autowired
    TestService testService;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test() {
        String result = testService.test();
        /*return Response.status(200).entity(result).build();*/
        return testService.test();
    }
}
