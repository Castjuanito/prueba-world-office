package com.prueba.world.office.employees.service.data;

import com.prueba.world.office.employees.dto.model.FileUploadDto;

public interface FileService {
    FileUploadDto storeFile(byte[] fileContent);
}
