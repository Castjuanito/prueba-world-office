package com.prueba.world.office.employees.controller.v1.resources;

import com.prueba.world.office.employees.api.v1.DataOperations;
import com.prueba.world.office.employees.dto.model.FileUploadDto;
import com.prueba.world.office.employees.dto.response.Response;
import com.prueba.world.office.employees.dto.response.ResponseError;
import com.prueba.world.office.employees.dto.response.ResponseStatus;
import com.prueba.world.office.employees.service.data.DataService;
import com.prueba.world.office.employees.service.data.FileService;
import com.prueba.world.office.employees.task.LoadCSVFileTask;
import com.prueba.world.office.employees.util.DateUtils;
import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
public class DataController implements DataOperations {

    @Autowired
    private FileService fileService;

    @Autowired
    private TaskScheduler taskScheduler;

    @Autowired
    private DataService dataService;


    @Override
    public ResponseEntity<Response> uploadCSVFile(MultipartFile file) throws IOException {
        if (!file.getContentType().contains("csv")) {
            log.error("File uploaded by the client is not a csv ", file.getContentType());
            ResponseError error = ResponseError
                    .builder()
                    .timeStamp(DateUtils.now())
                    .message("File must be a CSV file")
                    .details(List.of())
                    .build();
            Response responseError = Response
                    .builder()
                    .status(ResponseStatus.BAD_REQUEST)
                    .error(error)
                    .build();

            return new ResponseEntity<>(responseError, HttpStatus.BAD_REQUEST);
        }

        FileUploadDto fileUploadDto = fileService.storeFile(file.getBytes());

        taskScheduler.schedule(new LoadCSVFileTask(fileUploadDto.getFileName(), dataService), Instant.now().plusSeconds(FIXED_RATE_FOR_TASK_SC));

        Response response = Response.
                builder()
                .status(ResponseStatus.OK)
                .payload(fileUploadDto)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
