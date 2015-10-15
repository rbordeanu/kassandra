package com.kassandra.rest;

import java.util.UUID;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kassandra.repository.ITaskRepository;
import com.kassandra.repository.IUserRepository;
import com.kassandra.repository.RepositoryException;
import com.kassandra.repository.model.Task;
import com.kassandra.repository.model.TaskResult;
import com.kassandra.repository.model.User;

@Controller
@RequestMapping(value = "/result")
public class TaskResultResource {

    private final ITaskRepository taskRepository;
    private final IUserRepository userRepository;

    @Inject
    TaskResultResource(ITaskRepository taskRepository, IUserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/answer/{task_id}/{user_id}/{answer}", method = RequestMethod.GET)
    public @ResponseBody TaskResult answerQuestion(@PathVariable("task_id") final String task_id,
            @PathVariable("user_id") final String uset_id, @PathVariable("answer") String answer) {

        try {
            Task task = taskRepository.getTask(task_id);
            User user = userRepository.getUser(uset_id);
            String uid = UUID.randomUUID().toString();



        } catch (RepositoryException e) {
            e.printStackTrace();
        }

        return null;
    }
}
