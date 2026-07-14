# AI Code Assistant

An AI-powered code assistant built with Spring Boot that indexes GitHub repositories and answers questions about the codebase using Retrieval-Augmented Generation (RAG).

## Features

* Clone public GitHub repositories
* Parse and store source code files
* Split source code into semantic chunks
* Generate embeddings using Ollama (`nomic-embed-text`)
* Store embeddings in PostgreSQL using pgvector
* Perform semantic similarity search over code
* Answer repository questions using a local LLM through Ollama

## Tech Stack

### Backend

* Java 21
* Spring Boot
* Spring Data JPA
* JdbcTemplate

### Database

* PostgreSQL
* pgvector

### AI

* Ollama
* nomic-embed-text (Embeddings)
* Qwen / Llama (Chat Model)

## Architecture

```
GitHub Repository
        │
        ▼
Clone Repository
        │
        ▼
Extract Source Files
        │
        ▼
Chunk Source Code
        │
        ▼
Store Chunks
        │
        ▼
Generate Embeddings
        │
        ▼
Store Vector Embeddings
        │
        ▼
Semantic Search
        │
        ▼
Build Prompt
        │
        ▼
Local LLM (Ollama)
        │
        ▼
Answer
```

## API

### Import Repository

```
POST /repositories
```

Imports a GitHub repository, extracts source files, chunks the code, and stores it in the database.

---

### Generate Embeddings

```
POST /repositories/{id}/embeddings
```

Generates embeddings for all chunks in the repository and stores them in PostgreSQL.

Embedding generation is intentionally separated from repository import to keep imports fast and allow embeddings to be regenerated independently.

---

### Ask Questions

```
POST /ask?question=...
```

Example:

```
POST /ask?question=Where is authentication implemented?
```

The application:

1. Generates an embedding for the user's question.
2. Retrieves the most relevant code chunks using pgvector similarity search.
3. Builds a RAG prompt containing the retrieved context.
4. Sends the prompt to a local LLM via Ollama.
5. Returns the generated answer.

## Future Improvements

* Support incremental repository updates
* Background embedding generation
* Batch embedding requests
* Streaming chat responses
* Better prompt engineering
* Frontend interface
* Docker Compose deployment
* Authentication and multi-user support

## Learning Outcomes

This project demonstrates:

* REST API development with Spring Boot
* Git repository ingestion
* File parsing and code chunking
* PostgreSQL and pgvector integration
* Semantic search
* Retrieval-Augmented Generation (RAG)
* Integration with local LLMs using Ollama
* Software architecture and service separation
