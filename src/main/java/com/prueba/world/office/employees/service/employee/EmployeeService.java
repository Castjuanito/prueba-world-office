package com.prueba.world.office.employees.service.employee;

import com.prueba.world.office.employees.dto.model.EmployeeDto;
import com.prueba.world.office.employees.dto.model.PaginatedEmployeesListDto;
import java.util.List;

public interface EmployeeService {

    List<EmployeeDto> listEmployeesWithHighestSalary(int n);

    PaginatedEmployeesListDto listEmployeesByDepartment(long departmentId, int pageSize, int page);

    PaginatedEmployeesListDto listEmployees(int pageSize, int page);

}
