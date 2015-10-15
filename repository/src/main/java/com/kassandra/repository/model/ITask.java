package com.kassandra.repository.model;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;


public interface ITask {
    String get_id();

    Level getDifficulty();

    String getDescription();

    String getSubmitterId();

    boolean isQuiz();

    JsonNode getBody();

    public String getName();

    List<String> getTags();
}
