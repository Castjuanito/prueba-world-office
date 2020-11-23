package com.prueba.world.office.employees.repository;

import com.prueba.world.office.employees.model.Department;
import com.prueba.world.office.employees.model.custom.SalarySumByDepartment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends PagingAndSortingRepository<Department, Long> {
    @Query("SELECT new com.prueba.world.office.employees.model.custom.SalarySumByDepartment(d.name, SUM(e.salary)) FROM Department AS d LEFT JOIN Employee as e ON d.id = e.department.id GROUP BY d.id")
    List<SalarySumByDepartment> salarySumByDepartment();

    Optional<Department> findByShortCode(String shortCode);

    Page<Department> findAll(Pageable pageable);
}
