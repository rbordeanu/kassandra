package com.kassandra.repository.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kassandra.repository.IQuestion;

public class Question implements IQuestion {

    private final String content;
    private final List<String> allAnswers;
    private final int correctAnswer;

    @JsonCreator
    public Question(@JsonProperty("content") String content,
        @JsonProperty("correctAnswer") int correctAnswer,
        @JsonProperty("allAnswers") List<String> allAnswers) {
        this.content = content;
        this.allAnswers = allAnswers;
        this.correctAnswer = correctAnswer;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public int getCorrectAnswer() {
        return correctAnswer;
    }

    @Override
    public List<String> getAllAnswers() {
        return allAnswers;
    }
}
