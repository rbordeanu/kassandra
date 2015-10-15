package com.kassandra.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/compiler")
public class CompilerResource {

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody boolean getDummyResponse() {
        return true;
    }
}
