package com.minhbui.ai_code_assistant.service;

import org.springframework.stereotype.Service;

import java.util.List;

import com.minhbui.ai_code_assistant.repository.ChunkRepository;
import com.minhbui.ai_code_assistant.repository.ChunkVectorRepository;
import com.minhbui.ai_code_assistant.entity.ChunkEntity;

@Service
public class EmbeddingJobService {

    private final ChunkRepository chunkRepository;
    private final EmbeddingService embeddingService;
    private final ChunkVectorRepository chunkVectorRepository;

    public EmbeddingJobService(
            ChunkRepository chunkRepository,
            EmbeddingService embeddingService,
            ChunkVectorRepository chunkVectorRepository) {

        this.chunkRepository = chunkRepository;
        this.embeddingService = embeddingService;
        this.chunkVectorRepository = chunkVectorRepository;
    }

    public void generateEmbeddings(Long repositoryId) throws Exception {

        List<ChunkEntity> chunks = chunkRepository.findByCodeFileRepositoryId(repositoryId);

        for (ChunkEntity chunk : chunks) {
            float[] embedding = embeddingService.generateEmbedding(chunk.getContent());
            chunkVectorRepository.saveEmbedding(chunk.getId(),embedding);
        }
    }
}