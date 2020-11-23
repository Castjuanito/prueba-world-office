package com.prueba.world.office.employees.controller.v1.resources;

import com.prueba.world.office.employees.api.v1.EmployeeOperations;
import com.prueba.world.office.employees.dto.model.EmployeeDto;
import com.prueba.world.office.employees.dto.model.PaginatedEmployeesListDto;
import com.prueba.world.office.employees.dto.response.Response;
import com.prueba.world.office.employees.dto.response.ResponseStatus;
import com.prueba.world.office.employees.service.employee.EmployeeService;
import java.util.List;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController implements EmployeeOperations {

    @Autowired
    private EmployeeService employeeService;

    @Override
    public ResponseEntity<Response> listEmployees(
            @Min(1) @Max(MAX_PAGE_SIZE) Integer pageSize,
            @Min(1) @Max(MAX_PAGE_NUMBER) Integer pageNumber
    ) {
        PaginatedEmployeesListDto employees = employeeService.listEmployees(
                pageSize.intValue(),
                pageNumber.intValue()
        );
        Response response = Response
                .builder()
                .status(ResponseStatus.OK)
                .payload(employees.getEmployeeDtoList())
                .metadata(employees.getMetadata())
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Response> listEmployeesWithHighestSalaries(
            @Min(1) @Max(MAX_EMPLOYEES_PER_QUERY) Integer requiredEmployeesLength
    ) {
        List<EmployeeDto> employees = employeeService.listEmployeesWithHighestSalary(requiredEmployeesLength.intValue());
        Response response = Response
                .builder()
                .status(ResponseStatus.OK)
                .payload(employees)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Response> listEmployeesByDepartment(
            Long departmentId,
            @Min(1) @Max(MAX_PAGE_SIZE) Integer pageSize,
            @Min(1) @Max(MAX_PAGE_NUMBER) Integer pageNumber
    ) {
        PaginatedEmployeesListDto employees = employeeService.listEmployeesByDepartment(
                departmentId.longValue(),
                pageSize.intValue(),
                pageNumber.intValue()
        );
        Response response = Response
                .builder()
                .status(ResponseStatus.OK)
                .payload(employees.getEmployeeDtoList())
                .metadata(employees.getMetadata())
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
