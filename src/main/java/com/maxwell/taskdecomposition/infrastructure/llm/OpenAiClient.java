package com.maxwell.taskdecomposition.infrastructure.llm;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * OpenAI client that handles communication with GPT-3.5 Turbo API
 * to convert engineering prompts into subtasks.
 */
@Component
public class OpenAiClient {

  private static final Logger log = LoggerFactory.getLogger(OpenAiClient.class);

  private final WebClient webClient;
  private final String apiKey;
  private final ObjectMapper objectMapper;

  private static final String SYSTEM_MESSAGE = "You are an assistant that converts engineering prompts into subtasks.";
  private static final double TEMPERATURE = 0.2;
  private static final String MODEL = "gpt-3.5-turbo";

  public OpenAiClient(WebClient.Builder webClientBuilder,
                     @Value("${openai.api.key}") String apiKey,
                     ObjectMapper objectMapper) {
    this.webClient = webClientBuilder
        .baseUrl("https://api.openai.com/v1/chat/completions")
        .build();
    this.apiKey = apiKey;
    this.objectMapper = objectMapper;
  }

  /**
   * Fetches subtasks by sending prompt to OpenAI API.
   *
   * @param prompt natural language engineering prompt
   * @return list of subtasks or empty list on failure
   */
  public List<String> fetchSubtasks(String prompt) {
    String fullPrompt = "Break this engineering task into step-by-step subtasks:\n" + prompt;

    String requestBody = String.format("""
        {
          "model": "%s",
          "messages": [
            {"role": "system", "content": "%s"},
            {"role": "user", "content": "%s"}
          ],
          "temperature": %.1f
        }
        """, MODEL, SYSTEM_MESSAGE, escapeJson(fullPrompt), TEMPERATURE);

    try {
      log.debug("Sending request to OpenAI API with prompt: {}", prompt);

      String responseJson = webClient.post()
          .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
          .contentType(MediaType.APPLICATION_JSON)
          .bodyValue(requestBody)
          .retrieve()
          .bodyToMono(String.class)
          .block();

      System.out.println("OpenAI raw response: " + responseJson);

      if (responseJson == null) {
        log.error("Received null response from OpenAI API");
        return Collections.emptyList();
      }

      return parseSubtasksFromResponse(responseJson);

    } catch (WebClientResponseException e) {
      log.error("OpenAI API returned error: {} {}", e.getStatusCode(), e.getResponseBodyAsString(), e);
    } catch (Exception e) {
      log.error("Unexpected error while calling OpenAI API", e);
    }

    return Collections.emptyList();
  }

  private List<String> parseSubtasksFromResponse(String responseJson) {
    try {
      JsonNode root = objectMapper.readTree(responseJson);
      JsonNode choices = root.path("choices");

      if (!choices.isArray() || choices.size() == 0) {
        log.warn("OpenAI response 'choices' missing or empty");
        return Collections.emptyList();
      }

      String content = choices.get(0).path("message").path("content").asText(null);

      if (content == null || content.isEmpty()) {
        log.warn("OpenAI response 'content' is empty");
        return Collections.emptyList();
      }

      return StreamSupport.stream(content.lines().spliterator(), false)
          .map(String::trim)
          .filter(line -> !line.isEmpty())
          .map(line -> line.replaceAll("^[0-9]+\\.\\s*", "")) // Remove numbering like "1. "
          .collect(Collectors.toList());

    } catch (Exception e) {
      log.error("Failed to parse OpenAI response JSON", e);
      return Collections.emptyList();
    }
  }

  private String escapeJson(String text) {
    return text.replace("\\", "\\\\")
               .replace("\"", "\\\"")
               .replace("\n", "\\n");
  }
}
