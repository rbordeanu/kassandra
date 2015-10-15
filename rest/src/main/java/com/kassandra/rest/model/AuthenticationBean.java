package com.kassandra.rest.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthenticationBean {
    private final String username;
    private final String password;

    @JsonCreator
    public AuthenticationBean(@JsonProperty("username") String username,
        @JsonProperty("password") String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
