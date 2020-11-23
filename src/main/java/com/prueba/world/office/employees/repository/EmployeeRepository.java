package com.prueba.world.office.employees.repository;

import com.prueba.world.office.employees.model.Department;
import com.prueba.world.office.employees.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long> {
    Page<Employee> findAll(Pageable pageable);

    Page<Employee> findAllByDepartment(Department department, Pageable pageable);
}
