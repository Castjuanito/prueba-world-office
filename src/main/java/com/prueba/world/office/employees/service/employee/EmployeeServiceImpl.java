package com.prueba.world.office.employees.service.employee;

import com.prueba.world.office.employees.dto.mapper.EmployeeMapper;
import com.prueba.world.office.employees.dto.model.EmployeeDto;
import com.prueba.world.office.employees.dto.model.PaginatedEmployeesListDto;
import com.prueba.world.office.employees.dto.response.PageMetadata;
import com.prueba.world.office.employees.exception.AppException;
import com.prueba.world.office.employees.exception.EntityType;
import com.prueba.world.office.employees.exception.ExceptionType;
import com.prueba.world.office.employees.model.Department;
import com.prueba.world.office.employees.model.Employee;
import com.prueba.world.office.employees.repository.DepartmentRepository;
import com.prueba.world.office.employees.repository.EmployeeRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public List<EmployeeDto> listEmployeesWithHighestSalary(int n) {
        Pageable pageable =
                PageRequest.of(0, n, Sort.by(Sort.Direction.DESC, "salary"));

        List<Employee> employees = employeeRepository
                .findAll(pageable)
                .toList();

        log.info("Listing employees with highest salaries succeeded");
        return employees
                .stream()
                .map(EmployeeMapper::toEmployeeDto)
                .collect(Collectors.toList());
    }

    @Override
    public PaginatedEmployeesListDto listEmployeesByDepartment(long departmentId, int pageSize, int page) {
        Optional<Department> department = departmentRepository.findById(departmentId);
        if (department.isPresent()) {
            Pageable pageable =
                    PageRequest.of(page - 1, pageSize);
            Page<Employee> employees =
                    employeeRepository.findAllByDepartment(department.get(), pageable);

            log.info("Listing Paginated Employees by department: " + department.get().getName());
            return buildPaginatedEmployeesDto(employees);
        }
        log.error("Department " + departmentId + " not found");
        throw AppException.throwException(EntityType.DEPARTMENT, ExceptionType.ENTITY_NOT_FOUND, String.valueOf(departmentId));
    }

    @Override
    public PaginatedEmployeesListDto listEmployees(int pageSize, int page) {
        Pageable pageable =
                PageRequest.of(page - 1, pageSize);
        Page<Employee> employees =
                employeeRepository.findAll(pageable);

        log.info("Listing Paginated Employees Succeed!");
        return buildPaginatedEmployeesDto(employees);

    }

    private PaginatedEmployeesListDto buildPaginatedEmployeesDto(Page<Employee> employees) {
        List<EmployeeDto> employeeDtoList = employees
                .toList()
                .stream()
                .map(EmployeeMapper::toEmployeeDto)
                .collect(Collectors.toList());
        PageMetadata metadata = PageMetadata
                .builder()
                .pageNumber(employees.getNumber() + 1)
                .pageSize(employees.getSize())
                .totalPages(employees.getTotalPages())
                .build();

        return new PaginatedEmployeesListDto()
                .setEmployeeDtoList(employeeDtoList)
                .setMetadata(metadata);
    }
}
