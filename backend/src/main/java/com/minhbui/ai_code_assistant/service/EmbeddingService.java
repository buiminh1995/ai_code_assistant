package com.minhbui.ai_code_assistant.service;

import org.springframework.stereotype.Service;

import com.minhbui.ai_code_assistant.entity.ChunkEntity;
import com.minhbui.ai_code_assistant.client.ollama.OllamaClient;


@Service
public class EmbeddingService {

    private final OllamaClient ollamaClient;

    public EmbeddingService(OllamaClient ollamaClient) {
        this.ollamaClient = ollamaClient;
    }

    public float[] generateEmbedding(String text) throws Exception  {

        return ollamaClient.embed(text);

    }
}