package com.kassandra.repository.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kassandra.repository.IQuestion;

public class Question implements IQuestion {

    private final String _id;
    private final byte[] content;
    private final int score;
    private final int correctAnswer;
    private int answer;

    @JsonCreator
    public Question(@JsonProperty("_id") String id, @JsonProperty("content") byte[] content,
        @JsonProperty("score") int score, @JsonProperty("correctAnswer") int correctAnswer) {
        this._id = id;
        this.content = content;
        this.score = score;
        this.correctAnswer = correctAnswer;
    }

    @Override
    public byte[] getContent() {
        return new byte[0];
    }

    @Override
    public String getId() {
        return _id;
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
