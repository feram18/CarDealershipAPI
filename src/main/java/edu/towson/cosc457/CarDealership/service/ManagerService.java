package edu.towson.cosc457.CarDealership.service;

import edu.towson.cosc457.CarDealership.exceptions.AlreadyAssignedException;
import edu.towson.cosc457.CarDealership.exceptions.NotFoundException;
import edu.towson.cosc457.CarDealership.misc.Entity;
import edu.towson.cosc457.CarDealership.model.Employee;
import edu.towson.cosc457.CarDealership.model.Manager;
import edu.towson.cosc457.CarDealership.model.Mechanic;
import edu.towson.cosc457.CarDealership.repository.ManagerRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public Manager addEmployee(Manager manager) {
        return managerRepository.save(manager);
    }

    @Override
    public List<Manager> getEmployees() {
        return StreamSupport
                .stream(managerRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Manager getEmployee(Long id) {
        return managerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Entity.MANAGER.toString(), id));
    }

    @Override
    public Manager deleteEmployee(Long id) {
        Manager manager = getEmployee(id);
        managerRepository.delete(manager);
        return manager;
    }

    @Override
    @Transactional
    public Manager editEmployee(Long id, Manager manager) {
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

    @Transactional
    public Manager assignToManager(Long managerId, Long mechanicId) {
        Manager manager = getEmployee(managerId);
        Mechanic mechanic = mechanicService.getEmployee(mechanicId);
        if (Objects.nonNull(mechanic.getManager())) {
            throw new AlreadyAssignedException(
                    Entity.MECHANIC.toString(),
                    mechanicId,
                    Entity.MANAGER.toString(),
                    mechanic.getManager().getId()
            );
        }
        manager.assignMechanics(mechanic);
        mechanic.setManager(manager);
        return manager;
    }

    @Transactional
    public Manager removeFromManager(Long managerId, Long mechanicId) {
        Manager manager = getEmployee(managerId);
        Mechanic mechanic = mechanicService.getEmployee(mechanicId);
        manager.removeMechanics(mechanic);
        return manager;
    }
}
