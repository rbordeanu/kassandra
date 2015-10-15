package com.kassandra.repository.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CodingAnswer {

    private final String taskId;
    private final String userId;
    private final String content;

    @JsonCreator
    public CodingAnswer(@JsonProperty("taskId") String taskId,
            @JsonProperty("userId") String userId, @JsonProperty("content") String content) {
        this.taskId = taskId;
        this.userId = userId;
        this.content = content;
    }

    public String getTaskId() {
        return taskId;
    }

    public String getUserId() {
        return userId;
    }

    public String getContent() {
        return content;
    }
}
