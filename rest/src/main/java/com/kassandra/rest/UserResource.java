package com.kassandra.rest;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kassandra.repository.IUserRepository;
import com.kassandra.repository.RepositoryException;
import com.kassandra.repository.model.User;

@Controller
@RequestMapping(value = "/user")
public class UserResource {

    @Autowired
    private final IUserRepository userRepository;

    @Inject
    UserResource(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response getUser(@PathVariable("id") String id) {
        try {
            User got = userRepository.getUser(id);
            return Response.ok(got).build();
        } catch (RepositoryException ex) {
            return Response.serverError().entity(ex).build();
        }
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Response putUser(@RequestBody User user) {
        try {
            boolean sSuccess = userRepository.createUser(user);
            return Response.ok().build();
        } catch (RepositoryException ex) {
            return Response.serverError().entity(ex).build();
        }
    }

}
