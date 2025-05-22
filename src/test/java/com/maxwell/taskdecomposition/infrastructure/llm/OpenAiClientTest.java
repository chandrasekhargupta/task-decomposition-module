package com.maxwell.taskdecomposition.infrastructure.llm;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration test for OpenAiClient.
 */
@SpringBootTest
class OpenAiClientTest {

    @Autowired
    private OpenAiClient openAiClient;

    @Test
    void fetchSubtasks_shouldReturnListOfSubtasks() {
        String prompt = "Design a solar-powered water pumping system for a village.";

        List<String> subtasks = openAiClient.fetchSubtasks(prompt);

        assertNotNull(subtasks);
        assertFalse(subtasks.isEmpty());
        subtasks.forEach(task -> assertFalse(task.trim().isEmpty()));

        // For visibility while debugging
        subtasks.forEach(System.out::println);
    }
}
