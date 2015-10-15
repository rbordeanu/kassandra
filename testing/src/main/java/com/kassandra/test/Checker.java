package com.kassandra.test;

import static org.slf4j.LoggerFactory.getLogger;

import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kassandra.compiler.java.JCompiler;
import com.kassandra.compiler.java.utils.ResolveClassName;
import com.kassandra.repository.IQuestion;
import com.kassandra.repository.ITask;
import com.kassandra.repository.model.CodeTask;
import com.kassandra.repository.model.Task;
import com.kassandra.repository.model.Test;
import com.kassandra.repository.model.TestAnswer;

public class Checker {

    private static final Logger LOG = getLogger(Checker.class);

/*    public static double check(ITask task, String answer) {

        return task.isQuiz() ? getQuizScore(task.getBody(), answer) : getCodingScore(
                task.getBody(), answer);
    }*/

    public static double getCodingScore(Task task, String answer) {

        ObjectMapper mapper = new ObjectMapper();

        try {
            CodeTask codeTask = mapper.readValue(task.getBody().toString(), CodeTask.class);

            String className = ResolveClassName.getClassName(codeTask.getStatement());
            String fullClassName = ResolveClassName.getFullClassName(codeTask.getStatement());
            String thePath = ClassLoader.getSystemResource(".").toString()
                    .substring(6, ClassLoader.getSystemResource(".").toString().length() - 1)
                    .replace("/", "//");
            JCompiler compiler = new JCompiler("-d", thePath);
            boolean success = compiler.compile(className, answer);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static double getQuizScore(Task task, Map<String, Integer> answer) {
        double counter = 0;
        ObjectMapper mapper = new ObjectMapper();

        try {
            Test test = mapper.readValue(task.getBody().toString(), Test.class);
            for (IQuestion question : test.getQuestions()) {
                if (answer.containsKey(question.getId())) {
                    counter += (answer.get(question.getId()) == question.getCorrectAnswer() ? 1
                            : 0);
                }
            }

            return counter * 100.0 / test.getQuestions().size();
        } catch (IOException e) {
            LOG.error("Couldn't deserialize from json", e);
            return 0;
        }
    }
}
