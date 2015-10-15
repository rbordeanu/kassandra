package com.kassandra.rest;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.kassandra.repository.IQuestion;
import com.kassandra.repository.IQuestionRepository;
import com.kassandra.repository.IUserRepository;
import com.kassandra.repository.RepositoryException;
import com.kassandra.repository.model.Level;
import com.kassandra.repository.model.User;
import com.kassandra.test.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/test")
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
    @RequestMapping(value = "/start/{userId}/{testType}", method = RequestMethod.GET)
    public Response
            startTest(@PathVariable("userId") String user_id, @PathVariable("testType") Level type) {
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

    @RequestMapping(value = "/stop/{id}", method = RequestMethod.GET)
    public Response stopTest(@PathVariable("id") String id) {
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

    @Path("/nextQuestion/{id}")
    @RequestMapping(value = "/nextQuestion/{id}", method = RequestMethod.GET)
    public Response getNextQ(@PathVariable("id") String id) {
        Test currentTest = activeTestsMap.get(id);
        IQuestion question = currentTest.nextQuestion();

        return Response.ok(question).build();
    }

    @RequestMapping(value = "/previousQuestion/{id}", method = RequestMethod.GET)
    public Response getPrevQ(@PathVariable("id") String id) {
        Test currentTest = activeTestsMap.get(id);
        IQuestion question = currentTest.previousQuestion();

        return Response.ok(question).build();
    }

    @RequestMapping(value = "/goToQuestion/{id}/{index}", method = RequestMethod.GET)
    public Response getPrevQ(@PathVariable("id") String id, @PathVariable("index") int questionInd) {
        Test currentTest = activeTestsMap.get(id);
        IQuestion question = currentTest.goToQuestion(questionInd);

        return Response.ok(question).build();
    }

    @RequestMapping(value = "/answer/{id}/{answer}", method = RequestMethod.GET)
    public Response answerQuestion(@PathVariable("id") final String uid, @PathVariable("answer") String answer) {
        Test currentTest = activeTestsMap.get(uid);
        currentTest.answerCurrentQuestion(answer);

        return Response.ok().build();
    }

}
