package com.kassandra.rest;

import org.springframework.stereotype.Service;

import com.kassandra.rest.ITestService;

@Service
public class TestService  implements ITestService {
    public String test() {
        return "Fuck Guice! Go Spring";
    }
}
