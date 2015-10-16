package com.kassandra.test;

import static org.slf4j.LoggerFactory.getLogger;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.script.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kassandra.compiler.java.JCompiler;
import com.kassandra.compiler.java.utils.ResolveClassName;
import com.kassandra.repository.IQuestion;
import com.kassandra.repository.model.CodeTask;
import com.kassandra.repository.model.Task;
import com.kassandra.repository.model.Test;
import org.slf4j.Logger;

public class Checker {

    private static final Logger LOG = getLogger(Checker.class);

    public static double getCodingScore(Task task, String answer) {

        ObjectMapper mapper = new ObjectMapper();

        try {
            CodeTask codeTask = mapper.readValue(task.getBody().toString(), CodeTask.class);

            if("Java".equals(codeTask.getLang())){
                return testJava(answer, codeTask);
            } else if("JS".equals(codeTask.getLang())){
                return testJS(answer, codeTask);
            }

            return 0;
        } catch (IOException e) {
            return 0;
        }
    }

    private static double testJS(String answer, CodeTask codeTask){

        ScriptEngine se = new ScriptEngineManager()
                .getEngineByName("javascript");

        if ( Compilable.class.isAssignableFrom(se.getClass()) ) {

            Compilable c = (Compilable) se;
            CompiledScript cs = null;
            try {
                cs = c.compile(answer);

                cs.eval();
            } catch (ScriptException e) {
                return 0;
            }
        } else {
             try {
                se.eval(answer);
            } catch (ScriptException e) {
                return 0;
            }

        }

        if ( Invocable.class.isAssignableFrom(se.getClass()) ) {

            double score = 0;
            Invocable i = (Invocable) se;

            try {

                for(int ind=0; ind<codeTask.getInput().size(); ind++){
                    Double res = (Double)i.invokeFunction("myFunction", Integer.parseInt(codeTask.getInput().get(ind)));

                        score += (res.equals(codeTask.getOutput().get(ind)) ? 1 : 0);

                }
                return score*100/codeTask.getInput().size();
            } catch (ScriptException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

        }
        return 0;
    }

    private static double testInput(String input, String fullClassName, String s) {
        try {
            Double res = (Double) Class.forName(fullClassName).getDeclaredMethod("getResult")
                    .invoke(Integer.parseInt(input));

            Double expected = Double.parseDouble(s);

            return res == expected ? 1:0;
        } catch (RuntimeException e) {
           return 0;
        } catch (InvocationTargetException e) {
            return 0;
        } catch (NoSuchMethodException e) {
            return 0;
        } catch (ClassNotFoundException e) {
            return 0;
        } catch (IllegalAccessException e) {
            return 0;
        }
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

    private static double testJava(String answer, CodeTask codeTask){
        String className = ResolveClassName.getClassName(answer);
        String fullClassName = ResolveClassName.getFullClassName(answer);
        String thePath = ClassLoader.getSystemResource(".").toString()
                .substring(6, ClassLoader.getSystemResource(".").toString().length() - 1)
                .replace("/", "//");
        JCompiler compiler = new JCompiler("-d", thePath);
        boolean success = compiler.compile(className, answer);


        double score = 0;

        for(int i = 0; i<codeTask.getInput().size(); i++){
            String input = codeTask.getInput().get(i);
            score += testInput(input, fullClassName, codeTask.getOutput().get(i));
        }
        return success ? score *100 / codeTask.getInput().size() : 0;
    }
}
