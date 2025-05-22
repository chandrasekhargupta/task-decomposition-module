package com.maxwell.taskdecomposition.api;

import com.maxwell.taskdecomposition.application.service.DecompositionService;
import com.maxwell.taskdecomposition.domain.model.PromptRequest;
import com.maxwell.taskdecomposition.domain.model.SubtaskResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller to handle prompt decomposition requests.
 */
@RestController
@RequestMapping("/api/decompose")
public class DecompositionController {

    private final DecompositionService service;

    public DecompositionController(DecompositionService service) {
        this.service = service;
    }

    /**
     * Accepts a prompt and returns structured subtasks from LLM.
     *
     * @param request prompt from user
     * @return structured subtasks as response
     */
    @PostMapping
    public ResponseEntity<SubtaskResponse> decomposePrompt(@RequestBody PromptRequest request) {
        SubtaskResponse response = service.getSubtasks(request.getPrompt());
        return ResponseEntity.ok(response);
    }
}
