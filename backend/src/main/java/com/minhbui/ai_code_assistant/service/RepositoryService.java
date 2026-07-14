package com.minhbui.ai_code_assistant.service;

import com.minhbui.ai_code_assistant.repository.ChunkRepository;
import com.minhbui.ai_code_assistant.repository.CodeFileRepository;
import com.minhbui.ai_code_assistant.repository.RepositoryRepository;

import org.springframework.stereotype.Service;

import com.minhbui.ai_code_assistant.dto.response.FileResponse;
import com.minhbui.ai_code_assistant.entity.ChunkEntity;
import com.minhbui.ai_code_assistant.entity.CodeFileEntity;
import com.minhbui.ai_code_assistant.entity.RepositoryEntity;

import com.minhbui.ai_code_assistant.factory.CodeFileEntityFactory;
import com.minhbui.ai_code_assistant.factory.RepositoryEntityFactory;
import com.minhbui.ai_code_assistant.model.Chunk;

import java.io.File;
import java.util.List;


@Service
public class RepositoryService {

    private final RepositoryRepository repositoryRepository;

    private final GitService gitService;

    private final FileScannerService fileScannerService;

    private final CodeFileRepository codeFileRepository;

    private final ChunkRepository chunkRepository;
    
    private final ChunkService chunkService;

    public RepositoryService(RepositoryRepository repositoryRepository, CodeFileRepository codeFileRepository, GitService gitService, FileScannerService fileScannerService, ChunkService chunkService, ChunkRepository chunkRepository) {
        this.repositoryRepository = repositoryRepository;
        this.gitService = gitService;
        this.fileScannerService = fileScannerService;
        this.codeFileRepository = codeFileRepository;
        this.chunkRepository = chunkRepository;
        this.chunkService = chunkService;
    }

    public RepositoryEntity create(String url) throws Exception {

        //clone repo to local machine
        String localPath = gitService.cloneRepository(url);

        // fill in info for repo entity
        RepositoryEntity repo = RepositoryEntityFactory.initializeRepoEntity(localPath, url);

        //save to db
        repositoryRepository.save(repo);

        //files contain all files in the repo
        List<File> files = fileScannerService.scan(new File(localPath));

        //create a CodeFileEntity for each file
        for (File file: files) {
            CodeFileEntity entity = CodeFileEntityFactory.initializeCodeFileEntity(file, repo);

            //save CodeFileEntity to CodeFile database
            CodeFileEntity codeFile = codeFileRepository.save(entity);

            List<Chunk> chunks = chunkService.chunk(codeFile.getContent());

            for (Chunk chunk : chunks) {

                ChunkEntity chunkEntity = new ChunkEntity();

                chunkEntity.setCodeFile(codeFile);

                chunkEntity.setContent(chunk.getContent());

                chunkEntity.setStartLine(chunk.getStartLine());

                chunkEntity.setEndLine(chunk.getEndLine());

                chunkRepository.save(chunkEntity);
            }
        }

        return repo;
    }

    public List<FileResponse> getFiles(Long repositoryId) {

    return codeFileRepository.findByRepositoryId(repositoryId)
            .stream()
            .map(file -> new FileResponse(
                    file.getId(),
                    file.getPath(),
                    file.getExtension(),
                    file.getSize()
            ))
            .toList();
    }
}
