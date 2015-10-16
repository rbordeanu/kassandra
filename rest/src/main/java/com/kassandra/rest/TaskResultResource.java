package com.kassandra.rest;

import java.text.DecimalFormat;
import java.util.*;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.kassandra.repository.ITaskRepository;
import com.kassandra.repository.ITaskResultRepository;
import com.kassandra.repository.IUserRepository;
import com.kassandra.repository.RepositoryException;
import com.kassandra.repository.model.*;
import com.kassandra.test.Checker;
import com.kassandra.test.EmailSender;
import com.kassandra.test.SubmitScore;

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

    @RequestMapping(value = "/answerQuestion", method = RequestMethod.PUT)
    public @ResponseBody SubmitScore answerQuestion(@RequestBody TestAnswer testAnswer) {

        try {

            Task task = taskRepository.getTask(testAnswer.getTaskId());
            String uid = UUID.randomUUID().toString();
            double score = Checker.getQuizScore(task, testAnswer.getAnswers());

            TaskResult result = new TaskResult(uid, testAnswer.getTaskId(), testAnswer.getUserId(),
                    score);

            double percentage = 0;

            List<TaskResult> otherResults = resultRepository.getAllByTask(testAnswer.getTaskId());

            for (TaskResult otherResult : otherResults) {
                if (otherResult.getScore() < score) {
                    percentage++;
                }
            }
            DecimalFormat df = new DecimalFormat("#.#####");
            SubmitScore submitScore = new SubmitScore("" + score, "" + df.format(percentage * 100
                    / otherResults.size()));

            resultRepository.createTaskResult(result);

            User submitter = userRepository.getUser(testAnswer.getUserId());

            String userInfo = "New candidate: " + submitter.getFirstName() + " "
                    + submitter.getLastName() + "\nEmail: " + submitter.getEmail() + "\nGot: "
                    + score + " on " + task.getName();

            EmailSender.send(userInfo);

            return submitScore;

        } catch (RepositoryException e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value = "/answerCoding", method = RequestMethod.PUT)
    public @ResponseBody SubmitScore answerQuestion(@RequestBody CodingAnswer testCoding) {

        Task task = null;
        try {
            task = taskRepository.getTask(testCoding.getTaskId());
            double score = Checker.getCodingScore(task, testCoding.getContent());
            String uid = UUID.randomUUID().toString();
            TaskResult result = new TaskResult(uid, testCoding.getTaskId(), testCoding.getUserId(),
                    score);

            double percentage = 0;

            List<TaskResult> otherResults = resultRepository.getAllByTask(testCoding.getTaskId());

            for (TaskResult otherResult : otherResults) {
                if (otherResult.getScore() < score) {
                    percentage++;
                }
            }

            SubmitScore submitScore = new SubmitScore("" + score, "" + percentage * 100
                    / otherResults.size());

            resultRepository.createTaskResult(result);

             User submitter = userRepository.getUser(testCoding.getUserId());

            String userInfo = "New candidate: " + submitter.getFirstName() + " "
                    + submitter.getLastName() + "\nEmail: " + submitter.getEmail() + "\nGot: "
                    + score + " on " + task.getName();

            EmailSender.send(userInfo);
            return submitScore;
        } catch (RepositoryException e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value = "/statistics/{task_id}", method = RequestMethod.GET)
    public @ResponseBody Statistics getSubmissions(@PathVariable("task_id") final String task_id) {
        try {
            List<TaskResult> results = resultRepository.getAllByTask(task_id);

            double avg = 0;
            for (TaskResult result : results) {
                avg += result.getScore();
            }

            Double accuracy = avg / results.size();
            if (accuracy.equals(Double.NaN)) {
                accuracy = 0.0;
            }
            DecimalFormat df = new DecimalFormat("#.#####");
            return new Statistics(String.valueOf(results.size()), "" + df.format(accuracy));
        } catch (RepositoryException e) {
            throw new RuntimeException(e);
        }

    }

    @RequestMapping(value = "/submission/{userId}",
        method = RequestMethod.GET,
        produces = "application/json")
    public @ResponseBody List<SubmissionBean> getSubmissionsByUser(
            @PathVariable("userId") final String userId) {
        try {
            List<TaskResult> results = resultRepository.getAllByUser(userId);

            List<SubmissionBean> submissions = Lists.newArrayList();

            for(TaskResult result : results){
                Task task = taskRepository.getTask(result.getTaskId());
                DecimalFormat df = new DecimalFormat("#.#####");
                submissions.add(new SubmissionBean(task.getName(), Double.parseDouble(
                        df.format(result.getScore()))));
            }

            return submissions;
        } catch (RepositoryException e) {
            throw new RuntimeException(e);
        }

    }

    @RequestMapping(value = "/leaderboard",
        method = RequestMethod.GET,
        produces = "application/json")
    public @ResponseBody  List<LeaderboardBean> getLeaderboard() {
        try {

            List<User> users = userRepository.getAll();
            List<LeaderboardBean> leaderboard = Lists.newArrayList();

            for (User user : users) {
                List<TaskResult> results = resultRepository.getAllByUser(user.get_id());

                double avg = 0;
                for (TaskResult result : results) {
                    avg += result.getScore();
                }

                Double accuracy = avg / results.size();
                if (accuracy.equals(Double.NaN)) {
                    accuracy = 0.0;
                }

                leaderboard
                        .add(new LeaderboardBean(user.getUsername(), user.getGravatarUrl(), accuracy));
            }

            Collections.sort(leaderboard, new Comparator<LeaderboardBean>() {
                @Override public int compare(LeaderboardBean o1, LeaderboardBean o2) {
                   return o1.getScore() < o2.getScore() ? 1 : -1;
                }
            });
            return leaderboard;

        } catch (RepositoryException e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value = "/accuracy/{task_id}", method = RequestMethod.GET)
    public @ResponseBody String accuracy(@PathVariable("task_id") final String task_id) {

        try {
            List<TaskResult> results = resultRepository.getAllByTask(task_id);

            double avg = 0;
            for (TaskResult result : results) {
                avg += result.getScore();
            }

            return "" + (avg / results.size());

        } catch (RepositoryException e) {
            throw new RuntimeException(e);
        }

    }

}
