package com.minhbui.ai_code_assistant.factory;
import java.time.LocalDateTime;

import com.minhbui.ai_code_assistant.entity.RepositoryEntity;
import com.minhbui.ai_code_assistant.util.GitUtils;

public class RepositoryEntityFactory {

    private RepositoryEntityFactory() {}

    public static RepositoryEntity initializeRepoEntity(String localPath, String url) {
        RepositoryEntity repo = new RepositoryEntity();
        repo.setLocalPath(localPath);
        String repoName = GitUtils.extractRepositoryName(url);
        repo.setName(repoName);
        repo.setUrl(url);
        repo.setCreatedAt(LocalDateTime.now());
        return repo;
    }
    
}
