package com.minhbui.ai_code_assistant.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.minhbui.ai_code_assistant.entity.RepositoryEntity;

public interface RepositoryRepository
        extends JpaRepository<RepositoryEntity, Long> {

}
