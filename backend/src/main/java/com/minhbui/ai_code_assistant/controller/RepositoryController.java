package com.minhbui.ai_code_assistant.controller;

import com.minhbui.ai_code_assistant.dto.request.CreateRepositoryRequest;
import com.minhbui.ai_code_assistant.dto.response.FileResponse;
import com.minhbui.ai_code_assistant.dto.SimilarChunk;

import com.minhbui.ai_code_assistant.client.ollama.OllamaClient;

import com.minhbui.ai_code_assistant.entity.CodeFileEntity;
import com.minhbui.ai_code_assistant.entity.RepositoryEntity;
import com.minhbui.ai_code_assistant.repository.ChunkVectorRepository;
import com.minhbui.ai_code_assistant.service.EmbeddingJobService;
import com.minhbui.ai_code_assistant.service.EmbeddingService;
import com.minhbui.ai_code_assistant.service.RepositoryService;
import com.minhbui.ai_code_assistant.service.ChatService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/repositories")
public class RepositoryController {

    private final RepositoryService repositoryService;
    private final OllamaClient ollamaClient;
    private final EmbeddingJobService embeddingJobService;
    private final EmbeddingService embeddingService;
    private final ChunkVectorRepository chunkVectorRepository;
    private final ChatService chatService;

    public RepositoryController(RepositoryService repositoryService, 
        OllamaClient ollamaClient, 
        EmbeddingJobService embeddingJobService, 
        EmbeddingService embeddingService, 
        ChunkVectorRepository chunkVectorRepository,
        ChatService chatService) {

        this.repositoryService = repositoryService;
        this.ollamaClient = ollamaClient;
        this.embeddingJobService = embeddingJobService;
        this.embeddingService = embeddingService;
        this.chunkVectorRepository = chunkVectorRepository;
        this.chatService = chatService;
    }

    @PostMapping
    public RepositoryEntity create (
            @Valid @RequestBody CreateRepositoryRequest request) throws Exception {

        return repositoryService.create(request.getUrl());

    }

    @GetMapping("/{id}/files")
    public List<FileResponse> getFiles(
            @PathVariable Long id) {

        return repositoryService.getFiles(id);

    }

    @PostMapping("/{id}/embeddings")
    public String generateEmbeddings(@PathVariable Long id) throws Exception {

        embeddingJobService.generateEmbeddings(id);

        return "Done";
    }

    // @GetMapping("/search")
    // public List<SimilarChunk> search(@RequestParam String question) throws Exception {

    //     float[] embedding =
    //             embeddingService.generateEmbedding(question);

    //     return chunkVectorRepository.findSimilarChunks(
    //             embedding,
    //             5
    //     );
    // }

    @PostMapping("/{id}/ask")
    public String ask(@PathVariable Long id, @RequestParam String question) throws Exception {
        return chatService.ask(id, question);
    }

}
