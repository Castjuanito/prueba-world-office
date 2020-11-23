package com.prueba.world.office.employees.dto.mapper;

import com.prueba.world.office.employees.dto.model.EmployeeDto;
import com.prueba.world.office.employees.model.Employee;

public class EmployeeMapper {
    public static EmployeeDto toEmployeeDto(Employee employee) {
        return new EmployeeDto()
                .setId(employee.getId())
                .setName(employee.getName())
                .setRole(employee.getRole())
                .setSalary(employee.getSalary())
                .setFullTime(employee.getFullTime())
                .setDepartmentName(employee.getDepartment().getName());
    }
}
