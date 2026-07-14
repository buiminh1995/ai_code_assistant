package com.minhbui.ai_code_assistant.factory;

import java.io.IOException;
import java.nio.file.Files;
import java.io.File;

import com.minhbui.ai_code_assistant.entity.CodeFileEntity;
import com.minhbui.ai_code_assistant.entity.RepositoryEntity;

public class CodeFileEntityFactory {

    private CodeFileEntityFactory() {}

    public static CodeFileEntity initializeCodeFileEntity(File file, RepositoryEntity repo) {
        CodeFileEntity entity = new CodeFileEntity();
        entity.setPath(file.getAbsolutePath());
        entity.setRepository(repo);
        entity.setSize(file.length());

        String name = file.getName();
        int dot = name.lastIndexOf('.');

        if (dot != -1) {
            entity.setExtension(name.substring(dot + 1));
        }

        try {
            entity.setContent(Files.readString(file.toPath()));
        } catch (IOException e) {
            entity.setContent("");
        }

        return entity;
    }
}
