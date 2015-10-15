package com.kassandra.repository.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Test {

    private final List<Question> questions;

    public Test(@JsonProperty("questions") List<Question> questions) {
        this.questions = questions;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Test))
            return false;

        Test test = (Test) o;

        if (!questions.equals(test.questions))
            return false;

        return true;
    }

    @Override public int hashCode() {
        return questions.hashCode();
    }
}
