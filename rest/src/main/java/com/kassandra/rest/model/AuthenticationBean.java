package com.kassandra.rest.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthenticationBean {
    private final String email;
    private final String password;

    @JsonCreator
    public AuthenticationBean(@JsonProperty("email") String email,
        @JsonProperty("password") String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
