package com.minhbui.ai_code_assistant.client.ollama;

public class EmbedRequest {

    private String model;
    private String input;

    public EmbedRequest(String model, String input) {
        this.model = model;
        this.input = input;
    }

    public String getModel() {
        return model;
    }

    public String getInput() {
        return input;
    }
}