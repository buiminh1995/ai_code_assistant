package com.minhbui.ai_code_assistant.service;

import org.springframework.stereotype.Service;

import com.minhbui.ai_code_assistant.model.Chunk;

import java.util.ArrayList;
import java.util.List;

// import ch.usi.si.seart.treesitter.*;

@Service
public class ChunkService {

    // static {
    //     LibraryLoader.load();
    // }

    private static final int CHUNK_SIZE = 100;

    public List<Chunk> chunk(String content) {

        // 2. Initialize the parser with the Java language definition
        // try (Parser parser = Parser.getFor(Language.JAVA)) {
            
        //     // 3. Parse the source string into a syntax tree
        //     try (Tree tree = parser.parse(content)) {
                
        //         Node rootNode = tree.getRootNode();                
        //         for (int i = 0; i < rootNode.getChildCount(); i++) {
        //             System.out.println(rootNode.getChild(i));
        //         }
        //     }
        // }

        List<Chunk> chunks = new ArrayList<>();

        String[] lines = content.split("\n");

        StringBuilder builder = new StringBuilder();

        int count = 0; 

        int i = 0;

        int startLine = 1;
        
        while (i < lines.length) {

            builder.append(lines[i]).append("\n");
            count++;
            i++;

            if (count == CHUNK_SIZE) {

                int endLine = startLine + count - 1;

                chunks.add(new Chunk (builder.toString(), startLine, endLine));

                builder = new StringBuilder();

                i = i - 25;

                startLine = endLine - 25;

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