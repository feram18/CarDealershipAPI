package edu.towson.cosc457.CarDealership.controller;

import edu.towson.cosc457.CarDealership.mapper.DepartmentMapper;
import edu.towson.cosc457.CarDealership.mapper.EmployeeMapper;
import edu.towson.cosc457.CarDealership.mapper.LocationMapper;
import edu.towson.cosc457.CarDealership.mapper.LotMapper;
import edu.towson.cosc457.CarDealership.model.Location;
import edu.towson.cosc457.CarDealership.model.dto.*;
import edu.towson.cosc457.CarDealership.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/locations")
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;
    private final LocationMapper locationMapper;
    private final LotMapper lotMapper;
    private final DepartmentMapper departmentMapper;
    private final EmployeeMapper employeeMapper;

    @PostMapping
    public ResponseEntity<LocationDto> addLocation(@RequestBody final LocationDto locationDto) {
        Location location = locationService.addLocation(locationMapper.fromDto(locationDto));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(locationMapper.toDto(location));
    }

    @GetMapping
    public ResponseEntity<List<LocationDto>> getLocations() {
        List<Location> locations = locationService.getLocations();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(locations.stream().map(locationMapper::toDto).collect(Collectors.toList()));
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<LocationDto> getLocation(@PathVariable final Long id) {
        Location location = locationService.getLocation(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(locationMapper.toDto(location));
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<LocationDto> deleteLocation(@PathVariable final Long id) {
        Location location = locationService.deleteLocation(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(locationMapper.toDto(location));
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<LocationDto> editLocation(@PathVariable final Long id,
                                                    @RequestBody final LocationDto locationDto) {
        Location location = locationService.editLocation(id, locationMapper.fromDto(locationDto));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(locationMapper.toDto(location));
    }

    @GetMapping(value = "/{id}/lots")
    public ResponseEntity<List<LotDto>> getLots(@PathVariable final Long id) {
        Location location = locationService.getLocation(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(location.getLots().stream().map(lotMapper::toDto).collect(Collectors.toList()));
    }

    @PostMapping(value = "{locationId}/lots/{lotId}/add")
    public ResponseEntity<LocationDto> addLotToLocation(@PathVariable final Long locationId,
                                                        @PathVariable final Long lotId) {
        Location location = locationService.addLotToLocation(locationId, lotId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(locationMapper.toDto(location));
    }

    @DeleteMapping(value = "{locationId}/lots/{lotId}/remove")
    public ResponseEntity<LocationDto> removeLotFromLocation(@PathVariable final Long locationId,
                                                             @PathVariable final Long lotId) {
        Location location = locationService.removeLotFromLocation(locationId, lotId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(locationMapper.toDto(location));
    }

    @GetMapping(value = "/{id}/departments")
    public ResponseEntity<List<DepartmentDto>> getDepartments(@PathVariable final Long id) {
        Location location = locationService.getLocation(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(location.getDepartments().stream().map(departmentMapper::toDto).collect(Collectors.toList()));
    }

    @PostMapping(value = "{locationId}/departments/{departmentId}/add")
    public ResponseEntity<LocationDto> addDepartmentToLocation(@PathVariable final Long locationId,
                                                               @PathVariable final Long departmentId) {
        Location location = locationService.addDepartmentToLocation(locationId, departmentId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(locationMapper.toDto(location));
    }

    @DeleteMapping(value = "{locationId}/departments/{departmentId}/remove")
    public ResponseEntity<LocationDto> removeDepartmentFromLocation(@PathVariable final Long locationId,
                                                                    @PathVariable final Long departmentId) {
        Location location = locationService.removeDepartmentFromLocation(locationId, departmentId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(locationMapper.toDto(location));
    }

    @GetMapping(value = "/{id}/mechanics")
    public ResponseEntity<List<EmployeeDto>> getMechanics(@PathVariable final Long id) {
        Location location = locationService.getLocation(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(location.getMechanics().stream().map(employeeMapper::toDto).collect(Collectors.toList()));
    }

    @PostMapping(value = "{locationId}/mechanics/{mechanicId}/add")
    public ResponseEntity<LocationDto> assignMechanic(@PathVariable final Long locationId,
                                                      @PathVariable final Long mechanicId) {
        Location location = locationService.assignMechanic(locationId, mechanicId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(locationMapper.toDto(location));
    }

    @DeleteMapping(value = "{locationId}/mechanics/{mechanicId}/remove")
    public ResponseEntity<LocationDto> removeMechanic(@PathVariable final Long locationId,
                                                      @PathVariable final Long mechanicId) {
        Location location = locationService.removeMechanic(locationId, mechanicId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(locationMapper.toDto(location));
    }
}
