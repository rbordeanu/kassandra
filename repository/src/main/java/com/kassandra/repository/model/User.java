package com.kassandra.repository.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    private final String _id;
    private final String firstName;
    private final String lastName;
    private final String username;
    private final String email;
    private final String password;

    @JsonCreator
    public User(@JsonProperty("_id") String id, @JsonProperty("firstName") String firstName,
        @JsonProperty("lastName") String lastName, @JsonProperty("username") String username,
        @JsonProperty("email") String email, @JsonProperty("password") String password) {
        this._id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
