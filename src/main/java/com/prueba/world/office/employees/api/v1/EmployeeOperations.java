package com.prueba.world.office.employees.api.v1;

import com.prueba.world.office.employees.dto.response.Response;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/v1/employee")
public interface EmployeeOperations extends APIConstants {

    @GetMapping("")
    ResponseEntity<Response> listEmployees(
            @Valid @RequestParam(defaultValue = "10") @Min(1) @Max(MAX_PAGE_SIZE) Integer pageSize,
            @Valid @RequestParam(defaultValue = "1") @Min(1) @Max(MAX_PAGE_NUMBER) Integer pageNumber
    );

    @GetMapping("/highest-salaries")
    ResponseEntity<Response> listEmployeesWithHighestSalaries(
            @Valid @RequestParam(defaultValue = "5") @Min(1) @Max(MAX_EMPLOYEES_PER_QUERY) Integer requiredEmployeesLength
    );

    @GetMapping("/by-department")
    ResponseEntity<Response> listEmployeesByDepartment(
            @Valid @RequestParam() Long departmentId,
            @Valid @RequestParam(defaultValue = "10") @Min(1) @Max(MAX_PAGE_SIZE) Integer pageSize,
            @Valid @RequestParam(defaultValue = "1") @Min(1) @Max(MAX_PAGE_NUMBER) Integer pageNumber
    );
}
