package edu.towson.cosc457.CarDealership.service;

import edu.towson.cosc457.CarDealership.exceptions.AlreadyAssignedException;
import edu.towson.cosc457.CarDealership.exceptions.NotFoundException;
import edu.towson.cosc457.CarDealership.misc.Entity;
import edu.towson.cosc457.CarDealership.model.Department;
import edu.towson.cosc457.CarDealership.model.Location;
import edu.towson.cosc457.CarDealership.model.Lot;
import edu.towson.cosc457.CarDealership.model.Mechanic;
import edu.towson.cosc457.CarDealership.repository.LocationRepository;
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
public class LocationService {
    private final LocationRepository locationRepository;
    private final LotService lotService;
    private final DepartmentService departmentService;
    private final MechanicService mechanicService;
    private static final Logger LOGGER = LoggerFactory.getLogger(LocationService.class);

    public Location addLocation(Location location) {
        LOGGER.info("Create new Location in the database");
        return locationRepository.save(location);
    }

    public List<Location> getLocations() {
        LOGGER.info("Get all Locations");
        return StreamSupport
                .stream(locationRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Location getLocation(Long id) {
        LOGGER.info("Get Location with id {}", id);
        return locationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Entity.LOCATION.toString(), id, HttpStatus.NOT_FOUND));
    }

    public Location deleteLocation(Long id) {
        LOGGER.info("Delete Location with id {}", id);
        Location location = getLocation(id);
        locationRepository.delete(location);
        return location;
    }

    @Transactional
    public Location editLocation(Long id, Location location) {
        LOGGER.info("Update Location with id {}", id);
        Location locationToEdit = getLocation(id);
        locationToEdit.setName(location.getName());
        locationToEdit.setAddress(location.getAddress());
        locationToEdit.setSiteManager(location.getSiteManager());
        locationToEdit.setLots(location.getLots());
        locationToEdit.setDepartments(location.getDepartments());
        locationToEdit.setMechanics(location.getMechanics());
        locationToEdit.setSalesAssociates(location.getSalesAssociates());
        return locationToEdit;
    }

    @Transactional
    public Location addLotToLocation(Long locationId, Long lotId) {
        LOGGER.info("Add Lot with id {} to Location with id {}", lotId, locationId);
        Location location = getLocation(locationId);
        Lot lot = lotService.getLot(lotId);
        if (Objects.nonNull(lot.getLocation())) {
            throw new AlreadyAssignedException(
                    Entity.LOT.toString(),
                    lotId,
                    Entity.LOCATION.toString(),
                    lot.getLocation().getId(),
                    HttpStatus.BAD_REQUEST
            );
        }
        location.addLot(lot);
        lot.setLocation(location);
        return location;
    }

    @Transactional
    public Location removeLotFromLocation(Long locationId, Long lotId) {
        LOGGER.info("Delete Lot with id {} from Location with id {}", lotId, locationId);
        Location location = getLocation(locationId);
        Lot lot = lotService.getLot(lotId);
        location.removeLot(lot);
        return location;
    }

    @Transactional
    public Location addDepartmentToLocation(Long locationId, Long departmentId) {
        LOGGER.info("Add Department with id {} to Location with id {}", departmentId, locationId);
        Location location = getLocation(locationId);
        Department department = departmentService.getDepartment(departmentId);
        if (Objects.nonNull(department.getLocation())) {
            throw new AlreadyAssignedException(
                    Entity.LOT.toString(),
                    departmentId,
                    Entity.LOCATION.toString(),
                    department.getLocation().getId(),
                    HttpStatus.BAD_REQUEST
            );
        }
        location.addDepartment(department);
        department.setLocation(location);
        return location;
    }

    @Transactional
    public Location removeDepartmentFromLocation(Long locationId, Long departmentId) {
        LOGGER.info("Delete Department with id {} from Location with id {}", departmentId, locationId);
        Location location = getLocation(locationId);
        Department department = departmentService.getDepartment(departmentId);
        location.removeDepartment(department);
        return location;
    }

    @Transactional
    public Location assignMechanic(Long locationId, Long mechanicId) {
        LOGGER.info("Delete Mechanic with id {} to Location with id {}", mechanicId, locationId);
        Location location = getLocation(locationId);
        Mechanic mechanic = mechanicService.getEmployee(mechanicId);
        if (Objects.nonNull(mechanic.getWorkLocation())) {
            throw new AlreadyAssignedException(
                    Entity.MECHANIC.toString(),
                    mechanicId,
                    Entity.LOCATION.toString(),
                    mechanic.getWorkLocation().getId(),
                    HttpStatus.BAD_REQUEST
            );
        }
        location.assignMechanic(mechanic);
        mechanic.setWorkLocation(location);
        return location;
    }

    @Transactional
    public Location removeMechanic(Long locationId, Long mechanicId) {
        LOGGER.info("Delete Mechanic with id {} from Location with id {}", mechanicId, locationId);
        Location location = getLocation(locationId);
        Mechanic mechanic = mechanicService.getEmployee(mechanicId);
        location.removeMechanic(mechanic);
        return location;
    }
}
