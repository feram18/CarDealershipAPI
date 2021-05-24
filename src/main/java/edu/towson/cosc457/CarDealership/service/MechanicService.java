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

    @Override
    public Mechanic addEmployee(Mechanic mechanic) {
        LOGGER.info("Create new Mechanic in the database");
        return mechanicRepository.save(mechanic);
    }

    @Override
    public List<Mechanic> getEmployees() {
        LOGGER.info("Get all Mechanics");
        return StreamSupport
                .stream(mechanicRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Mechanic getEmployee(Long id) {
        LOGGER.info("Get Mechanic with id {}", id);
        return mechanicRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Entity.MECHANIC.toString(), id, HttpStatus.NOT_FOUND));
    }

    @Override
    public Mechanic deleteEmployee(Long id) {
        LOGGER.info("Delete Mechanic with id {}", id);
        Mechanic mechanic = getEmployee(id);
        mechanicRepository.delete(mechanic);
        return mechanic;
    }

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

    @Transactional
    public Mechanic removeTicket(Long mechanicId, Long ticketId) {
        LOGGER.info("Remove Service Ticket with id {} from Mechanic with id {}", ticketId, mechanicId);
        Mechanic mechanic = getEmployee(mechanicId);
        ServiceTicket ticket = ticketService.getServiceTicket(ticketId);
        mechanic.removeServiceTicket(ticket);
        return mechanic;
    }
}
