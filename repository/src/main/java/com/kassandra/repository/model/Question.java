package com.kassandra.repository.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kassandra.repository.IQuestion;

public class Question implements IQuestion {

    private final String id;
    private final String content;
    private final List<String> allAnswers;
    private final int correctAnswer;

    @JsonCreator
    public Question(@JsonProperty("id") String id, @JsonProperty("content") String content,
        @JsonProperty("correctAnswer") int correctAnswer,
        @JsonProperty("allAnswers") List<String> allAnswers) {
        this.id = id;
        this.content = content;
        this.allAnswers = allAnswers;
        this.correctAnswer = correctAnswer;
    }

    @Override
    public String getId() {
        return id;
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

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Question))
            return false;

        Question question = (Question) o;

        if (id != null ? !id.equals(question.id) : question.id != null)
            return false;

        return true;
    }

    @Override public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
