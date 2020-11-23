package com.prueba.world.office.employees.service.data;

import com.prueba.world.office.employees.dto.model.FileUploadDto;
import com.prueba.world.office.employees.exception.AppException;
import com.prueba.world.office.employees.exception.EntityType;
import com.prueba.world.office.employees.exception.ExceptionType;
import com.prueba.world.office.employees.service.file.NativeFileHandler;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FileServiceImpl implements FileService {

    @Autowired
    private NativeFileHandler nativeFileHandler;

    @Value("${file.server.path}")
    private String path;

    @Override
    public FileUploadDto storeFile(byte[] fileContent) {
        char[][] pairs = {{'A', 'Z'}, {'0', '9'}};
        RandomStringGenerator randomStringGenerator = new RandomStringGenerator
                .Builder()
                .withinRange(pairs)
                .build();

        String folderPath = path + "/csv/";
        nativeFileHandler.createFolder(folderPath);
        String fileName = randomStringGenerator.generate(10) + ".csv";
        Path filePath = Paths.get(folderPath + fileName);

        try {
            Files.write(filePath, fileContent);
            long size = nativeFileHandler.getFileSize(filePath);
            log.info("Storing csv file succeed");
            return new FileUploadDto()
                    .setFileName(fileName)
                    .setFileSize(size)
                    .setStartedJob(Boolean.FALSE);
        } catch (IOException ex) {
            log.error("Exception while storing file: ", ex.getMessage());
            throw AppException.throwException(EntityType.CSV, ExceptionType.FILE_EXCEPTION, fileName);
        }
    }
}
