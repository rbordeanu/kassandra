package com.kassandra.rest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Statistics {

    private final String submissions;
    private final String accuracy;

    @JsonCreator
    public Statistics(@JsonProperty("submissions") String submissions, @JsonProperty("accuracy") String accuracy) {

        this.submissions = submissions;
        this.accuracy = accuracy;
    }

    public String getSubmissions() {
        return submissions;
    }

    public String getAccuracy() {
        return accuracy;
    }
}
