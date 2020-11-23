package com.prueba.world.office.employees.service.department;

import com.prueba.world.office.employees.dto.mapper.DepartmentMapper;
import com.prueba.world.office.employees.dto.mapper.SalariesSumByDepartmentMapper;
import com.prueba.world.office.employees.dto.model.DepartmentDto;
import com.prueba.world.office.employees.dto.model.PaginatedDepartmentListDto;
import com.prueba.world.office.employees.dto.model.SalariesSumByDepartmentDto;
import com.prueba.world.office.employees.dto.response.PageMetadata;
import com.prueba.world.office.employees.model.Department;
import com.prueba.world.office.employees.model.custom.SalarySumByDepartment;
import com.prueba.world.office.employees.repository.DepartmentRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public List<SalariesSumByDepartmentDto> listSalariesByDepartment() {
        List<SalarySumByDepartment> salarySumByDepartments = departmentRepository.salarySumByDepartment();
        log.info("Listing Sum of Salaries by department succeeded");
        return salarySumByDepartments
                .stream()
                .map(SalariesSumByDepartmentMapper::toSalariesSumByDepartmentDto)
                .collect(Collectors.toList());
    }

    @Override
    public PaginatedDepartmentListDto listDepartments(int pageSize, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        Page<Department> departments = departmentRepository.findAll(pageable);

        List<DepartmentDto> departmentDtoList = departments
                .toList()
                .stream()
                .map(DepartmentMapper::toDepartmentDto)
                .collect(Collectors.toList());

        PageMetadata pageMetadata = PageMetadata
                .builder()
                .pageNumber(departments.getNumber() + 1)
                .pageSize(departments.getSize())
                .totalPages(departments.getTotalPages())
                .build();

        log.info("Listing all departments");
        return new PaginatedDepartmentListDto()
                .setDepartmentDtoList(departmentDtoList)
                .setPageMetadata(pageMetadata);
    }
}
