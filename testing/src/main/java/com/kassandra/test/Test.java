package com.kassandra.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.kassandra.repository.IQuestion;
import com.kassandra.repository.IQuestionRepository;
import com.kassandra.repository.RepositoryException;

/**
 * Created by madatoia on 10/12/2015.
 */

public class Test {

    private final List<IQuestion> questions;
    private TestType type;
    private final IQuestionRepository questionRepo;
    private int questionIndex;


    public Test(IQuestionRepository repository) {
        this.questionRepo = repository;
        this.questions = new ArrayList();
        questionIndex = 0;
    }

    void init(TestType difficulty) throws RepositoryException {

        type = difficulty;

        for(int i=0; i<10; i++){
            List<String> ids = questionRepo.getAll(type.toString());
            Random randomGen = new Random();
            String id = ids.get(randomGen.nextInt(ids.size()));
            questions.add(questionRepo.getQuestion(id)) ;
        }

    }

    public boolean notDone() {
        return false;
    }

    public IQuestion nextQuestion() {
        return questions.get(questionIndex++);
    }

    public IQuestion previousQuestion() {
        return questions.get(questionIndex--);
    }

    public double summarise() {

        for(IQuestion question : questions){
           //TODO
        }
        return 0;
    }
}
