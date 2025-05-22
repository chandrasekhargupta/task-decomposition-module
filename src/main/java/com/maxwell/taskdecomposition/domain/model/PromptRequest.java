package com.maxwell.taskdecomposition.domain.model;


/**
 * Represents a natural language prompt sent by the client.
 * Used as a request body in the API to generate subtasks.
 */
public class PromptRequest {

    private String prompt;

    // Default constructor (needed for JSON deserialization)
    public PromptRequest() {}

    // Constructor with parameters
    public PromptRequest(String prompt) {
        this.prompt = prompt;
    }

    // Getter
    public String getPrompt() {
        return prompt;
    }

    // Setter
    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    @Override
    public String toString() {
        return "PromptRequest{" +
                "prompt='" + prompt + '\'' +
                '}';
    }
}
