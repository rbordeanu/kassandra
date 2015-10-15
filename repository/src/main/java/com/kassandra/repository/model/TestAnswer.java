package com.kassandra.repository.model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TestAnswer {

    private final Map<String, Integer> answers;

    public TestAnswer(@JsonProperty("answers") Map<String, Integer> answers) {
        this.answers = answers;
    }

    public Map<String, Integer> getAnswers() {
        return answers;
    }
}
