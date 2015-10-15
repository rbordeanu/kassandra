package com.kassandra.repository.model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TestAnswer {

    private final String taskId;
    private final String userId;

    private final Map<String, Integer> answers;

    @JsonCreator
    public TestAnswer(@JsonProperty("taskId") String taskId, @JsonProperty("userId") String userId,
            @JsonProperty("answers") Map<String, Integer> answers) {
        this.taskId = taskId;
        this.userId = userId;
        this.answers = answers;
    }

    public String getTaskId() {
        return taskId;
    }

    public String getUserId() {
        return userId;
    }

    public Map<String, Integer> getAnswers() {
        return answers;
    }
}
