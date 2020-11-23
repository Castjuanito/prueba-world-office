package com.prueba.world.office.employees.dto.mapper;

import com.prueba.world.office.employees.dto.model.SalariesSumByDepartmentDto;
import com.prueba.world.office.employees.model.custom.SalarySumByDepartment;

public class SalariesSumByDepartmentMapper {
    public static SalariesSumByDepartmentDto toSalariesSumByDepartmentDto(SalarySumByDepartment salarySumByDepartment) {
        return new SalariesSumByDepartmentDto()
                .setDepartmentName(salarySumByDepartment.getDepartmentName())
                .setSumSalaries(salarySumByDepartment.getSalarySum());
    }
}
