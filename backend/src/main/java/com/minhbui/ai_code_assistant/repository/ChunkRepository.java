package com.minhbui.ai_code_assistant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.minhbui.ai_code_assistant.entity.ChunkEntity;

import java.util.List;

public interface ChunkRepository
        extends JpaRepository<ChunkEntity, Long> {
        
        List<ChunkEntity> findByCodeFileRepositoryId(Long repositoryId);

}

