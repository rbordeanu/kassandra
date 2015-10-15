package com.kassandra.rest;

import java.util.List;

import javax.inject.Inject;

import com.kassandra.repository.ITaskRepository;
import com.kassandra.repository.IUserRepository;
import com.kassandra.repository.RepositoryException;
import com.kassandra.repository.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/task")
public class TaskResource {

    @Autowired
    private final ITaskRepository taskRepository;

    @Inject
    TaskResource(ITaskRepository taskRepository, IUserRepository userRepository) {
        this.taskRepository = taskRepository;
    }

    @RequestMapping(value = "/get/{task_id}",
        method = RequestMethod.GET,
        produces = "application/json")
    public @ResponseBody Task getTask(@PathVariable("task_id") String task_id) {

        try {
            return taskRepository.getTask(task_id);
        } catch (RepositoryException e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value = "/get/", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<Task> getALL() {

        try {
            return taskRepository.getAll();
        } catch (RepositoryException e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value = "get/{user_id}",
        method = RequestMethod.GET,
        produces = "application/json")
    public @ResponseBody List<Task> getAvailable(@PathVariable("user_id") String user_id) {
        try {
            return taskRepository.getAvailableTasks(user_id);
        } catch (RepositoryException e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(method = RequestMethod.PUT)
    public @ResponseBody boolean putTask(@RequestBody Task task) {
        try {
            return taskRepository.createTask(task);
        } catch (RepositoryException ex) {
            throw new RuntimeException(ex);
        }
    }
}
