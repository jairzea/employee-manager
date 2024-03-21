package com.company.employeemanager.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.company.employeemanager.model.Employee;
import com.company.employeemanager.service.EmployeeService;
import com.company.employeemanager.utils.ServicesUtils;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    
    @PostMapping("/save")
    public ResponseEntity<?> saveEmployee(@Valid @RequestBody Employee employee, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ServicesUtils.validation(bindingResult);
        }

        try {
            Employee savedEmployee = employeeService.saveEmployee(employee);
            return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping()
    public ResponseEntity<Page<Employee>> getAllEmployees(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Page<Employee> employeesPage = employeeService.getAllEmployees(page, size);
            return new ResponseEntity<>(employeesPage, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable Long id) {
        try {
            Employee employee = employeeService.getEmployeeById(id);
            if (employee != null) {
                return new ResponseEntity<>(employee, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Empleado con el ID " + id + " no encontrado", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployeeById(@PathVariable Long id) {
        try {
            Employee employee = employeeService.getEmployeeById(id);
            if (employee != null) {
                employeeService.deleteEmployeeById(id);
                return ServicesUtils.message("Empleado eliminado exitosamente", HttpStatus.OK);
            } else {
                return ServicesUtils.gResponseEntity("Empleado con el ID " + id + " no encontrado", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/age")
    public ResponseEntity<?> getEmployeesWithAgeGreaterThanEqual(@RequestParam(required = false, defaultValue = "40") Integer age) {
        try {
            List<Employee> employees = employeeService.getEmployeesWithAgeGreaterThanEqual(age);
            if(employees.size() <= 0){
                return ServicesUtils.gResponseEntity("No se encontraron empleados con edad igual o mayor a: " + age, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(employees, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/female")
    public ResponseEntity<?> getFemaleEmployees(@RequestParam(required = false, defaultValue = "Femenino") String gender) {
        try {
            List<Employee> employees = employeeService.getFemaleEmployees(gender);
            if(employees.size() <= 0){
                return ServicesUtils.gResponseEntity("No se encontraron empleados con el sexo: " + gender, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(employees, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editEmployee(@PathVariable Long id, @Valid @RequestBody Employee newEmployeeData, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ServicesUtils.validation(bindingResult);
        }
        try {
            Optional<Employee> editedEmployee = employeeService.editEmployee(id, newEmployeeData);
            if (editedEmployee.isPresent()) {
                return ResponseEntity.ok(editedEmployee.get());
            } else {
                return ServicesUtils.gResponseEntity("Empleado con el ID " + id + " no encontrado", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }
}
