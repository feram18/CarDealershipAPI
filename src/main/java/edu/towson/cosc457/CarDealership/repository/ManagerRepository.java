package edu.towson.cosc457.CarDealership.repository;

import edu.towson.cosc457.CarDealership.model.Department;
import edu.towson.cosc457.CarDealership.model.Manager;
import edu.towson.cosc457.CarDealership.model.SiteManager;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ManagerRepository extends EmployeeRepository {
    Optional<List<Manager>> findBySiteManager(SiteManager siteManager);
    Optional<Manager> findByDepartment(Department department);
}
