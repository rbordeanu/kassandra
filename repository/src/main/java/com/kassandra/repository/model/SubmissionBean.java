package com.kassandra.repository.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SubmissionBean {

    private final String taskName;
    private final double score;

    @JsonCreator
    public SubmissionBean(@JsonProperty("taskName") String taskName, @JsonProperty("score") double score){

        this.taskName = taskName;
        this.score = score;
    }

    public String getTaskName() {
        return taskName;
    }

    public double getScore() {
        return score;
    }
}
