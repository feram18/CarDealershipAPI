package edu.towson.cosc457.CarDealership.service;

import edu.towson.cosc457.CarDealership.exceptions.NotFoundException;
import edu.towson.cosc457.CarDealership.exceptions.AlreadyAssignedException;
import edu.towson.cosc457.CarDealership.misc.Entity;
import edu.towson.cosc457.CarDealership.model.ServiceTicket;
import edu.towson.cosc457.CarDealership.model.Vehicle;
import edu.towson.cosc457.CarDealership.repository.VehicleRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class VehicleService {
    private final VehicleRepository vehicleRepository;
    private final ServiceTicketService ticketService;
    private static final Logger LOGGER = LoggerFactory.getLogger(VehicleService.class);

    public Vehicle addVehicle(Vehicle vehicle) {
        LOGGER.info("Create new Vehicle in the database");
        return vehicleRepository.save(vehicle);
    }

    public List<Vehicle> getVehicles() {
        LOGGER.info("Get all Vehicles");
        return StreamSupport
                .stream(vehicleRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Vehicle getVehicle(Long id) {
        LOGGER.info("Get Vehicle with id {}", id);
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Entity.VEHICLE.toString(), id, HttpStatus.NOT_FOUND));
    }

    public Vehicle deleteVehicle(Long id) {
        LOGGER.info("Delete Vehicle with id {}", id);
        Vehicle vehicle = getVehicle(id);
        vehicleRepository.delete(vehicle);
        return vehicle;
    }

    @Transactional
    public Vehicle editVehicle(Long id, Vehicle vehicle) {
        LOGGER.info("Update Vehicle with id {}", id);
        Vehicle vehicleToEdit = getVehicle(id);
        vehicleToEdit.setVin(vehicle.getVin());
        vehicleToEdit.setMake(vehicle.getMake());
        vehicleToEdit.setModel(vehicle.getModel());
        vehicleToEdit.setYear(vehicle.getYear());
        vehicleToEdit.setColor(vehicle.getColor());
        vehicleToEdit.setType(vehicle.getType());
        vehicleToEdit.setTransmission(vehicle.getTransmission());
        vehicleToEdit.setFeatures(vehicle.getFeatures());
        vehicleToEdit.setMpg(vehicle.getMpg());
        vehicleToEdit.setMileage(vehicle.getMileage());
        vehicleToEdit.setPrice(vehicle.getPrice());
        vehicleToEdit.setLot(vehicle.getLot());
        vehicleToEdit.setTickets(vehicle.getTickets());
        return vehicleToEdit;
    }

    @Transactional
    public Vehicle assignTicket(Long vehicleId, Long ticketId) {
        LOGGER.info("Assign Vehicle with id {} to Service Ticket with id {}", vehicleId, ticketId);
        Vehicle vehicle = getVehicle(vehicleId);
        ServiceTicket ticket = ticketService.getServiceTicket(ticketId);
        if (Objects.nonNull(ticket.getVehicle())) {
            throw new AlreadyAssignedException(
                    Entity.SERVICE_TICKET.toString(),
                    ticketId,
                    Entity.VEHICLE.toString(),
                    ticket.getVehicle().getId(),
                    HttpStatus.BAD_REQUEST
            );
        }
        vehicle.assignTicket(ticket);
        ticket.setVehicle(vehicle);
        return vehicle;
    }

    @Transactional
    public Vehicle removeTicket(Long vehicleId, Long ticketId) {
        LOGGER.info("Remove Vehicle with id {} from Service Ticket with id {}", vehicleId, ticketId);
        Vehicle vehicle = getVehicle(vehicleId);
        ServiceTicket ticket = ticketService.getServiceTicket(ticketId);
        vehicle.removeTicket(ticket);
        return vehicle;
    }
}
