package com.kassandra.repository.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kassandra.repository.IQuestion;

public class Test {

    private final List<IQuestion> questions;

    public Test(@JsonProperty("questions") List<IQuestion> questions) {
        this.questions = questions;
    }

    public List<IQuestion> getQuestions() {
        return questions;
    }
}
