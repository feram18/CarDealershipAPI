package edu.towson.cosc457.CarDealership.service;

import edu.towson.cosc457.CarDealership.exceptions.AlreadyAssignedException;
import edu.towson.cosc457.CarDealership.exceptions.NotFoundException;
import edu.towson.cosc457.CarDealership.misc.Entity;
import edu.towson.cosc457.CarDealership.model.Manager;
import edu.towson.cosc457.CarDealership.model.SiteManager;
import edu.towson.cosc457.CarDealership.repository.SiteManagerRepository;
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
public class SiteManagerService implements EmployeeService<SiteManager> {
    private final SiteManagerRepository siteManagerRepository;
    private final ManagerService managerService;
    private static final Logger LOGGER = LoggerFactory.getLogger(SiteManagerService.class);

    @Override
    public SiteManager addEmployee(SiteManager siteManager) {
        LOGGER.info("Create new Site Manager in the database");
        return siteManagerRepository.save(siteManager);
    }

    @Override
    public List<SiteManager> getEmployees() {
        LOGGER.info("Get all Site Managers");
        return StreamSupport
                .stream(siteManagerRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public SiteManager getEmployee(Long id) {
        LOGGER.info("Get Site Manager with id {}", id);
        return siteManagerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Entity.SITE_MANAGER.toString(), id, HttpStatus.NOT_FOUND));
    }


    @Override
    public SiteManager deleteEmployee(Long id) {
        LOGGER.info("Delete Site Manager with id {}", id);
        SiteManager siteManager = getEmployee(id);
        siteManagerRepository.delete(siteManager);
        return siteManager;
    }

    @Override
    @Transactional
    public SiteManager editEmployee(Long id, SiteManager siteManager) {
        LOGGER.info("Update Site Manager with id {}", id);
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
        siteManagerToEdit.setManagedLocation(siteManager.getManagedLocation());
        siteManagerToEdit.setManagers(siteManager.getManagers());
        return siteManagerToEdit;
    }

    @Transactional
    public SiteManager assignToManager(Long siteManagerId, Long managerId) {
        LOGGER.info("Assign Manager with id {} to Site Manager with id {}", managerId, siteManagerId);
        SiteManager siteManager = getEmployee(siteManagerId);
        Manager manager = managerService.getEmployee(managerId);
        if (Objects.nonNull(manager.getSiteManager())) {
            throw new AlreadyAssignedException(
                    Entity.MANAGER.toString(),
                    managerId,
                    Entity.SITE_MANAGER.toString(),
                    manager.getSiteManager().getId(),
                    HttpStatus.BAD_REQUEST
            );
        }
        siteManager.assignManager(manager);
        manager.setSiteManager(siteManager);
        return siteManager;
    }

    @Transactional
    public SiteManager removeFromManager(Long siteManagerId, Long managerId) {
        LOGGER.info("Remove Manager with id {} from Site Manager with id {}", managerId, siteManagerId);
        SiteManager siteManager = getEmployee(siteManagerId);
        Manager manager = managerService.getEmployee(managerId);
        siteManager.removeManager(manager);
        return siteManager;
    }
}
