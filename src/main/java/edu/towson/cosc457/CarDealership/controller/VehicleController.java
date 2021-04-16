package edu.towson.cosc457.CarDealership.controller;

import edu.towson.cosc457.CarDealership.model.Vehicle;
import edu.towson.cosc457.CarDealership.model.dto.ServiceTicketDto;
import edu.towson.cosc457.CarDealership.model.dto.VehicleDto;
import edu.towson.cosc457.CarDealership.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/vehicles")
public class VehicleController {
    private final VehicleService vehicleService;

    @Autowired
    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping
    public ResponseEntity<VehicleDto> addVehicle(@RequestBody final VehicleDto vehicleDto) {
        Vehicle vehicle = vehicleService.addVehicle(Vehicle.from(vehicleDto));
        return new ResponseEntity<>(VehicleDto.from(vehicle), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<VehicleDto>> getVehicles() {
        List<Vehicle> vehicles = vehicleService.getVehicles();
        List<VehicleDto> vehiclesDto = vehicles.stream().map(VehicleDto::from).collect(Collectors.toList());
        return new ResponseEntity<>(vehiclesDto, HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<VehicleDto> getVehicle(@PathVariable final Long id) {
        Vehicle vehicle = vehicleService.getVehicle(id);
        return new ResponseEntity<>(VehicleDto.from(vehicle), HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<VehicleDto> deleteVehicle(@PathVariable final Long id) {
        Vehicle vehicle = vehicleService.deleteVehicle(id);
        return new ResponseEntity<>(VehicleDto.from(vehicle), HttpStatus.OK);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<VehicleDto> editVehicle(@PathVariable final Long id,
                                                  @RequestBody final VehicleDto vehicleDto) {
        Vehicle vehicle = vehicleService.editVehicle(id, Vehicle.from(vehicleDto));
        return new ResponseEntity<>(VehicleDto.from(vehicle), HttpStatus.OK);
    }

    @GetMapping(value = "{vehicleId}/tickets")
    public ResponseEntity<List<ServiceTicketDto>> getAssignedTickets(@PathVariable final Long id) {
        Vehicle vehicle = vehicleService.getVehicle(id);
        List<ServiceTicketDto> serviceTicketsDto = vehicle.getTickets()
                .stream().map(ServiceTicketDto::from).collect(Collectors.toList());
        return new ResponseEntity<>(serviceTicketsDto, HttpStatus.OK);
    }

    @PostMapping(value = "{vehicleId}/tickets/{ticketId}/add")
    public ResponseEntity<VehicleDto> assignTicket(@PathVariable final Long vehicleId,
                                                   @PathVariable final Long ticketId) {
        Vehicle vehicle = vehicleService.assignTicket(vehicleId, ticketId);
        return new ResponseEntity<>(VehicleDto.from(vehicle), HttpStatus.OK);
    }

    @DeleteMapping(value = "{vehicleId}/tickets/{ticketId}/remove")
    public ResponseEntity<VehicleDto> removeTicket(@PathVariable final Long vehicleId,
                                                   @PathVariable final Long ticketId) {
        Vehicle vehicle = vehicleService.removeTicket(vehicleId, ticketId);
        return new ResponseEntity<>(VehicleDto.from(vehicle), HttpStatus.OK);
    }
}
