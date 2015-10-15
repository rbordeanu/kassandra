package com.kassandra.test;

import static org.slf4j.LoggerFactory.getLogger;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;

import org.slf4j.Logger;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kassandra.compiler.java.JCompiler;
import com.kassandra.compiler.java.utils.ResolveClassName;
import com.kassandra.repository.IQuestion;
import com.kassandra.repository.ITask;
import com.kassandra.repository.model.CodeTask;
import com.kassandra.repository.model.Test;
import com.kassandra.repository.model.TestAnswer;

public class Checker {

    private static final Logger LOG = getLogger(Checker.class);


    public static double check(ITask task, String answer){

       return task.isQuiz() ? getQuizScore(task.getBody(), answer) : getCodingScore(task.getBody(), answer);
    }

    private static double getCodingScore(JsonNode body, String answer) {

        ObjectMapper mapper = new ObjectMapper();

        try {
            CodeTask codeTask = mapper.readValue(body.toString(), CodeTask.class);

            String className = ResolveClassName.getClassName(answer);
            String fullClassName = ResolveClassName.getFullClassName(answer);
           /* JCompiler compiler = new JCompiler("-d",
                    + "");*/
            /*boolean success = compiler.compile(className, answer);*/





        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0;
    }

    private static double getQuizScore(JsonNode body, String answer) {
        double counter = 0;
        ObjectMapper mapper = new ObjectMapper();

        try {
            Test test = mapper.readValue(body.toString(), Test.class);
            TestAnswer testAnswerBean = mapper.readValue(answer, TestAnswer.class);
            Map<String, Integer> testAnswers = testAnswerBean.getAnswers();
            for(IQuestion question : test.getQuestions()){
                if(testAnswers.containsKey(question.getId())){
                    counter += (testAnswers.get(question.getId()) == question.getCorrectAnswer() ? 1 : 0);
                }
            }

            return counter * 100.0/ test.getQuestions().size();
        } catch (IOException e) {
            LOG.error("Couldn't deserialize from json", e);
            return 0;
        }
    }
}
