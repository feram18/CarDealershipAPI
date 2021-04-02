package edu.towson.cosc457.CarDealership.repository;

import edu.towson.cosc457.CarDealership.model.Location;
import edu.towson.cosc457.CarDealership.model.Lot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LotRepository extends JpaRepository<Lot, Long> {
    Optional<List<Lot>> findBySizeLessThan(Double size);
    Optional<List<Lot>> findBySizeGreaterThan(Double size);
    Optional<List<Lot>> findBySizeBetween(Double minSize, Double maxSize);
    Optional<List<Lot>> findByLocation(Location location);
}
