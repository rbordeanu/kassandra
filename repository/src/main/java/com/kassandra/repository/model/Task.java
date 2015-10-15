package com.kassandra.repository.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

public class Task {
    private final String _id;
    private final Level difficulty;
    private final String description;
    private final String submitterId;
    private final boolean quiz;
    private final JsonNode body; // either quiz or coding

    @JsonCreator
    public Task(@JsonProperty("_id") String _id, @JsonProperty("difficulty") Level difficulty,
        @JsonProperty("description") String description,
        @JsonProperty("submitterId") String submitterId, @JsonProperty("quiz") boolean quiz,
        @JsonProperty("body") JsonNode body) {
        this._id = _id;
        this.difficulty = difficulty;
        this.description = description;
        this.submitterId = submitterId;
        this.quiz = quiz;
        this.body = body;
    }

    public String get_id() {
        return _id;
    }

    public Level getDifficulty() {
        return difficulty;
    }

    public String getDescription() {
        return description;
    }

    public String getSubmitterId() {
        return submitterId;
    }

    public boolean isQuiz() {
        return quiz;
    }

    public JsonNode getBody() {
        return body;
    }

    @Override
    public int hashCode() {
        return Objects.hash(submitterId);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Task)) {
            return false;
        }
        final Task other = (Task) obj;

        return Objects.equals(other._id, _id);
    }
}
