package com.minhbui.ai_code_assistant.entity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "code_files")
@Data
public class CodeFileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String path;

    private String extension;

    private Long size;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "repository_id")
    private RepositoryEntity repository;
}