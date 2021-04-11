package edu.towson.cosc457.CarDealership.repository;

import edu.towson.cosc457.CarDealership.model.Location;
import edu.towson.cosc457.CarDealership.model.SiteManager;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SiteManagerRepository extends EmployeeRepository<SiteManager> {
    Optional<SiteManager> findByLocation(Location location);
}
