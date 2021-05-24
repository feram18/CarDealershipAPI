package edu.towson.cosc457.CarDealership.service;

import edu.towson.cosc457.CarDealership.model.Employee;

import java.util.List;

public interface EmployeeService <T extends Employee> {
    /**
     * Create new entry of type Employee in the database
     * @param employee object of type Employee to be added database
     * @return Employee saved on repository
     */
    T addEmployee(T employee);

    /**
     * Get all Employees (of given type)
     * @return List of Employees
     */
    List<T> getEmployees();

    /**
     * Get Employee by Id
     * @param id identifier of Employee to be fetched
     * @return fetched Employee
     */
    T getEmployee(Long id);

    /**
     * Delete Employee by Id
     * @param id identifier of Employee to be deleted
     * @return deleted Employee
     */
    T deleteEmployee(Long id);

    /**
     * Update Employee
     * @param id identifier of Employee to be updated
     * @param employee object of type Employee with updated fields
     * @return updated Employee entity
     */
    T editEmployee(Long id, T employee);
}
