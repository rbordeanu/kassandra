package com.kassandra.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.kassandra.repository.IQuestion;
import com.kassandra.repository.ITaskRepository;
import com.kassandra.repository.RepositoryException;
import com.kassandra.repository.model.Level;

public class Test {

    private final List<IQuestion> questions;
    private final ITaskRepository questionRepo;


    public Test(ITaskRepository repository, String userId) {
        this.questionRepo = repository;
        this.questions = new ArrayList();
    }

    public void init(List<String> questionIds, Level difficulty) throws RepositoryException {

       if(questionIds.isEmpty()){
           Random randomGen = new Random();

           for(int i=0; i<10; i++){
            List<String> ids = questionRepo.getAll(difficulty.toString());
            String id = ids.get(randomGen.nextInt(ids.size()));
            questions.add(questionRepo.getQuestion(id)) ;
        }
       }

    }

    public double summarise(String user, List<String> ) {

        long timeTook = new Date().getTime() - startTime.getTime();

        double score = 0;
        for(IQuestion question : questions){
           score += question.checkAnswer();
        }

        if(score > 75 && timeTook < 3600000){
            EmailSender.send(user);
        }
        return score;
    }

    public void answerCurrentQuestion(String answer) {
        questions.get(questionIndex).setAnswer(Integer.parseInt(answer));
    }
}
