package com.kassandra.rest;

import java.util.List;
import java.util.UUID;

import com.kassandra.repository.ITaskRepository;
import com.kassandra.repository.ITaskResultRepository;
import com.kassandra.repository.IUserRepository;
import com.kassandra.repository.RepositoryException;
import com.kassandra.repository.model.Task;
import com.kassandra.repository.model.TaskResult;
import com.kassandra.repository.model.User;
import com.kassandra.test.Checker;
import com.kassandra.test.EmailSender;
import com.kassandra.test.SubmitScore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/result")
public class TaskResultResource {

    private final ITaskRepository taskRepository;
    private final IUserRepository userRepository;
    private final ITaskResultRepository resultRepository;

    @Autowired
    TaskResultResource(ITaskRepository taskRepository, IUserRepository userRepository,
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

            for (TaskResult otherResult : otherResults) {
                if (otherResult.getScore() < score) {
                    percentage++;
                }
            }

            SubmitScore submitScore = new SubmitScore("" + score, "" + percentage * 100
                    / otherResults.size());

            resultRepository.createTaskResult(result);

            User submitter = userRepository.getUser(user_id);

            String userInfo = "New candidate: " + submitter.getFirstName() + " "
                    + submitter.getLastName() + "\nEmail: " + submitter.getEmail() + "\nGot: "
                    + score + " on " + task.getName();

            EmailSender.send(userInfo);

            return submitScore;

        } catch (RepositoryException e) {
            throw new RuntimeException(e);
        }
    }


    @RequestMapping(value = "/accuracy/{task_id}", method = RequestMethod.GET)
    public @ResponseBody String accuracy(@PathVariable("task_id") final String task_id) {

        try {
            List<TaskResult> results = resultRepository.getAllByTask(task_id);

            double avg = 0;
            for(TaskResult result : results){
                avg += result.getScore();
            }

            return "" + (avg/results.size());

        } catch (RepositoryException e) {
            throw new RuntimeException(e);
        }

    }
}
