package com.minhbui.ai_code_assistant.util;

public class GitUtils {

    private GitUtils() {
        // Prevent instantiation
    }

    public static String extractRepositoryName(String url) {
        String repoName = url.substring(url.lastIndexOf("/") + 1);

        if (repoName.endsWith(".git")) {
            repoName = repoName.substring(0, repoName.length() - 4);
        }

        return repoName;
    }
}
