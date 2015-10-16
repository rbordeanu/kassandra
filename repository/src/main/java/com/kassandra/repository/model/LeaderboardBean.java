package com.kassandra.repository.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LeaderboardBean {

    private final String userName;
    private final String gravatarUrl;
    private final double score;

    @JsonCreator
    public LeaderboardBean(@JsonProperty("username") String username,@JsonProperty("gravatarUrl") String gravatarUrl,
            @JsonProperty("score") double score){
        this.userName = username;
        this.gravatarUrl = gravatarUrl;
        this.score = score;
    }

    public String getUserName() {
        return userName;
    }

    public String getGravatarUrl() {
        return gravatarUrl;
    }

    public double getScore() {
        return score;
    }
}
