package com.minhbui.ai_code_assistant.client.ollama;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;

import java.util.List;
@Component
public class OllamaClient {

    private final HttpClient httpClient = HttpClient.newHttpClient();

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String chat(String prompt) throws Exception {

        ChatRequest requestBody = new ChatRequest(
                "qwen3:4b",
                List.of(new OllamaMessage("user", prompt)),
                false
        );

        String json = objectMapper.writeValueAsString(requestBody);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:11434/api/chat"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = httpClient.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        System.out.println("hello");

        System.out.println(response.body());

        ChatResponse chatResponse =
                objectMapper.readValue(
                        response.body(),
                        ChatResponse.class
                );

        return chatResponse.getMessage().getContent();
    }

    public float[] embed(String text) throws Exception  {

        EmbedRequest requestBody =
        new EmbedRequest(
                "nomic-embed-text",
                text
        );

        //Jackson handles escape quotes
        String json = objectMapper.writeValueAsString(requestBody);

        HttpRequest request =
        HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:11434/api/embed"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response =
        httpClient.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        EmbedResponse embedResponse =
        objectMapper.readValue(
                response.body(),
                EmbedResponse.class
        );

        List<Float> embedding =
        embedResponse.getEmbeddings().get(0);

        float[] result = new float[embedding.size()];

        for (int i = 0; i < embedding.size(); i++) {
            result[i] = embedding.get(i);
        }

        return result;

    }

}