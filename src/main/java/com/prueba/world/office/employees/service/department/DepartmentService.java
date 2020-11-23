package com.prueba.world.office.employees.service.department;

import com.prueba.world.office.employees.dto.model.PaginatedDepartmentListDto;
import com.prueba.world.office.employees.dto.model.SalariesSumByDepartmentDto;
import java.util.List;

public interface DepartmentService {

    List<SalariesSumByDepartmentDto> listSalariesByDepartment();

    PaginatedDepartmentListDto listDepartments(int pageSize, int pageNumber);

}
