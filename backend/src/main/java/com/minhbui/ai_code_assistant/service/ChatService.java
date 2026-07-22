package com.minhbui.ai_code_assistant.service;

import org.springframework.stereotype.Service;

import com.minhbui.ai_code_assistant.repository.ChunkVectorRepository;
import com.minhbui.ai_code_assistant.client.ollama.OllamaClient;
import com.minhbui.ai_code_assistant.dto.SimilarChunk;

import java.util.List;

@Service
public class ChatService {

    private final EmbeddingService embeddingService;
    private final ChunkVectorRepository chunkVectorRepository;
    private final OllamaClient ollamaClient;

    public ChatService(
            EmbeddingService embeddingService,
            ChunkVectorRepository chunkVectorRepository,
            OllamaClient ollamaClient) {

        this.embeddingService = embeddingService;
        this.chunkVectorRepository = chunkVectorRepository;
        this.ollamaClient = ollamaClient;
    }

    public String ask(Long repositoryId, String question) throws Exception {

        float[] embedding = embeddingService.generateEmbedding(question);

        List<SimilarChunk> chunks = chunkVectorRepository.findSimilarChunks(repositoryId, embedding, 10);

        System.out.println(chunks);

        StringBuilder prompt = new StringBuilder();

        prompt.append("""
                You are an AI assistant answering questions about a Java project.

                Context:

                """);

        for (SimilarChunk chunk : chunks) {
            prompt.append(chunk.content()).append("\n\n");
        }

        prompt.append("Question:\n");
        prompt.append(question);
        prompt.append("\n\nAnswer:");

        return ollamaClient.chat(prompt.toString());
    }

}
