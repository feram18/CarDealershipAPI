package edu.towson.cosc457.CarDealership.repository;

import edu.towson.cosc457.CarDealership.model.Location;
import edu.towson.cosc457.CarDealership.model.SiteManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    Optional<Location> findByName(String locationName);
    Optional<Location> findByAddressLike(String addressLike);
    Optional<Location> findBySiteManager(SiteManager siteManager);
}
