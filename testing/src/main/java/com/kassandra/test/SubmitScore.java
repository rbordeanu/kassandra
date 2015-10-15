package com.kassandra.test;


public class SubmitScore {

    private final String score;
    private final String percentage;

    public SubmitScore(String score, String percentage) {
        this.score = score;
        this.percentage = percentage;
    }

    public String getScore() {
        return score;
    }

    public String getPercentage() {
        return percentage;
    }
}
