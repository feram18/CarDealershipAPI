package edu.towson.cosc457.CarDealership.repository;

import edu.towson.cosc457.CarDealership.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<List<Address>> findByStreet(String street);
    Optional<List<Address>> findByCity(String city);
    Optional<List<Address>> findByState(String state);
    Optional<List<Address>> findByZipCode(Integer zipCode);
}
