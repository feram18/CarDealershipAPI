package edu.towson.cosc457.CarDealership.repository;

import edu.towson.cosc457.CarDealership.model.Department;
import edu.towson.cosc457.CarDealership.model.Manager;
import edu.towson.cosc457.CarDealership.model.Mechanic;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MechanicRepository extends EmployeeRepository<Mechanic> {
    Optional<List<Mechanic>> findByManager(Manager manager);
    Optional<List<Mechanic>> findByDepartment(Department department);
}
