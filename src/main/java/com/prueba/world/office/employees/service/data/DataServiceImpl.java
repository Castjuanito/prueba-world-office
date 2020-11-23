package com.prueba.world.office.employees.service.data;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.MappingStrategy;
import com.prueba.world.office.employees.exception.AppException;
import com.prueba.world.office.employees.exception.EntityType;
import com.prueba.world.office.employees.exception.ExceptionType;
import com.prueba.world.office.employees.model.Department;
import com.prueba.world.office.employees.model.Employee;
import com.prueba.world.office.employees.model.csv.CSVEmployeeInput;
import com.prueba.world.office.employees.repository.DepartmentRepository;
import com.prueba.world.office.employees.repository.EmployeeRepository;
import com.prueba.world.office.employees.util.DepartmentUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DataServiceImpl implements DataService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Value("${file.server.path}")
    private String path;

    @Override
    public void updateDataBaseFromCSV(String filePath) {
        String folderPath = path + "/csv/";
        File file = new File(folderPath + filePath);
        if (file.exists()) {
            try (Reader reader = new BufferedReader(new FileReader(file))) {

                CsvToBean<CSVEmployeeInput> csvEmployeeInput = new CsvToBeanBuilder(reader)
                        .withType(CSVEmployeeInput.class)
                        .withSkipLines(1)
                        .withIgnoreLeadingWhiteSpace(true)
                        .build();


                List<CSVEmployeeInput> csvEmployeeInputs = csvEmployeeInput.parse();
                csvEmployeeInputs
                        .stream()
                        .forEach(value -> insertRecordInDataBase(value));
                log.info("csv file loaded successfully");
                return;
            } catch (Exception e) {
                e.printStackTrace();
                log.error("Error: error processing the file", e.getMessage());
                throw AppException.throwException(EntityType.CSV, ExceptionType.FILE_EXCEPTION, filePath);
            }

        }
        log.error("File " + filePath + " does not exist");
        throw AppException.throwException(EntityType.CSV, ExceptionType.ENTITY_NOT_FOUND, filePath);
    }

    private CSVEmployeeInput insertRecordInDataBase(CSVEmployeeInput csvEmployeeInput) {
        log.info("Saving "+ csvEmployeeInput.getNombre());
        String shortCode = DepartmentUtils.shortCode(csvEmployeeInput.getDepartamento());
        Optional<Department> department = departmentRepository.findByShortCode(shortCode);
        if (!department.isPresent()) {
            log.warn("department " + csvEmployeeInput.getDepartamento()+ " does not exist, shortcode is: " + shortCode);
            Department newDepartment = new Department()
                    .setName(csvEmployeeInput.getDepartamento())
                    .setShortCode(shortCode);
            department = Optional.ofNullable(departmentRepository.save(newDepartment));
            if (!department.isPresent()) {
                log.error("Error: error saving: ", csvEmployeeInput.toString());
                throw AppException.throwException(EntityType.CSV, ExceptionType.FILE_EXCEPTION);
            }
        }
        Employee newEmployee = new Employee()
                .setDepartment(department.get())
                .setFullTime(csvEmployeeInput.getTiempoCompleto())
                .setName(csvEmployeeInput.getNombre())
                .setRole(csvEmployeeInput.getCargo())
                .setSalary(csvEmployeeInput.getSalario());
        Optional<Employee> createdEmployee = Optional.ofNullable(employeeRepository.save(newEmployee));
        if (!createdEmployee.isPresent()) {
            log.error("Error: error saving: ", newEmployee.toString());
            throw AppException.throwException(EntityType.CSV, ExceptionType.FILE_EXCEPTION);
        }
        log.info("Adding employee record succeed");
        return csvEmployeeInput;
    }
}
