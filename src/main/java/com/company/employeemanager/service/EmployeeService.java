package com.company.employeemanager.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.company.employeemanager.model.Employee;
import com.company.employeemanager.repository.EmployeeRepository;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Page<Employee> getAllEmployees(int page, int size) {
        int newPage =  (page < 1) ? 1 : (page - 1);
        int maxSize = Math.min(size, 30);
        Pageable pageable = PageRequest.of(newPage, maxSize, Sort.by("id").ascending());
        return employeeRepository.findAll(pageable);
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public void deleteEmployeeById(Long id) {
        employeeRepository.deleteById(id);
    }

    public List<Employee> getEmployeesWithAgeGreaterThanEqual(int age) {
        return employeeRepository.findByAgeGreaterThanEqual(age);
    }

    public List<Employee> getFemaleEmployees(String gender) {
        return employeeRepository.findByGender(gender);
    }

    public Optional<Employee> editEmployee(Long id, Employee newEmployeeData) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isPresent()) {
            Employee existingEmployee = optionalEmployee.get();
            existingEmployee.setName(newEmployeeData.getName());
            existingEmployee.setLastNames(newEmployeeData.getLastNames());
            existingEmployee.setGender(newEmployeeData.getGender());
            existingEmployee.setAge(newEmployeeData.getAge());
            existingEmployee.setEmail(newEmployeeData.getEmail());
            return Optional.of(employeeRepository.save(existingEmployee));
        } else {
            return Optional.empty();
        }
    }
}
