package com.maxwell.taskdecomposition.application;

import com.maxwell.taskdecomposition.application.service.DecompositionService;
import com.maxwell.taskdecomposition.infrastructure.llm.OpenAiClient;
import com.maxwell.taskdecomposition.domain.model.SubtaskResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for DecompositionService.
 */
public class DecompositionServiceTest {

    private OpenAiClient mockClient;
    private DecompositionService service;

    @BeforeEach
    void setUp() {
        mockClient = mock(OpenAiClient.class);
        service = new DecompositionService(mockClient);
    }

    @Test
    void getSubtasks_shouldReturnValidResponse() {
        // Given
        String prompt = "Design a robotic arm for assembly line";
        List<String> mockSubtasks = List.of(
            "Define functional requirements",
            "Select actuators and sensors",
            "Model joint movements in CAD",
            "Simulate control system",
            "Assemble and test"
        );

        when(mockClient.fetchSubtasks(prompt)).thenReturn(mockSubtasks);

        // When
        SubtaskResponse response = service.getSubtasks(prompt);

        // Then
        assertNotNull(response);
        assertEquals(5, response.getSubtasks().size());
        assertTrue(response.getSubtasks().contains("Select actuators and sensors"));

        // Verify interaction
        verify(mockClient, times(1)).fetchSubtasks(prompt);
    }
}
