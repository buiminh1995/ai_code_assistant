package com.minhbui.ai_code_assistant.client.ollama;

import java.util.List;

public class ChatRequest {

    private String model;
    private List<OllamaMessage> messages;
    private boolean stream;

    // constructor/getters

    public ChatRequest(String model, List<OllamaMessage> messages, boolean stream) {
        this.model = model;
        this.messages = messages;
        this.stream = stream;
    }

    public ChatRequest() {
    }

    public String getModel() {
        return model;
    }

    public List<OllamaMessage> getMessages() {
        return messages;
    }

    public boolean getStream() {
        return stream;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setMesages(List<OllamaMessage> messages) {
        this.messages = messages;
    }

    public void setStream(boolean stream) {
        this.stream = stream;
    }
}