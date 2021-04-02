package edu.towson.cosc457.CarDealership.repository;

import edu.towson.cosc457.CarDealership.misc.Status;
import edu.towson.cosc457.CarDealership.model.Mechanic;
import edu.towson.cosc457.CarDealership.model.ServiceTicket;
import edu.towson.cosc457.CarDealership.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceTicketRepository extends JpaRepository<ServiceTicket, Long> {
    Optional<List<ServiceTicket>> findByVehicle(Vehicle vehicle);
    Optional<List<ServiceTicket>> findByMechanic(Mechanic mechanic);
    Optional<List<ServiceTicket>> findByDateCreated(LocalDate dateCreated);
    Optional<List<ServiceTicket>> findByDateUpdated(LocalDate dateUpdated);
    Optional<List<ServiceTicket>> findByStatus(Status status);
}
