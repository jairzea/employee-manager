package com.company.employeemanager.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del empleado es obligatorio")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String name;

    @NotBlank(message = "El Apellido del empleado es obligatorio")
    @Size(min = 2, max = 50, message = "El apellido debe tener entre 2 y 50 caracteres")
    private String lastNames;

    @NotBlank(message = "El sexo del empleado es obligatorio")
    @Pattern(regexp = "^(?:M|F)$", message = "El sexo debe ser 'M' o 'F'")
    private String gender;
    
    @PositiveOrZero(message = "La edad debe ser un número entero válido")
    @NotBlank(message = "La edad del empleado es obligatoria")
    private String age;

    @NotBlank(message = "El correo del empleado es obligatorio")
    @Email(message = "El correo electrónico debe tener un formato válido")
    private String email;
    
    // Constructor vacío
    public Employee() {
    }

    // Constructor con parámetros
    public Employee(String name, String lastNames, String gender, String age, String email) {
        this.name = name;
        this.lastNames = lastNames;
        this.gender = gender;
        this.age = age;
        this.email = email;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastNames() {
        return lastNames;
    }

    public void setLastNames(String lastNames) {
        this.lastNames = lastNames;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
