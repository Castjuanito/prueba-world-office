package com.prueba.world.office.employees.task;

import com.prueba.world.office.employees.service.data.DataService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Slf4j
@RequiredArgsConstructor
public class LoadCSVFileTask implements Runnable {

    private final String fileName;

    private final DataService dataService;

    @Override
    public void run() {
        processFile();
    }

    public void processFile() {
        log.info("Start file processing " + fileName);
        dataService.updateDataBaseFromCSV(fileName);
        log.info("Ending file processing " + fileName);
    }
}
