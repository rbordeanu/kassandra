package com.kassandra.repository.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kassandra.repository.IQuestion;

/**
 * Created by madatoia on 10/13/2015.
 */
public class Question implements IQuestion {

    private final String id;
    private final byte[] content;
    private final int score;
    private final int answer;

    @JsonCreator
    public Question(@JsonProperty("_id") String id, @JsonProperty("content") byte[] content,
        @JsonProperty("score") int score, @JsonProperty("answer") int answer) {
        this.id = id;
        this.content = content;
        this.score = score;
        this.answer = answer;
    }

    @Override
    public byte[] getContent() {
        return new byte[0];
    }

    public String getId() {
        return id;
    }

    public int getScore() {
        return score;
    }

    public int getAnswer() {
        return answer;
    }
}
