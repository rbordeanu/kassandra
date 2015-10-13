package com.kassandra.test;

import com.kassandra.repository.IQuestion;
import com.kassandra.repository.model.User;

/**
 * Created by madatoia on 10/12/2015.
 */
public class Session implements Runnable{

    private final User testTaker;
    private final Test test;

    public Session(User testTaker, TestType type) {

        this.testTaker = testTaker;
        test = new Test();
    }

    @Override public void run() {

        test.init();

        while (test.notDone()){
            IQuestion question = test.nextQuestion();
        }

        double score = test.summarise();

    }
}
