package com.example.javaautocad.AutoCad.ai;

import com.example.javaautocad.AutoCad.dto.AiDto;
import com.example.javaautocad.AutoCad.message.ErrorMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class AiClient {
    private final String AUTHORIZATION = "Authorization";
    private final String BEARER = "Bearer ";
    private final String TYPE = "Content-Type";
    private final String APP = "application/json";
    private final String CHOC = "choices";
    private final String MESSAGE = "message";
    private final String CONTENT = "content";

    public String serialization(AiDto dto) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(ErrorMessage.JSON_ERROR.getMessage());
        }
    }

    public HttpRequest aiRequest(AutoAi ai, AiDto dto) {
        return HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                .POST(HttpRequest.BodyPublishers.ofString(serialization(dto)))
                .header(AUTHORIZATION, BEARER + ai.keyMapping())
                .header(TYPE, APP)
                .build();
    }

    public HttpResponse<String> aiResponse(AutoAi ai, AiDto dto) {
        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            return httpClient.send(aiRequest(ai, dto), HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new IllegalArgumentException(ErrorMessage.RESPONSE_ERROR.getMessage());
        }
    }

    public String aiParser(AutoAi ai, AiDto dto) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            String body = aiResponse(ai, dto).body();
            JsonNode node = objectMapper.readTree(body);
            if (!node.has(CHOC) || node.get(CHOC).isEmpty()) {
                throw new IllegalArgumentException(ErrorMessage.CHOICES_ERROR.getMessage());
            }
            return node.path(CHOC).get(0).path(MESSAGE).path(CONTENT).asText();
        } catch (Exception e) {
            throw new IllegalArgumentException(ErrorMessage.MAPPING_ERROR.getMessage() + " / " + e.getMessage());
        }
    }


//    public String aiParser(AutoAi ai, AiDto dto) {
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            JsonNode node = objectMapper.readTree(aiResponse(ai, dto).body());
//            return node.path("choices").get(0).path("message").path("content").asText();
//        } catch (JsonProcessingException e) {
//            throw new IllegalArgumentException(ErrorMessage.MAPPING_ERROR.getMessage());
//        }
//    }
}
