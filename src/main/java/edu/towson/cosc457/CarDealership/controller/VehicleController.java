package edu.towson.cosc457.CarDealership.controller;

import edu.towson.cosc457.CarDealership.mapper.ServiceTicketMapper;
import edu.towson.cosc457.CarDealership.mapper.VehicleMapper;
import edu.towson.cosc457.CarDealership.model.Vehicle;
import edu.towson.cosc457.CarDealership.model.dto.ServiceTicketDto;
import edu.towson.cosc457.CarDealership.model.dto.VehicleDto;
import edu.towson.cosc457.CarDealership.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/vehicles")
@RequiredArgsConstructor
public class VehicleController {
    private final VehicleService vehicleService;
    private final VehicleMapper vehicleMapper;
    private final ServiceTicketMapper serviceTicketMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(VehicleController.class);

    @PostMapping
    public ResponseEntity<VehicleDto> addVehicle(@RequestBody final VehicleDto vehicleDto) {
        LOGGER.info("POST /api/v1/vehicles/");
        Vehicle vehicle = vehicleService.addVehicle(vehicleMapper.fromDto(vehicleDto));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(vehicleMapper.toDto(vehicle));
    }

    @GetMapping
    public ResponseEntity<List<VehicleDto>> getVehicles() {
        LOGGER.info("GET /api/v1/vehicles/");
        List<Vehicle> vehicles = vehicleService.getVehicles();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(vehicles.stream().map(vehicleMapper::toDto).collect(Collectors.toList()));
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<VehicleDto> getVehicle(@PathVariable final Long id) {
        LOGGER.info("GET /api/v1/vehicles/{}", id);
        Vehicle vehicle = vehicleService.getVehicle(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(vehicleMapper.toDto(vehicle));
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<VehicleDto> deleteVehicle(@PathVariable final Long id) {
        LOGGER.info("DELETE /api/v1/vehicles/{}", id);
        Vehicle vehicle = vehicleService.deleteVehicle(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(vehicleMapper.toDto(vehicle));
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<VehicleDto> editVehicle(@PathVariable final Long id,
                                                  @RequestBody final VehicleDto vehicleDto) {
        LOGGER.info("PUT /api/v1/vehicles/{}", id);
        Vehicle vehicle = vehicleService.editVehicle(id, vehicleMapper.fromDto(vehicleDto));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(vehicleMapper.toDto(vehicle));
    }

    @GetMapping(value = "{id}/tickets")
    public ResponseEntity<List<ServiceTicketDto>> getAssignedTickets(@PathVariable final Long id) {
        LOGGER.info("GET /api/v1/vehicles/{}/tickets", id);
        Vehicle vehicle = vehicleService.getVehicle(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(vehicle.getTickets().stream().map(serviceTicketMapper::toDto).collect(Collectors.toList()));
    }

    @PostMapping(value = "{vehicleId}/tickets/{ticketId}/add")
    public ResponseEntity<VehicleDto> assignTicket(@PathVariable final Long vehicleId,
                                                   @PathVariable final Long ticketId) {
        LOGGER.info("POST /api/v1/vehicles/{}/tickets/{}/add", vehicleId, ticketId);
        Vehicle vehicle = vehicleService.assignTicket(vehicleId, ticketId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(vehicleMapper.toDto(vehicle));
    }

    @DeleteMapping(value = "{vehicleId}/tickets/{ticketId}/remove")
    public ResponseEntity<VehicleDto> removeTicket(@PathVariable final Long vehicleId,
                                                   @PathVariable final Long ticketId) {
        LOGGER.info("DELETE /api/v1/vehicles/{}/tickets/{}/add", vehicleId, ticketId);
        Vehicle vehicle = vehicleService.removeTicket(vehicleId, ticketId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(vehicleMapper.toDto(vehicle));
    }
}
