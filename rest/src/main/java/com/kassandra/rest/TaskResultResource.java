package com.kassandra.rest;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kassandra.test.EmailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kassandra.repository.ITaskRepository;
import com.kassandra.repository.ITaskResultRepository;
import com.kassandra.repository.IUserRepository;
import com.kassandra.repository.RepositoryException;
import com.kassandra.repository.model.Task;
import com.kassandra.repository.model.TaskResult;
import com.kassandra.repository.model.User;
import com.kassandra.test.Checker;
import com.kassandra.test.SubmitScore;

@Controller
@RequestMapping(value = "/result")
public class TaskResultResource {

    private final ITaskRepository taskRepository;
    private final IUserRepository userRepository;
    private final ITaskResultRepository resultRepository;

    @Inject TaskResultResource(ITaskRepository taskRepository, IUserRepository userRepository,
            ITaskResultRepository resultRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.resultRepository = resultRepository;
    }

    @RequestMapping(value = "/answer/{task_id}/{user_id}/{answer}", method = RequestMethod.GET)
    public @ResponseBody SubmitScore answerQuestion(@PathVariable("task_id") final String task_id,
            @PathVariable("user_id") final String user_id, @PathVariable("answer") String answer) {

        try {
            Task task = taskRepository.getTask(task_id);
            String uid = UUID.randomUUID().toString();
            double score = Checker.check(task, answer);

            TaskResult result = new TaskResult(uid, task_id, user_id, score);

            double percentage = 0;

            List<TaskResult> otherResults = resultRepository.getAllByTask(task_id);

            for(TaskResult otherResult : otherResults){
                if(otherResult.getScore() < score){
                    percentage ++;
                }
            }

            SubmitScore submitScore = new SubmitScore(""+score, ""+percentage*100/otherResults.size());

            resultRepository.createTaskResult(result);

            User submitter = userRepository.getUser(user_id);

            ObjectMapper objectMapper = new ObjectMapper();
            try {
                String jsonUser = objectMapper.writeValueAsString(submitter);

                EmailSender.send(jsonUser);
            } catch(JsonProcessingException e) {
                e.printStackTrace();
            }
            return submitScore;

        } catch (RepositoryException e) {
            throw new RuntimeException(e);
        }
    }
}
