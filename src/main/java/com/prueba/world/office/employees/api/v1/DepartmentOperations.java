package com.prueba.world.office.employees.api.v1;

import com.prueba.world.office.employees.dto.response.Response;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/v1/department")
public interface DepartmentOperations extends APIConstants {

    @GetMapping("")
    ResponseEntity<Response> listDepartments(
            @RequestParam(defaultValue = "10") @Min(1) @Max(MAX_PAGE_SIZE) Integer pageSize,
            @RequestParam(defaultValue = "1") @Min(1) @Max(MAX_PAGE_NUMBER) Integer pageNumber
    );

    @GetMapping("/salaries-sum")
    ResponseEntity<Response> sumSalariesByDepartment();

}
