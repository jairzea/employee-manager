package com.company.employeemanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.company.employeemanager.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByGender(String string);

    List<Employee> findByAgeGreaterThanEqual(int age);

}
