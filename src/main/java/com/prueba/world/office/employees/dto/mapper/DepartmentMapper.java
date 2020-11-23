package com.prueba.world.office.employees.dto.mapper;

import com.prueba.world.office.employees.dto.model.DepartmentDto;
import com.prueba.world.office.employees.model.Department;

public class DepartmentMapper {
    public static DepartmentDto toDepartmentDto(Department department) {
        return new DepartmentDto()
                .setId(department.getId())
                .setName(department.getName())
                .setShortCode(department.getShortCode());
    }
}
