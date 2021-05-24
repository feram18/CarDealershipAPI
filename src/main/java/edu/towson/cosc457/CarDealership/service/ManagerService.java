package edu.towson.cosc457.CarDealership.service;

import edu.towson.cosc457.CarDealership.exceptions.AlreadyAssignedException;
import edu.towson.cosc457.CarDealership.exceptions.NotFoundException;
import edu.towson.cosc457.CarDealership.misc.Entity;
import edu.towson.cosc457.CarDealership.model.Manager;
import edu.towson.cosc457.CarDealership.model.Mechanic;
import edu.towson.cosc457.CarDealership.repository.ManagerRepository;
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
public class ManagerService implements EmployeeService<Manager> {
    private final ManagerRepository managerRepository;
    private final MechanicService mechanicService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ManagerService.class);

    /**
     * Create a new Manager in the database
     * @param manager Manager object to be added to database
     * @return Manager saved on repository
     */
    @Override
    public Manager addEmployee(Manager manager) {
        LOGGER.info("Create new Manager in the database");
        return managerRepository.save(manager);
    }

    /**
     * Get All Managers
     * @return List of Managers
     */
    @Override
    public List<Manager> getEmployees() {
        LOGGER.info("Get all Managers");
        return StreamSupport
                .stream(managerRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    /**
     * Get Manager by Id
     * @param id identifier of Manager to be fetched
     * @return fetched Manager
     * @throws NotFoundException if no Manager with matching id found
     */
    @Override
    public Manager getEmployee(Long id) {
        LOGGER.info("Get Manager with id {}", id);
        return managerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Entity.MANAGER.toString(), id, HttpStatus.NOT_FOUND));
    }

    /**
     * Delete Manager by Id
     * @param id identifier of Manager to be deleted
     * @return deleted Manager
     * @throws NotFoundException if no Manager with matching id found
     */
    @Override
    public Manager deleteEmployee(Long id) {
        LOGGER.info("Delete Manager with id {}", id);
        Manager manager = getEmployee(id);
        managerRepository.delete(manager);
        return manager;
    }

    /**
     * Update Manager
     * @param id identifier of Manager to be updated
     * @param manager Manager object with updated fields
     * @return updated Manager
     * @throws NotFoundException if no Manager with matching id found
     */
    @Override
    @Transactional
    public Manager editEmployee(Long id, Manager manager) {
        LOGGER.info("Update Manager with id {}", id);
        Manager managerToEdit = getEmployee(id);
        managerToEdit.setSsn(manager.getSsn());
        managerToEdit.setFirstName(manager.getFirstName());
        managerToEdit.setMiddleInitial(manager.getMiddleInitial());
        managerToEdit.setLastName(manager.getLastName());
        managerToEdit.setGender(manager.getGender());
        managerToEdit.setDateOfBirth(manager.getDateOfBirth());
        managerToEdit.setPhoneNumber(manager.getPhoneNumber());
        managerToEdit.setEmail(manager.getEmail());
        managerToEdit.setWorkLocation(manager.getWorkLocation());
        managerToEdit.setSalary(manager.getSalary());
        managerToEdit.setDateStarted(manager.getDateStarted());
        managerToEdit.setAddress(manager.getAddress());
        managerToEdit.setHoursWorked(manager.getHoursWorked());
        managerToEdit.setSiteManager(manager.getSiteManager());
        managerToEdit.setDepartment(manager.getDepartment());
        managerToEdit.setMechanics(manager.getMechanics());
        managerToEdit.setSalesAssociates(manager.getSalesAssociates());
        return managerToEdit;
    }

    /**
     * Assign Mechanic to Manager
     * @param managerId identifier of Manager to be updated
     * @param mechanicId identifier of Mechanic to be assigned to Manager
     * @return updated Manager entity
     * @throws NotFoundException if no Manager or Mechanic with matching managerId/mechanicId were found
     * @throws AlreadyAssignedException if Mechanic is already assigned to a Manager
     */
    @Transactional
    public Manager assignToManager(Long managerId, Long mechanicId) {
        LOGGER.info("Assign Mechanic with id {} to Manager with id {}", mechanicId, managerId);
        Manager manager = getEmployee(managerId);
        Mechanic mechanic = mechanicService.getEmployee(mechanicId);
        if (Objects.nonNull(mechanic.getManager())) {
            throw new AlreadyAssignedException(
                    Entity.MECHANIC.toString(),
                    mechanicId,
                    Entity.MANAGER.toString(),
                    mechanic.getManager().getId(),
                    HttpStatus.BAD_REQUEST
            );
        }
        manager.assignMechanics(mechanic);
        mechanic.setManager(manager);
        return manager;
    }

    /**
     * Remove assigned Mechanic from Manager
     * @param managerId identifier of Manager to be updated
     * @param mechanicId identifier of Mechanic to be removed from Manager
     * @return updated Manager entity
     * @throws NotFoundException if no Manager or Mechanic with matching managerId/mechanicId were found
     */
    @Transactional
    public Manager removeFromManager(Long managerId, Long mechanicId) {
        LOGGER.info("Remove Mechanic with id {} from Manager with id {}", mechanicId, managerId);
        Manager manager = getEmployee(managerId);
        Mechanic mechanic = mechanicService.getEmployee(mechanicId);
        manager.removeMechanics(mechanic);
        return manager;
    }
}
