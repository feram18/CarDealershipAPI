package edu.towson.cosc457.CarDealership.service;

import edu.towson.cosc457.CarDealership.model.Employee;

import java.util.List;

public interface EmployeeService <T extends Employee> {
    T addEmployee(T employee);
    List<T> getEmployees();
    T getEmployee(Long id);
    T deleteEmployee(Long id);
    T editEmployee(Long id, T employee);
}
