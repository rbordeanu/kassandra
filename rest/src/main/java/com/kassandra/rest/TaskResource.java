package com.kassandra.rest;

import javax.inject.Inject;
import javax.ws.rs.GET;

import com.kassandra.repository.ITaskRepository;
import com.kassandra.repository.IUserRepository;
import com.kassandra.repository.RepositoryException;
import com.kassandra.repository.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/task")
public class TaskResource {

    @Autowired
    private final ITaskRepository taskRepository;
       
    @Inject
    TaskResource(ITaskRepository taskRepository, IUserRepository userRepository) {
        this.taskRepository = taskRepository;
    }

    @GET
    @RequestMapping(value = "/get/{task_id}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Task
            startTest(@PathVariable("task_id") String task_id) {

        try {
            return taskRepository.getTask(task_id);
        } catch (RepositoryException e) {
            throw new RuntimeException(e);
        }
    }
}
