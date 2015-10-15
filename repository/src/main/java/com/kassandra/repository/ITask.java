package com.kassandra.repository;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.kassandra.repository.model.Level;

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
