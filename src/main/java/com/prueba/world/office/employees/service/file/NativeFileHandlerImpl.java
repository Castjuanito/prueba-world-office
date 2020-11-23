package com.prueba.world.office.employees.service.file;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NativeFileHandlerImpl implements NativeFileHandler {
    @Override
    public void createFolder(String path) {
        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    @Override
    public List<String> filesInFolder(String path) {
        List<String> results = new ArrayList<>();
        File[] files = new File(path).listFiles();
        for (File file : files) {
            if (file.isFile()) {
                results.add(file.getName());
            }
        }
        return results;
    }

    @Override
    public long getFileSize(Path path) {
        File file = new File(path.toAbsolutePath().toString());
        return (!file.exists()) ? 0 : file.length();
    }
}
