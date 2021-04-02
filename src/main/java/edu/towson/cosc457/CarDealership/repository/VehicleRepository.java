package edu.towson.cosc457.CarDealership.repository;

import edu.towson.cosc457.CarDealership.misc.TransmissionType;
import edu.towson.cosc457.CarDealership.misc.VehicleType;
import edu.towson.cosc457.CarDealership.model.Lot;
import edu.towson.cosc457.CarDealership.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    Optional<List<Vehicle>> findByVin(String vin);
    Optional<List<Vehicle>> findByMake(String make);
    Optional<List<Vehicle>> findByYear(Integer year);
    Optional<List<Vehicle>> findByMakeAndModel(String make, String model);
    Optional<List<Vehicle>> findByMakeAndModelAndYear(String make, String model, Integer year);
    Optional<List<Vehicle>> findByColor(String color);
    Optional<List<Vehicle>> findByType(VehicleType type);
    Optional<List<Vehicle>> findByTransmission(TransmissionType transmission);
    Optional<List<Vehicle>> findByFeaturesLikeIgnoreCase(String features);
    Optional<List<Vehicle>> findByLot(Lot lot);
    Optional<List<Vehicle>> findByMpgLessThan(Integer mpg);
    Optional<List<Vehicle>> findByMpgGreaterThan(Integer mpg);
    Optional<List<Vehicle>> findByMpgBetween(Integer minMpg, Integer maxMpg);
    Optional<List<Vehicle>> findByMileageLessThan(Integer mileage);
    Optional<List<Vehicle>> findByMileageGreaterThan(Integer mileage);
    Optional<List<Vehicle>> findByMileageBetween(Integer minMileage, Integer maxMileage);
    Optional<List<Vehicle>> findByPriceLessThan(Double price);
    Optional<List<Vehicle>> findByPriceGreaterThan(Double price);
    Optional<List<Vehicle>> findByPriceBetween(Double minPrice, Double maxPrice);
}
