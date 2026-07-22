package com.minhbui.ai_code_assistant.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileScannerService {

    private static final List<String> IGNORED =
            List.of(".git", "node_modules", "target", "build", ".idea",".eot",".svg",".ttf",".woff",".jar", ".png");

    public List<File> scan(File root) {

        List<File> files = new ArrayList<>();

        walk(root, files);

        return files;
    }

    private void walk(File file, List<File> files) {

        if (IGNORED.contains(file.getName())) {
            return;
        }

        if (file.isDirectory()) {

            File[] children = file.listFiles();

            if (children == null)
                return;

            for (File child : children) {
                walk(child, files);
            }

        } else {

            String fileName = file.getName().toLowerCase();

            for (String ignored : IGNORED) {
                if (fileName.endsWith(ignored)) {
                    return;
                }
            }

            files.add(file);

        }

    }

}
