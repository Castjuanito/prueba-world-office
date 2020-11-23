package com.prueba.world.office.employees.api.v1;

import com.prueba.world.office.employees.dto.response.Response;
import java.io.IOException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/v1/data")
public interface DataOperations extends APIConstants {

    @PostMapping("/csv/upload")
    ResponseEntity<Response> uploadCSVFile(
            @RequestParam MultipartFile file
    ) throws IOException;
}
