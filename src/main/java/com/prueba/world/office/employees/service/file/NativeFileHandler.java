package com.prueba.world.office.employees.service.file;

import java.nio.file.Path;
import java.util.List;


public interface NativeFileHandler {

    void createFolder(String path);

    List<String> filesInFolder(String path);

    long getFileSize(Path path);

}
