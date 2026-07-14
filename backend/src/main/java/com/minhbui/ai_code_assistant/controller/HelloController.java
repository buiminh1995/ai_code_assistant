package com.minhbui.ai_code_assistant.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.minhbui.ai_code_assistant.service.ChunkService;

@RestController
public class HelloController {

    @GetMapping("/")
    public String hello() {
        return "AI Code Assistant Backend is running!";
    }

    @GetMapping("/test")
    public String testChunk(ChunkService chunkService) {

        String text = "";

        for (int i = 1; i <= 120; i++) {
            text += "Line " + i + "\n";
        }

        return "Chunks: " + chunkService.chunk(text).size();
    }

}
