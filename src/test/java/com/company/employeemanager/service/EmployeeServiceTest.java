package com.company.employeemanager.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.company.employeemanager.model.Employee;
import com.company.employeemanager.repository.EmployeeRepository;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    void testGetAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("John", "Doe", "Male", 30, "john.doe@example.com"));
        employees.add(new Employee("Jane", "Doe", "Female", 25, "jane.doe@example.com"));
        Page<Employee> page = new PageImpl<>(employees);

        when(employeeRepository.findAll(any(Pageable.class))).thenReturn(page);

        Page<Employee> result = employeeService.getAllEmployees(1, 10);

        assertEquals(2, result.getTotalElements());
    }

    @Test
    void testGetEmployeeById() {
        Employee employee = new Employee("John", "Doe", "Male", 30, "john.doe@example.com");
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        Employee result = employeeService.getEmployeeById(1L);

        assertNotNull(result);
        assertEquals("John", result.getName());
    }

    @Test
    void testSaveEmployee() {
        Employee employee = new Employee("John", "Doe", "Male", 30, "john.doe@example.com");
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        Employee savedEmployee = employeeService.saveEmployee(employee);

        assertNotNull(savedEmployee);
        assertEquals("John", savedEmployee.getName());
    }

    @Test
    void testDeleteEmployeeById() {
        assertDoesNotThrow(() -> employeeService.deleteEmployeeById(1L));
    }

    @Test
    void testGetEmployeesWithAgeGreaterThanEqual() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("John", "Doe", "Male", 30, "john.doe@example.com"));
        employees.add(new Employee( "Jane", "Doe", "Female", 25, "jane.doe@example.com"));

        when(employeeRepository.findByAgeGreaterThanEqual(25)).thenReturn(employees);

        List<Employee> result = employeeService.getEmployeesWithAgeGreaterThanEqual(25);

        assertEquals(2, result.size());
    }

    @Test
    void testGetFemaleEmployees() {
        List<Employee> femaleEmployees = new ArrayList<>();
        femaleEmployees.add(new Employee( "Jane", "Doe", "Female", 25, "jane.doe@example.com"));

        when(employeeRepository.findByGender("Female")).thenReturn(femaleEmployees);

        List<Employee> result = employeeService.getFemaleEmployees("Female");

        assertEquals(1, result.size());
    }

    @Test
    void testEditEmployee() {
        Employee existingEmployee = new Employee("John", "Doe", "Male", 30, "john.doe@example.com");
        Employee newEmployeeData = new Employee("Jane", "Doe", "Female", 25, "jane.doe@example.com");
        Optional<Employee> optionalEmployee = Optional.of(existingEmployee);

        when(employeeRepository.findById(1L)).thenReturn(optionalEmployee);
        when(employeeRepository.save(any(Employee.class))).thenReturn(newEmployeeData);

        Optional<Employee> result = employeeService.editEmployee(1L, newEmployeeData);

        assertTrue(result.isPresent());
        assertEquals("Jane", result.get().getName());
    }
}

