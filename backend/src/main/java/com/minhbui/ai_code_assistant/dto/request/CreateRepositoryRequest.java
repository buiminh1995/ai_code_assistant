package com.minhbui.ai_code_assistant.dto.request;

import jakarta.validation.constraints.NotBlank;

public class CreateRepositoryRequest {

    @NotBlank
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
