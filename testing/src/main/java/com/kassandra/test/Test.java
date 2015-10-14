package com.kassandra.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.kassandra.repository.IQuestion;
import com.kassandra.repository.IQuestionRepository;
import com.kassandra.repository.RepositoryException;
import com.kassandra.repository.model.Level;

public class Test {

    private final List<IQuestion> questions;
    private Level type;
    private final IQuestionRepository questionRepo;
    private int questionIndex;
    private Date startTime;
    private final String userId;


    public Test(IQuestionRepository repository, String userId) {
        this.questionRepo = repository;
        this.userId = userId;
        this.questions = new ArrayList();
        questionIndex = 0;
        startTime = new Date();
    }

    public void init(Level difficulty) throws RepositoryException {

        type = difficulty;

        for(int i=0; i<10; i++){
            List<String> ids = questionRepo.getAll(type.toString());
            Random randomGen = new Random();
            String id = ids.get(randomGen.nextInt(ids.size()));
            questions.add(questionRepo.getQuestion(id)) ;
        }

    }

    public IQuestion nextQuestion() {

        return questionIndex < 10 ? questions.get(questionIndex++) : questions.get(questionIndex);
    }

    public IQuestion previousQuestion() {
        return questionIndex > 0 ? questions.get(questionIndex--) : questions.get(questionIndex);
    }

    public IQuestion goToQuestion(int index){
        questionIndex = index;
        return questions.get(questionIndex);
    }

    public double summarise(String user) {

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

    public String getUserId() {
        return userId;
    }
}
