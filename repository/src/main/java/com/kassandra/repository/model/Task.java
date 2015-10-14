package com.kassandra.repository.model;

import com.fasterxml.jackson.databind.JsonNode;

public class Task {
    private final String id;
    private final Level difficulty;
    private final String description;
    private final boolean isQuiz;
    private final JsonNode body; // either quiz or coding

    public Task(String id, Level difficulty, String description, boolean isQuiz, JsonNode body) {
        this.id = id;
        this.difficulty = difficulty;
        this.description = description;
        this.isQuiz = isQuiz;
        this.body = body;
    }
}
