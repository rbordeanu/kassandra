package com.kassandra.rest;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import com.google.inject.Inject;
import com.kassandra.repository.IQuestion;
import com.kassandra.repository.IQuestionRepository;
import com.kassandra.repository.RepositoryException;

/**
 * Created by madatoia on 10/13/2015.
 */

@Path("questions")
public class QuestionResource {

    private final IQuestionRepository questionRepository;

    @Inject
    QuestionResource(IQuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @GET
    @Path("{id}")
    @Produces(APPLICATION_JSON)
    @Consumes(APPLICATION_JSON)
    public Response getUser(@PathParam("id") String id) {
        try {
            IQuestion got = questionRepository.getQuestion(id);
            return Response.ok(got).build();
        } catch (RepositoryException ex) {
            return Response.serverError().entity(ex).build();
        }
    }

    @POST
    @Consumes(APPLICATION_JSON)
    public Response postQuestion(IQuestion question) {
        try {
            boolean sSuccess = questionRepository.createQuestion(question);
            return Response.ok().build();
        } catch (RepositoryException ex) {
            return Response.serverError().entity(ex).build();
        }
    }

}
