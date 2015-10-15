package com.kassandra.rest.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TokenOutput {
    private final boolean type; // true if success
    private final String data; // username if success or error if failed
    private final String token;

    @JsonCreator
    public TokenOutput(@JsonProperty("type") boolean type, @JsonProperty("data") String data,
        @JsonProperty("token") String token) {
        this.type = type;
        this.data = data;
        this.token = token;
    }

    public boolean isType() {
        return type;
    }

    public String getData() {
        return data;
    }

    public String getToken() {
        return token;
    }
}
