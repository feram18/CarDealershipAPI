package edu.towson.cosc457.CarDealership.controller;

import edu.towson.cosc457.CarDealership.mapper.DepartmentMapper;
import edu.towson.cosc457.CarDealership.mapper.EmployeeMapper;
import edu.towson.cosc457.CarDealership.mapper.LocationMapper;
import edu.towson.cosc457.CarDealership.mapper.LotMapper;
import edu.towson.cosc457.CarDealership.model.Location;
import edu.towson.cosc457.CarDealership.model.dto.*;
import edu.towson.cosc457.CarDealership.service.LocationService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(LocationController.class);

    @ApiOperation(value = "Add Location", response = LocationDto.class)
    @PostMapping
    public ResponseEntity<LocationDto> addLocation(@RequestBody final LocationDto locationDto) {
        LOGGER.info("POST /api/v1/locations/");
        Location location = locationService.addLocation(locationMapper.fromDto(locationDto));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(locationMapper.toDto(location));
    }

    @ApiOperation(value = "Fetch All Locations", response = Iterable.class)
    @GetMapping
    public ResponseEntity<List<LocationDto>> getLocations() {
        LOGGER.info("GET /api/v1/locations/");
        List<Location> locations = locationService.getLocations();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(locations.stream().map(locationMapper::toDto).collect(Collectors.toList()));
    }

    @ApiOperation(value = "Fetch Location by Id", response = LocationDto.class)
    @GetMapping(value = "{id}")
    public ResponseEntity<LocationDto> getLocation(@PathVariable final Long id) {
        LOGGER.info("GET /api/v1/locations/{}", id);
        Location location = locationService.getLocation(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(locationMapper.toDto(location));
    }

    @ApiOperation(value = "Delete Location", response = LocationDto.class)
    @DeleteMapping(value = "{id}")
    public ResponseEntity<LocationDto> deleteLocation(@PathVariable final Long id) {
        LOGGER.info("DELETE /api/v1/locations/{}", id);
        Location location = locationService.deleteLocation(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(locationMapper.toDto(location));
    }

    @ApiOperation(value = "Update Location", response = LocationDto.class)
    @PutMapping(value = "{id}")
    public ResponseEntity<LocationDto> editLocation(@PathVariable final Long id,
                                                    @RequestBody final LocationDto locationDto) {
        LOGGER.info("PUT /api/v1/locations/{}", id);
        Location location = locationService.editLocation(id, locationMapper.fromDto(locationDto));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(locationMapper.toDto(location));
    }

    @ApiOperation(value = "Fetch All Lots in Location", response = Iterable.class)
    @GetMapping(value = "/{id}/lots")
    public ResponseEntity<List<LotDto>> getLots(@PathVariable final Long id) {
        LOGGER.info("GET /api/v1/locations/{}/lots", id);
        Location location = locationService.getLocation(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(location.getLots().stream().map(lotMapper::toDto).collect(Collectors.toList()));
    }

    @ApiOperation(value = "Add Lot to Location", response = LocationDto.class)
    @PostMapping(value = "{locationId}/lots/{lotId}/add")
    public ResponseEntity<LocationDto> addLotToLocation(@PathVariable final Long locationId,
                                                        @PathVariable final Long lotId) {
        LOGGER.info("POST /api/v1/locations/{}/lots/{}/add", locationId, lotId);
        Location location = locationService.addLotToLocation(locationId, lotId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(locationMapper.toDto(location));
    }

    @ApiOperation(value = "Remove Lot from Location", response = LocationDto.class)
    @DeleteMapping(value = "{locationId}/lots/{lotId}/remove")
    public ResponseEntity<LocationDto> removeLotFromLocation(@PathVariable final Long locationId,
                                                             @PathVariable final Long lotId) {
        LOGGER.info("DELETE /api/v1/locations/{}/lots/{}/remove", locationId, lotId);
        Location location = locationService.removeLotFromLocation(locationId, lotId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(locationMapper.toDto(location));
    }

    @ApiOperation(value = "Fetch All Departments in Location", response = Iterable.class)
    @GetMapping(value = "/{id}/departments")
    public ResponseEntity<List<DepartmentDto>> getDepartments(@PathVariable final Long id) {
        LOGGER.info("GET /api/v1/locations/{}/departments", id);
        Location location = locationService.getLocation(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(location.getDepartments().stream().map(departmentMapper::toDto).collect(Collectors.toList()));
    }

    @ApiOperation(value = "Add Department to Location", response = LocationDto.class)
    @PostMapping(value = "{locationId}/departments/{departmentId}/add")
    public ResponseEntity<LocationDto> addDepartmentToLocation(@PathVariable final Long locationId,
                                                               @PathVariable final Long departmentId) {
        LOGGER.info("POST /api/v1/locations/{}/departments/{}/add", locationId, departmentId);
        Location location = locationService.addDepartmentToLocation(locationId, departmentId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(locationMapper.toDto(location));
    }

    @ApiOperation(value = "Remove Department from Location", response = LocationDto.class)
    @DeleteMapping(value = "{locationId}/departments/{departmentId}/remove")
    public ResponseEntity<LocationDto> removeDepartmentFromLocation(@PathVariable final Long locationId,
                                                                    @PathVariable final Long departmentId) {
        LOGGER.info("DELETE /api/v1/locations/{}/departments/{}/remove", locationId, departmentId);
        Location location = locationService.removeDepartmentFromLocation(locationId, departmentId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(locationMapper.toDto(location));
    }

    @ApiOperation(value = "Fetch All Mechanics in Location", response = Iterable.class)
    @GetMapping(value = "/{id}/mechanics")
    public ResponseEntity<List<EmployeeDto>> getMechanics(@PathVariable final Long id) {
        LOGGER.info("GET /api/v1/locations/{}/mechanics", id);
        Location location = locationService.getLocation(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(location.getMechanics().stream().map(employeeMapper::toDto).collect(Collectors.toList()));
    }

    @ApiOperation(value = "Add Mechanic to Location", response = LocationDto.class)
    @PostMapping(value = "{locationId}/mechanics/{mechanicId}/add")
    public ResponseEntity<LocationDto> assignMechanic(@PathVariable final Long locationId,
                                                      @PathVariable final Long mechanicId) {
        LOGGER.info("POST /api/v1/locations/{}/mechanics/{}/add", locationId, mechanicId);
        Location location = locationService.assignMechanic(locationId, mechanicId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(locationMapper.toDto(location));
    }

    @ApiOperation(value = "Remove Mechanic from Location", response = LocationDto.class)
    @DeleteMapping(value = "{locationId}/mechanics/{mechanicId}/remove")
    public ResponseEntity<LocationDto> removeMechanic(@PathVariable final Long locationId,
                                                      @PathVariable final Long mechanicId) {
        LOGGER.info("DELETE /api/v1/locations/{}/mechanics/{}/remove", locationId, mechanicId);
        Location location = locationService.removeMechanic(locationId, mechanicId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(locationMapper.toDto(location));
    }
}
