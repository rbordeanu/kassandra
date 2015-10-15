package com.kassandra.repository.model;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

public class Task implements ITask {
    private final String _id;
    private final String name;
    private final Level difficulty;
    private final String description;
    private final String submitterId;
    private final boolean quiz;
    private final JsonNode body; // either quiz or coding
    private final long time;
    private final List<String> tags;

    @JsonCreator
    public Task(@JsonProperty("_id") String _id, @JsonProperty("name") String name, @JsonProperty("difficulty") Level difficulty,
            @JsonProperty("description") String description, @JsonProperty("submitterId") String submitterId, @JsonProperty("quiz") boolean quiz,
            @JsonProperty("body") JsonNode body, @JsonProperty("time") long time,  @JsonProperty("tags") List<String> tags) {
        this._id = _id;
        this.name = name;
        this.difficulty = difficulty;
        this.description = description;
        this.submitterId = submitterId;
        this.quiz = quiz;
        this.body = body;
        this.time = time;
        this.tags = tags;
    }

    @Override public String get_id() {
        return _id;
    }

    @Override public Level getDifficulty() {
        return difficulty;
    }

    @Override public String getDescription() {
        return description;
    }

    @Override public String getSubmitterId() {
        return submitterId;
    }

    @Override public boolean isQuiz() {
        return quiz;
    }

    @Override public JsonNode getBody() {
        return body;
    }

    @Override public List<String> getTags() {
        return tags;
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

    public String getName() {
        return name;
    }

    public long getTime() {
        return time;
    }
}
