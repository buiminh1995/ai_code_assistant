package com.minhbui.ai_code_assistant.model;

public class Chunk {
    private final String content;
    private final int startLine;
    private final int endLine;

    public Chunk (String content, int startLine, int endLine){
        this.content = content;
        this.startLine = startLine;
        this.endLine = endLine;
    }

    public String getContent() {
        return content;
    }

    public int getStartLine() {
        return startLine;
    }

    public int getEndLine() {
        return endLine;
    }   
}
