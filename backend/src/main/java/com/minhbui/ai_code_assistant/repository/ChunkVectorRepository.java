package com.minhbui.ai_code_assistant.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.pgvector.PGvector;
import com.minhbui.ai_code_assistant.dto.SimilarChunk;

import java.util.List;

@Repository
public class ChunkVectorRepository {

    private final JdbcTemplate jdbcTemplate;

    public ChunkVectorRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveEmbedding(Long chunkId, float[] embedding) {

        jdbcTemplate.update(
                "UPDATE chunks SET embedding = ? WHERE id = ?",
                new PGvector(embedding),
                chunkId
        );

    }

    public List<SimilarChunk> findSimilarChunks(Long repositoryId, float[] embedding, int limit) {
        return jdbcTemplate.query(
            """
            SELECT c.id, c.content
            FROM chunks c
            JOIN code_files f
                ON c.code_file_id = f.id
            WHERE f.repository_id = ?
            ORDER BY c.embedding <=> ?
            LIMIT ?;
            """,
            (rs, rowNum) -> new SimilarChunk(
                    rs.getLong("id"),
                    rs.getString("content")
            ),
            repositoryId,
            new PGvector(embedding),
            limit
        );

    }

}