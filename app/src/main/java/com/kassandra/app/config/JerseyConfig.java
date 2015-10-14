package com.kassandra.app.config;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

import com.kassandra.rest.TestService;

@ApplicationPath("api")
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        packages("com.kassandra");
        register(TestService.class);
    }

}
