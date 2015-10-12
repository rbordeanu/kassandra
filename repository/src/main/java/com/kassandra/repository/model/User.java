package com.kassandra.repository.model;

public class User {
    private String _id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;

    public User() {
        password = "";
        email = "";
        username = "";
        firstName = "";
        lastName = "";
        _id = "";
    }

    public User(String id, String firstName, String lastName, String username, String email,
        String password) {
        _id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
