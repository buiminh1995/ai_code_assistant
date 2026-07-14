package com.minhbui.ai_code_assistant.service;

import org.springframework.stereotype.Service;

import com.minhbui.ai_code_assistant.model.Chunk;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChunkService {

    private static final int CHUNK_SIZE = 50;

    public List<Chunk> chunk(String content) {

        List<Chunk> chunks = new ArrayList<>();

        String[] lines = content.split("\n");

        StringBuilder builder = new StringBuilder();

        int count = 0; 

        int startLine = 1;

        for (String line : lines) {

            builder.append(line).append("\n");
            count++;

            if (count == CHUNK_SIZE) {

                int endLine = startLine + count - 1;

                chunks.add(new Chunk (builder.toString(), startLine, endLine));

                builder = new StringBuilder();

                startLine = endLine + 1;

                count = 0;
            }

        }

        if (!builder.isEmpty()) {
            int endLine = startLine + count - 1;
            chunks.add(new Chunk (builder.toString(), startLine, endLine));
        }

        return chunks;
    }

}