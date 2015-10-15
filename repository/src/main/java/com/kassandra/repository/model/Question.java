package com.kassandra.repository.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kassandra.repository.IQuestion;

public class Question implements IQuestion {

    private final String content;
    private final int score;
    private final int correctAnswer;
    private int answer;

    @JsonCreator
    public Question(@JsonProperty("content") String content,
        @JsonProperty("score") int score, @JsonProperty("correctAnswer") int correctAnswer) {
        this.content = content;
        this.score = score;
        this.correctAnswer = correctAnswer;
    }

    @Override
    public String getContent() {
        return content;
    }


    @Override
    public int checkAnswer() {
        return answer == correctAnswer ? score : 0;
    }

    @Override
    public int getAnswer() {
        return answer;
    }

    @Override
    public int getCorrectAnswer() {
        return correctAnswer;
    }

    @Override
    public void setAnswer(int answer) {
        this.answer = answer;
    }
}
