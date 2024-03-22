package com.company.employeemanager.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.company.employeemanager.model.Employee;

@Service
public interface EmployeeService {

    Page<Employee> getAllEmployees(int page, int size);
    Employee getEmployeeById(Long id);
    Employee saveEmployee(Employee employee);
    void deleteEmployeeById(Long id);
    List<Employee> getEmployeesWithAgeGreaterThanEqual(int age);
    List<Employee> getFemaleEmployees(String gender);
    Optional<Employee> editEmployee(Long id, Employee newEmployeeData);
}
