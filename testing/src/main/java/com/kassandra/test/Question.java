package com.kassandra.test;

import java.util.List;

import com.kassandra.repository.IQuestion;

public class Question implements IQuestion {

    private final String content;
    private final String id;
    private final List<String> allAnswers;
    private final int correctAnswer;

    public Question(String id, String content, List<String> allAnswers, int score, int correctAnswer) {
        this.id = id;
        this.content = content;
        this.allAnswers = allAnswers;
        this.correctAnswer = correctAnswer;
    }


    @Override
    public int getCorrectAnswer() {
        return correctAnswer;
    }

    @Override public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public List<String> getAllAnswers() {
        return allAnswers;
    }
}
