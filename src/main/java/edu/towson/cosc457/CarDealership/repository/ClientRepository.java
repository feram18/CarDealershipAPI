package edu.towson.cosc457.CarDealership.repository;

import edu.towson.cosc457.CarDealership.misc.Gender;
import edu.towson.cosc457.CarDealership.model.Client;
import edu.towson.cosc457.CarDealership.model.SalesAssociate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findBySsn(String ssn);
    Optional<List<Client>> findByFirstName(String firstName);
    Optional<List<Client>> findByLastName(String lastName);
    Optional<List<Client>> findByGender(Gender gender);
    Optional<Client> findByEmail(String email);
    Optional<Client> findByPhoneNumber(String phoneNumber);
    Optional<List<Client>> findBySalesAssociate(SalesAssociate salesAssociate);
}
