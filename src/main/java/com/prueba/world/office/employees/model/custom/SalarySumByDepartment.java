package com.prueba.world.office.employees.model.custom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@AllArgsConstructor
@Accessors(chain = true)
public class SalarySumByDepartment {
    private final String departmentName;
    private final Double salarySum;
}
