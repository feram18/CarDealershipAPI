package edu.towson.cosc457.CarDealership.service;

import edu.towson.cosc457.CarDealership.exceptions.AlreadyAssignedException;
import edu.towson.cosc457.CarDealership.exceptions.NotFoundException;
import edu.towson.cosc457.CarDealership.misc.Entity;
import edu.towson.cosc457.CarDealership.model.Employee;
import edu.towson.cosc457.CarDealership.model.Manager;
import edu.towson.cosc457.CarDealership.model.SiteManager;
import edu.towson.cosc457.CarDealership.repository.SiteManagerRepository;
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
public class SiteManagerService implements EmployeeService<SiteManager> {
    private final SiteManagerRepository siteManagerRepository;
    private final ManagerService managerService;

    @Override
    public SiteManager addEmployee(SiteManager siteManager) {
        return siteManagerRepository.save(siteManager);
    }

    @Override
    public List<SiteManager> getEmployees() {
        return StreamSupport
                .stream(siteManagerRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public SiteManager getEmployee(Long id) {
        return siteManagerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Entity.SITE_MANAGER.toString(), id));
    }


    @Override
    public SiteManager deleteEmployee(Long id) {
        SiteManager siteManager = getEmployee(id);
        siteManagerRepository.delete(siteManager);
        return siteManager;
    }

    @Override
    @Transactional
    public SiteManager editEmployee(Long id, SiteManager siteManager) {
        SiteManager siteManagerToEdit = getEmployee(id);
        siteManagerToEdit.setSsn(siteManager.getSsn());
        siteManagerToEdit.setFirstName(siteManager.getFirstName());
        siteManagerToEdit.setMiddleInitial(siteManager.getMiddleInitial());
        siteManagerToEdit.setLastName(siteManager.getLastName());
        siteManagerToEdit.setGender(siteManager.getGender());
        siteManagerToEdit.setDateOfBirth(siteManager.getDateOfBirth());
        siteManagerToEdit.setPhoneNumber(siteManager.getPhoneNumber());
        siteManagerToEdit.setEmail(siteManager.getEmail());
        siteManagerToEdit.setWorkLocation(siteManager.getWorkLocation());
        siteManagerToEdit.setSalary(siteManager.getSalary());
        siteManagerToEdit.setDateStarted(siteManager.getDateStarted());
        siteManagerToEdit.setAddress(siteManager.getAddress());
        siteManagerToEdit.setHoursWorked(siteManager.getHoursWorked());
        siteManagerToEdit.setLocation(siteManager.getLocation());
        siteManagerToEdit.setManagers(siteManager.getManagers());
        return siteManagerToEdit;
    }

    @Transactional
    public SiteManager assignToManager(Long siteManagerId, Long managerId) {
        SiteManager siteManager = getEmployee(siteManagerId);
        Manager manager = managerService.getEmployee(managerId);
        if (Objects.nonNull(manager.getSiteManager())) {
            throw new AlreadyAssignedException(
                    Entity.MANAGER.toString(),
                    managerId,
                    Entity.SITE_MANAGER.toString(),
                    manager.getSiteManager().getId()
            );
        }
        siteManager.assignManager(manager);
        manager.setSiteManager(siteManager);
        return siteManager;
    }

    @Transactional
    public SiteManager removeFromManager(Long siteManagerId, Long managerId) {
        SiteManager siteManager = getEmployee(siteManagerId);
        Manager manager = managerService.getEmployee(managerId);
        siteManager.removeManager(manager);
        return siteManager;
    }
}
