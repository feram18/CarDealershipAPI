package edu.towson.cosc457.CarDealership.service;

import edu.towson.cosc457.CarDealership.exceptions.NotFoundException;
import edu.towson.cosc457.CarDealership.exceptions.AlreadyAssignedException;
import edu.towson.cosc457.CarDealership.misc.Entity;
import edu.towson.cosc457.CarDealership.model.Mechanic;
import edu.towson.cosc457.CarDealership.model.ServiceTicket;
import edu.towson.cosc457.CarDealership.repository.MechanicRepository;
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
public class MechanicService implements EmployeeService<Mechanic> {
    private final MechanicRepository mechanicRepository;
    private final ServiceTicketService ticketService;
    private static final Logger LOGGER = LoggerFactory.getLogger(MechanicService.class);

    /**
     * Create a new Mechanic in the database
     * @param mechanic Mechanic object to be added to database
     * @return Mechanic saved on repository
     */
    @Override
    public Mechanic addEmployee(Mechanic mechanic) {
        LOGGER.info("Create new Mechanic in the database");
        return mechanicRepository.save(mechanic);
    }

    /**
     * Get All Mechanics
     * @return List of Mechanic
     */
    @Override
    public List<Mechanic> getEmployees() {
        LOGGER.info("Get all Mechanics");
        return StreamSupport
                .stream(mechanicRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    /**
     * Get Mechanic by Id
     * @param id identifier of Mechanic to be fetched
     * @return fetched Mechanic
     * @throws NotFoundException if no Mechanic with matching id found
     */
    @Override
    public Mechanic getEmployee(Long id) {
        LOGGER.info("Get Mechanic with id {}", id);
        return mechanicRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Entity.MECHANIC.toString(), id, HttpStatus.NOT_FOUND));
    }

    /**
     * Delete Mechanic by Id
     * @param id identifier of Mechanic to be deleted
     * @return deleted Mechanic
     * @throws NotFoundException if no Mechanic with matching id found
     */
    @Override
    public Mechanic deleteEmployee(Long id) {
        LOGGER.info("Delete Mechanic with id {}", id);
        Mechanic mechanic = getEmployee(id);
        mechanicRepository.delete(mechanic);
        return mechanic;
    }

    /**
     * Update Mechanic
     * @param id identifier of Mechanic to be updated
     * @param mechanic Mechanic object with updated fields
     * @return updated Mechanic
     * @throws NotFoundException if no Mechanic with matching id found
     */
    @Override
    @Transactional
    public Mechanic editEmployee(Long id, Mechanic mechanic) {
        LOGGER.info("Update Mechanic with id {}", id);
        Mechanic mechanicToEdit = getEmployee(id);
        mechanicToEdit.setSsn(mechanic.getSsn());
        mechanicToEdit.setFirstName(mechanic.getFirstName());
        mechanicToEdit.setMiddleInitial(mechanic.getMiddleInitial());
        mechanicToEdit.setLastName(mechanic.getLastName());
        mechanicToEdit.setGender(mechanic.getGender());
        mechanicToEdit.setDateOfBirth(mechanic.getDateOfBirth());
        mechanicToEdit.setPhoneNumber(mechanic.getPhoneNumber());
        mechanicToEdit.setEmail(mechanic.getEmail());
        mechanicToEdit.setWorkLocation(mechanic.getWorkLocation());
        mechanicToEdit.setSalary(mechanic.getSalary());
        mechanicToEdit.setDateStarted(mechanic.getDateStarted());
        mechanicToEdit.setAddress(mechanic.getAddress());
        mechanicToEdit.setHoursWorked(mechanic.getHoursWorked());
        mechanicToEdit.setManager(mechanic.getManager());
        mechanicToEdit.setDepartment(mechanic.getDepartment());
        mechanicToEdit.setTickets(mechanic.getTickets());
        mechanicToEdit.setComments(mechanic.getComments());
        return mechanicToEdit;
    }

    /**
     * Assign Service Ticket to Mechanic
     * @param mechanicId identifier of Mechanic to be updated
     * @param ticketId identifier of Service Ticket to be assigned to Mechanic
     * @return updated Mechanic entity
     * @throws NotFoundException if no Mechanic or ServiceTicket with matching ticketId/mechanicId were found
     * @throws AlreadyAssignedException if ServiceTicket has already been assigned to a Mechanic
     */
    @Transactional
    public Mechanic assignTicket(Long mechanicId, Long ticketId) {
        LOGGER.info("Assign Service Ticket with id {} to Mechanic with id {}", ticketId, mechanicId);
        Mechanic mechanic = getEmployee(mechanicId);
        ServiceTicket ticket = ticketService.getServiceTicket(ticketId);
        if (Objects.nonNull(ticket.getMechanic())) {
            throw new AlreadyAssignedException(
                    Entity.SERVICE_TICKET.toString(),
                    ticketId,
                    Entity.MECHANIC.toString(),
                    ticket.getMechanic().getId(),
                    HttpStatus.BAD_REQUEST
            );
        }
        mechanic.addServiceTicket(ticket);
        ticket.setMechanic(mechanic);
        return mechanic;
    }

    /**
     * Remove assigned ServiceTicket to Mechanic
     * @param mechanicId identifier of Mechanic to be updated
     * @param ticketId identifier of ServiceTicket to be removed from Mechanic
     * @return updated Mechanic entity
     * @throws NotFoundException if no Mechanic or ServiceTicket with matching ticketId/mechanicId were found
     */
    @Transactional
    public Mechanic removeTicket(Long mechanicId, Long ticketId) {
        LOGGER.info("Remove Service Ticket with id {} from Mechanic with id {}", ticketId, mechanicId);
        Mechanic mechanic = getEmployee(mechanicId);
        ServiceTicket ticket = ticketService.getServiceTicket(ticketId);
        mechanic.removeServiceTicket(ticket);
        return mechanic;
    }
}
