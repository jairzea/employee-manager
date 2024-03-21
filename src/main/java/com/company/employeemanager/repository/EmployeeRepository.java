package com.company.employeemanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.company.employeemanager.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // Puedes agregar métodos adicionales aquí si es necesario para consultas personalizadas
}
