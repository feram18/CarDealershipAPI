package edu.towson.cosc457.CarDealership.controller;

import edu.towson.cosc457.CarDealership.service.EmployeeService;

public abstract class EmployeeController<T extends EmployeeService> {
    private final T employeeService;

    public EmployeeController(T employeeService) {
        this.employeeService = employeeService;
    }
}
