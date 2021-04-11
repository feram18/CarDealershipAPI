package edu.towson.cosc457.CarDealership.repository;

import edu.towson.cosc457.CarDealership.model.Department;
import edu.towson.cosc457.CarDealership.model.Manager;
import edu.towson.cosc457.CarDealership.model.SalesAssociate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SalesAssociateRepository extends EmployeeRepository<SalesAssociate> {
    Optional<List<SalesAssociate>> findByManager(Manager manager);
    Optional<List<SalesAssociate>> findByDepartment(Department department);
}
