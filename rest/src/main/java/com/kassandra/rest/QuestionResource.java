package com.kassandra.rest;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import com.kassandra.repository.IQuestion;
import com.kassandra.repository.ITaskRepository;
import com.kassandra.repository.RepositoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/question")
public class QuestionResource {

    @Autowired
    private final ITaskRepository questionRepository;

    @Inject
    QuestionResource(ITaskRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response getUser(@PathVariable("id") String id) {
        try {
            IQuestion got = questionRepository.getQuestion(id);
            return Response.ok(got).build();
        } catch (RepositoryException ex) {
            return Response.serverError().entity(ex).build();
        }
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Response postQuestion(IQuestion question) {
        try {
            boolean sSuccess = questionRepository.createQuestion(question);
            return Response.ok().build();
        } catch (RepositoryException ex) {
            return Response.serverError().entity(ex).build();
        }
    }

}
