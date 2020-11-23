package com.prueba.world.office.employees.controller.v1.resources;

import com.prueba.world.office.employees.api.v1.DepartmentOperations;
import com.prueba.world.office.employees.dto.model.PaginatedDepartmentListDto;
import com.prueba.world.office.employees.dto.model.SalariesSumByDepartmentDto;
import com.prueba.world.office.employees.dto.response.Response;
import com.prueba.world.office.employees.dto.response.ResponseStatus;
import com.prueba.world.office.employees.service.department.DepartmentService;
import java.util.List;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DepartmentController implements DepartmentOperations {

    @Autowired
    private DepartmentService departmentService;

    @Override
    public ResponseEntity<Response> listDepartments(
            @Min(1) @Max(MAX_PAGE_SIZE) Integer pageSize,
            @Min(1) @Max(MAX_PAGE_NUMBER) Integer pageNumber
    ) {
        PaginatedDepartmentListDto departmentListDto = departmentService.listDepartments(
                pageSize.intValue(),
                pageNumber.intValue()
        );
        Response response = Response
                .builder()
                .status(ResponseStatus.OK)
                .payload(departmentListDto.getDepartmentDtoList())
                .metadata(departmentListDto.getPageMetadata())
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Response> sumSalariesByDepartment() {
        List<SalariesSumByDepartmentDto> salariesSumByDepartmentDtos = departmentService.listSalariesByDepartment();
        Response response = Response
                .builder()
                .status(ResponseStatus.OK)
                .payload(salariesSumByDepartmentDtos)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
