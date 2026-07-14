package com.minhbui.ai_code_assistant.client.ollama;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OllamaMessage {

    private String role;
    private String content;

    public OllamaMessage(String role, String content) {
        this.role = role;
        this.content = content;
    }

    public OllamaMessage() {
    }

    public String getRole() {
        return role;
    }

    public String getContent() {
        return content;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setContent(String content) {
        this.content = content;
    }
}