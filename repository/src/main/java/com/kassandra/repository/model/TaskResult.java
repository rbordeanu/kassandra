package com.kassandra.repository.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TaskResult {

    private final String _id;
    private final String taskId; // to query test results
    private final String userId; // to query someone's results

    // go simple - no need to drilldown in the test for start
    private final double score; // score percentage

    @JsonCreator
    public TaskResult(@JsonProperty("_id") String _id, @JsonProperty("taskId") String taskId,
        @JsonProperty("userId") String userId, @JsonProperty("score") double score) {
        this._id = _id;
        this.taskId = taskId;
        this.userId = userId;
        this.score = score;
    }

    public String get_id() {
        return _id;
    }

    public String getTaskId() {
        return taskId;
    }

    public String getUserId() {
        return userId;
    }

    public double getScore() {
        return score;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof TaskResult)) {
            return false;
        }
        final TaskResult other = (TaskResult) obj;

        return Objects.equals(other._id, _id);
    }
}
