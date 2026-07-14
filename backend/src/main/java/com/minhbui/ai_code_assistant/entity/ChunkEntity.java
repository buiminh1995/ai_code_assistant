package com.minhbui.ai_code_assistant.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "chunks")
@Data
public class ChunkEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private Integer startLine;

    private Integer endLine;

    @ManyToOne
    @JoinColumn(name = "code_file_id")
    private CodeFileEntity codeFile;

}