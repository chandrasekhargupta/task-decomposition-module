package com.maxwell.taskdecomposition.domain.model;

import java.util.List;
import java.util.Objects;

/**
 * Represents the structured response containing subtasks derived from a prompt.
 */
public class SubtaskResponse {

    private List<String> subtasks;

    // Default constructor for deserialization
    public SubtaskResponse() {}

    // Constructor with parameters
    public SubtaskResponse(List<String> subtasks) {
        this.subtasks = subtasks;
    }

    // Getter
    public List<String> getSubtasks() {
        return subtasks;
    }

    // Setter
    public void setSubtasks(List<String> subtasks) {
        this.subtasks = subtasks;
    }

    @Override
    public String toString() {
        return "SubtaskResponse{" +
                "subtasks=" + subtasks +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubtaskResponse)) return false;
        SubtaskResponse that = (SubtaskResponse) o;
        return Objects.equals(subtasks, that.subtasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subtasks);
    }
}
