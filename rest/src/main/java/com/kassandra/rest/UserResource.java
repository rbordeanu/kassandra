package com.kassandra.rest;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import com.google.inject.Inject;
import com.kassandra.repository.IUserRepository;
import com.kassandra.repository.RepositoryException;
import com.kassandra.repository.model.User;

@Path("users")
public class UserResource {
    private final IUserRepository userRepository;

    @Inject
    UserResource(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GET
    @Path("{id}")
    @Produces(APPLICATION_JSON)
    @Consumes(APPLICATION_JSON)
    public Response getUser(@PathParam("id") String id) {
        try {
            User got = userRepository.getUser(id);
            return Response.ok(got).build();
        } catch (RepositoryException ex) {
            return Response.serverError().entity(ex).build();
        }
    }

    @POST
    @Consumes(APPLICATION_JSON)
    public Response putUser(User user) {
        try {
            boolean sSuccess = userRepository.createUser(user);
            return Response.ok().build();
        } catch (RepositoryException ex) {
            return Response.serverError().entity(ex).build();
        }
    }

}
