package com.kassandra.test;

import com.kassandra.repository.IQuestion;
import com.kassandra.repository.IQuestionRepository;
import com.kassandra.repository.RepositoryException;

/**
 * Created by madatoia on 10/12/2015.
 */
public class Session implements Runnable{

    private final String userId;
    private final Test test;
    private final TestType type;

    public Session(String userId, TestType type, IQuestionRepository questionRepo) {

        this.userId = userId;
        this.type = type;
        test = new Test(questionRepo);
    }

    @Override public void run() {

        try {
            test.init(type);
        } catch (RepositoryException e) {
            System.out.print("Boom");
        }

        while (test.notDone()){
            IQuestion question = test.nextQuestion();
        }

        double score = test.summarise();

    }
}
