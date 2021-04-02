package edu.towson.cosc457.CarDealership.controller;

import edu.towson.cosc457.CarDealership.model.Employee;
import edu.towson.cosc457.CarDealership.model.dto.EmployeeDto;
import edu.towson.cosc457.CarDealership.service.EmployeeService;
import org.springframework.http.ResponseEntity;

public abstract class EmployeeController<T extends Employee> {
    private final EmployeeService<T> employeeService;

    public EmployeeController(EmployeeService<T> employeeService) {
        this.employeeService = employeeService;
    }
}
