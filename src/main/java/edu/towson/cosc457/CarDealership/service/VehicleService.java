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

    /**
     * Create a new Vehicle in the database
     * @param vehicle Vehicle object to be added to database
     * @return Vehicle saved on repository
     */
    public Vehicle addVehicle(Vehicle vehicle) {
        LOGGER.info("Create new Vehicle in the database");
        return vehicleRepository.save(vehicle);
    }

    /**
     * Get All Vehicles
     * @return List of Vehicles
     */
    public List<Vehicle> getVehicles() {
        LOGGER.info("Get all Vehicles");
        return StreamSupport
                .stream(vehicleRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    /**
     * Get Vehicle by Id
     * @param id identifier of Vehicle to be fetched
     * @return fetched Vehicle
     * @throws NotFoundException if no Vehicle with matching id found
     */
    public Vehicle getVehicle(Long id) {
        LOGGER.info("Get Vehicle with id {}", id);
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Entity.VEHICLE.toString(), id, HttpStatus.NOT_FOUND));
    }

    /**
     * Delete Vehicle by Id
     * @param id identifier of Vehicle to be deleted
     * @return deleted Vehicle
     * @throws NotFoundException if no Vehicle with matching id found
     */
    public Vehicle deleteVehicle(Long id) {
        LOGGER.info("Delete Vehicle with id {}", id);
        Vehicle vehicle = getVehicle(id);
        vehicleRepository.delete(vehicle);
        return vehicle;
    }

    /**
     * Update Vehicle
     * @param id identifier of Vehicle to be updated
     * @param vehicle Vehicle object with updated fields
     * @return updated Vehicle
     * @throws NotFoundException if no Vehicle with matching id found
     */
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

    /**
     * Assign Vehicle to ServiceTicket
     * @param vehicleId identifier of Vehicle to be updated
     * @param ticketId identifier of ServiceTicket Vehicle will be assigned to
     * @return updated Vehicle entity
     * @throws NotFoundException if no Vehicle or ServiceTicket with matching vehicleId/ticketId were found
     * @throws AlreadyAssignedException if ServiceTicket has already been assigned to a Vehicle
     */
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

    /**
     * Remove assigned Vehicle from ServiceTicket
     * @param vehicleId identifier of Vehicle to be updated
     * @param ticketId identifier of ServiceTicket Vehicle will be removed from
     * @return updated Vehicle entity
     * @throws NotFoundException if no Vehicle or ServiceTicket with matching vehicleId/ticketId were found
     */
    @Transactional
    public Vehicle removeTicket(Long vehicleId, Long ticketId) {
        LOGGER.info("Remove Vehicle with id {} from Service Ticket with id {}", vehicleId, ticketId);
        Vehicle vehicle = getVehicle(vehicleId);
        ServiceTicket ticket = ticketService.getServiceTicket(ticketId);
        vehicle.removeTicket(ticket);
        return vehicle;
    }
}
