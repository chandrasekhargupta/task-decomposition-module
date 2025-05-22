package com.maxwell.taskdecomposition.application.service;

import com.maxwell.taskdecomposition.domain.model.SubtaskResponse;
import com.maxwell.taskdecomposition.infrastructure.llm.OpenAiClient;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class that handles business logic for decomposing prompts using LLM.
 */
@Service
public class DecompositionService {

    private final OpenAiClient llmClient;

    public DecompositionService(OpenAiClient llmClient) {
        this.llmClient = llmClient;
    }

    /**
     * Converts a natural language prompt into structured subtasks.
     *
     * @param prompt Natural language engineering prompt
     * @return SubtaskResponse with list of subtasks
     */
    public SubtaskResponse getSubtasks(String prompt) {
        List<String> subtasks = llmClient.fetchSubtasks(prompt);
        return new SubtaskResponse(subtasks);
    }
}
