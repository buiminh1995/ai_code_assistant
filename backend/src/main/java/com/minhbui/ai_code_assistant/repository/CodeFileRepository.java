
package com.minhbui.ai_code_assistant.repository;

import com.minhbui.ai_code_assistant.entity.CodeFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CodeFileRepository
        extends JpaRepository<CodeFileEntity, Long> {
            List<CodeFileEntity> findByRepositoryId(Long repositoryId);
}
