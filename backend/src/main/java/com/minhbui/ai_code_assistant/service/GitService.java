package com.minhbui.ai_code_assistant.service;

import org.eclipse.jgit.api.Git;
import org.springframework.stereotype.Service;

import com.minhbui.ai_code_assistant.util.GitUtils;

import java.io.File;

@Service
public class GitService {

    public String cloneRepository(String url) throws Exception {

        String repoName = GitUtils.extractRepositoryName(url);
        File directory = new File("repos/" + repoName);

        Git.cloneRepository()
                .setURI(url)
                .setDirectory(directory)
                .call();

        return directory.getAbsolutePath();
    }
}
