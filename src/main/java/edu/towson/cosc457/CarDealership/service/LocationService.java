package edu.towson.cosc457.CarDealership.service;

import edu.towson.cosc457.CarDealership.exceptions.AlreadyAssignedException;
import edu.towson.cosc457.CarDealership.exceptions.NotFoundException;
import edu.towson.cosc457.CarDealership.misc.Entity;
import edu.towson.cosc457.CarDealership.model.Department;
import edu.towson.cosc457.CarDealership.model.Location;
import edu.towson.cosc457.CarDealership.model.Lot;
import edu.towson.cosc457.CarDealership.model.Mechanic;
import edu.towson.cosc457.CarDealership.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class LocationService {
    private final LocationRepository locationRepository;
    private final LotService lotService;
    private final DepartmentService departmentService;
    private final MechanicService mechanicService;

    @Autowired
    public LocationService(LocationRepository locationRepository, LotService lotService, DepartmentService departmentService, MechanicService mechanicService) {
        this.locationRepository = locationRepository;
        this.lotService = lotService;
        this.departmentService = departmentService;
        this.mechanicService = mechanicService;
    }

    public Location addLocation(Location location) {
        return locationRepository.save(location);
    }

    public List<Location> getLocations() {
        return StreamSupport
                .stream(locationRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Location getLocation(Long id) {
        return locationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Entity.LOCATION.toString(), id));
    }

    public Location deleteLocation(Long id) {
        Location location = getLocation(id);
        locationRepository.delete(location);
        return location;
    }

    @Transactional
    public Location editLocation(Long id, Location location) {
        Location locationToEdit = getLocation(id);
        locationToEdit.setId(location.getId());
        locationToEdit.setName(location.getName());
        locationToEdit.setAddress(location.getAddress());
        locationToEdit.setSiteManager(location.getSiteManager());
        return locationToEdit;
    }

    @Transactional
    public Location addLotToLocation(Long locationId, Long lotId) {
        Location location = getLocation(locationId);
        Lot lot = lotService.getLot(lotId);
        if (Objects.nonNull(lot.getLocation())) {
            throw new AlreadyAssignedException(
                    Entity.LOT.toString(),
                    lotId,
                    Entity.LOCATION.toString(),
                    lot.getLocation().getId()
            );
        }
        location.addLot(lot);
        lot.setLocation(location);
        return location;
    }

    @Transactional
    public Location removeLotFromLocation(Long locationId, Long lotId) {
        Location location = getLocation(locationId);
        Lot lot = lotService.getLot(lotId);
        location.removeLot(lot);
        return location;
    }

    @Transactional
    public Location addDepartment(Long locationId, Long departmentId) {
        Location location = getLocation(locationId);
        Department department = departmentService.getDepartment(departmentId);
        if (Objects.nonNull(department.getLocation())) {
            throw new AlreadyAssignedException(
                    Entity.DEPARTMENT.toString(),
                    departmentId,
                    Entity.LOCATION.toString(),
                    department.getLocation().getId()
            );
        }
        location.addDepartment(department);
        department.setLocation(location);
        return location;
    }

    @Transactional
    public Location removeDepartment(Long locationId, Long departmentId) {
        Location location = getLocation(locationId);
        Department department = departmentService.getDepartment(departmentId);
        location.removeDepartment(department);
        return location;
    }

    @Transactional
    public Location assignMechanic(Long locationId, Long mechanicId) {
        Location location = getLocation(locationId);
        Mechanic mechanic = mechanicService.getEmployee(mechanicId);
        if (Objects.nonNull(mechanic.getWorkLocation())) {
            throw new AlreadyAssignedException(
                    Entity.MECHANIC.toString(),
                    mechanicId,
                    Entity.LOCATION.toString(),
                    mechanic.getWorkLocation().getId()
            );
        }
        location.assignMechanic(mechanic);
        mechanic.setWorkLocation(location);
        return location;
    }

    @Transactional
    public Location removeMechanic(Long locationId, Long mechanicId) {
        Location location = getLocation(locationId);
        Mechanic mechanic = mechanicService.getEmployee(mechanicId);
        location.removeMechanic(mechanic);
        return location;
    }
}
