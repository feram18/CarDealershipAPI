package edu.towson.cosc457.CarDealership.controller;

import edu.towson.cosc457.CarDealership.model.Location;
import edu.towson.cosc457.CarDealership.model.dto.DepartmentDto;
import edu.towson.cosc457.CarDealership.model.dto.LocationDto;
import edu.towson.cosc457.CarDealership.model.dto.LotDto;
import edu.towson.cosc457.CarDealership.model.dto.MechanicDto;
import edu.towson.cosc457.CarDealership.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/locations")
public class LocationController {
    private final LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping
    public ResponseEntity<LocationDto> addLocation(@RequestBody final LocationDto locationDto) {
        Location location = locationService.addLocation(Location.from(locationDto));
        return new ResponseEntity<>(LocationDto.from(location), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<LocationDto>> getLocations() {
        List<Location> locations = locationService.getLocations();
        List<LocationDto> locationsDto = locations.stream().map(LocationDto::from).collect(Collectors.toList());
        return new ResponseEntity<>(locationsDto, HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<LocationDto> getLocation(@PathVariable final Long id) {
        Location location = locationService.getLocation(id);
        return new ResponseEntity<>(LocationDto.from(location), HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<LocationDto> deleteLocation(@PathVariable final Long id) {
        Location location = locationService.deleteLocation(id);
        return new ResponseEntity<>(LocationDto.from(location), HttpStatus.OK);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<LocationDto> editLocation(@PathVariable final Long id,
                                                    @RequestBody final LocationDto locationDto) {
        Location location = locationService.editLocation(id, Location.from(locationDto));
        return new ResponseEntity<>(LocationDto.from(location), HttpStatus.OK);
    }

    @GetMapping(value = "/{locationId}/lots")
    public ResponseEntity<List<LotDto>> getLots(@PathVariable final Long id) {
        Location location = locationService.getLocation(id);
        List<LotDto> lotsDto = location.getLots().stream().map(LotDto::from).collect(Collectors.toList());
        return new ResponseEntity<>(lotsDto, HttpStatus.OK);
    }

    @PostMapping(value = "{locationId}/lots/{lotId}/add")
    public ResponseEntity<LocationDto> addLotToLocation(@PathVariable final Long locationId,
                                                        @PathVariable final Long lotId) {
        Location location = locationService.addLotToLocation(locationId, lotId);
        return new ResponseEntity<>(LocationDto.from(location), HttpStatus.OK);
    }

    @DeleteMapping(value = "{locationId}/lots/{lotId}/remove")
    public ResponseEntity<LocationDto> removeLotFromLocation(@PathVariable final Long locationId,
                                                             @PathVariable final Long lotId) {
        Location location = locationService.removeLotFromLocation(locationId, lotId);
        return new ResponseEntity<>(LocationDto.from(location), HttpStatus.OK);
    }

    @GetMapping(value = "/{locationId}/departments")
    public ResponseEntity<List<DepartmentDto>> getDepartments(@PathVariable final Long id) {
        Location location = locationService.getLocation(id);
        List<DepartmentDto> departmentsDto = location.getDepartments()
                .stream().map(DepartmentDto::from).collect(Collectors.toList());
        return new ResponseEntity<>(departmentsDto, HttpStatus.OK);
    }

    @PostMapping(value = "{locationId}/departments/{departmentId}/add")
    public ResponseEntity<LocationDto> addDepartmentToLocation(@PathVariable final Long locationId,
                                                               @PathVariable final Long departmentId) {
        Location location = locationService.addDepartmentToLocation(locationId, departmentId);
        return new ResponseEntity<>(LocationDto.from(location), HttpStatus.OK);
    }

    @DeleteMapping(value = "{locationId}/departments/{departmentId}/remove")
    public ResponseEntity<LocationDto> removeDepartmentFromLocation(@PathVariable final Long locationId,
                                                                    @PathVariable final Long departmentId) {
        Location location = locationService.removeDepartmentFromLocation(locationId, departmentId);
        return new ResponseEntity<>(LocationDto.from(location), HttpStatus.OK);
    }

    @GetMapping(value = "/{locationId}/mechanics")
    public ResponseEntity<List<MechanicDto>> getMechanics(@PathVariable final Long id) {
        Location location = locationService.getLocation(id);
        List<MechanicDto> mechanicsDto = location.getMechanics()
                .stream().map(MechanicDto::from).collect(Collectors.toList());
        return new ResponseEntity<>(mechanicsDto, HttpStatus.OK);
    }

    @PostMapping(value = "{locationId}/mechanics/{mechanicId}/add")
    public ResponseEntity<LocationDto> assignMechanic(@PathVariable final Long locationId,
                                                      @PathVariable final Long mechanicId) {
        Location location = locationService.assignMechanic(locationId, mechanicId);
        return new ResponseEntity<>(LocationDto.from(location), HttpStatus.OK);
    }

    @DeleteMapping(value = "{locationId}/mechanics/{mechanicId}/remove")
    public ResponseEntity<LocationDto> removeMechanic(@PathVariable final Long locationId,
                                                      @PathVariable final Long mechanicId) {
        Location location = locationService.removeMechanic(locationId, mechanicId);
        return new ResponseEntity<>(LocationDto.from(location), HttpStatus.OK);
    }
}
