package com.kassandra.rest;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import com.google.inject.Inject;
import com.kassandra.repository.IQuestion;
import com.kassandra.repository.IQuestionRepository;
import com.kassandra.repository.IUserRepository;
import com.kassandra.repository.RepositoryException;
import com.kassandra.repository.model.User;
import com.kassandra.test.Test;
import com.kassandra.repository.model.Level;

@Path("test")
public class TestResource {

    private final IQuestionRepository questionRepository;
    private final IUserRepository userRepository;
    private final Map<String, Test> activeTestsMap = new HashMap<String, Test>();

    @Inject
    TestResource(IQuestionRepository questionRepository, IUserRepository userRepository) {
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
    }

    @GET
    @Path("{start}")
    @Produces(APPLICATION_JSON)
    @Consumes(APPLICATION_JSON)
    public Response
            startTest(@PathParam("user_id") String user_id, @PathParam("testType") Level type) {
        Test newTest = new Test(questionRepository, user_id);
        String uniqueID = UUID.randomUUID().toString();
        activeTestsMap.put(uniqueID, newTest);

        try {
            newTest.init(type);
            return Response.ok(uniqueID).build();

        } catch (RepositoryException e) {
            return Response.serverError().entity(e).build();
        }

    }

    @GET
    @Path("{done}")
    @Produces(APPLICATION_JSON)
    @Consumes(APPLICATION_JSON)
    public Response startTest(@PathParam("id") String id) {
        Test currentTest = activeTestsMap.get(id);
        try {
            User testTaker = userRepository.getUser(currentTest.getUserId());
            double score = currentTest.summarise(testTaker.toString());
            activeTestsMap.remove(id);

            return Response.ok(score).build();
        } catch (RepositoryException e) {
            return Response.serverError().entity(e).build();
        }


    }

    @GET
    @Path("{nextQuestion}")
    @Produces(APPLICATION_JSON)
    @Consumes(APPLICATION_JSON)
    public Response getNextQ(@PathParam("id") String id) {
        Test currentTest = activeTestsMap.get(id);
        IQuestion question = currentTest.nextQuestion();

        return Response.ok(question).build();
    }

    @GET
    @Path("{previousQuestion}")
    @Produces(APPLICATION_JSON)
    @Consumes(APPLICATION_JSON)
    public Response getPrevQ(@PathParam("id") String id) {
        Test currentTest = activeTestsMap.get(id);
        IQuestion question = currentTest.previousQuestion();

        return Response.ok(question).build();
    }

    @GET
    @Path("{goToQuestion}")
    @Produces(APPLICATION_JSON)
    @Consumes(APPLICATION_JSON)
    public Response getPrevQ(@PathParam("id") String id, @PathParam("index") int questionInd) {
        Test currentTest = activeTestsMap.get(id);
        IQuestion question = currentTest.goToQuestion(questionInd);

        return Response.ok(question).build();
    }

    @POST
    @Path("{answer}")
    @Consumes(APPLICATION_JSON)
    public Response postQuestion(@PathParam("id") final String uid, String answer) {
        Test currentTest = activeTestsMap.get(uid);
        currentTest.answerCurrentQuestion(answer);

        return Response.ok().build();
    }

}
