package com.minhbui.ai_code_assistant.dto.response;

public class FileResponse {

    private Long id;
    private String path;
    private String extension;
    private Long size;

    public FileResponse() {}

    public FileResponse(Long id, String path, String extension, Long size) {
        this.id = id;
        this.path = path;
        this.extension = extension;
        this.size = size;
    }

    public Long getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    public String getExtension() {
        return extension;
    }

    public Long getSize() {
        return size;
    }
}
