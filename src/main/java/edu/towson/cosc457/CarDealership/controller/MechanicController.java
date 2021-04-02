package edu.towson.cosc457.CarDealership.controller;

import edu.towson.cosc457.CarDealership.model.Mechanic;
import edu.towson.cosc457.CarDealership.model.dto.MechanicDto;
import edu.towson.cosc457.CarDealership.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/mechanics")
public class MechanicController extends EmployeeController<Mechanic> {
    private final EmployeeService<Mechanic> mechanicService;

    @Autowired
    public MechanicController(EmployeeService<Mechanic> mechanicService) {
        super(mechanicService);
        this.mechanicService = mechanicService;
    }

    @PostMapping
    public ResponseEntity<MechanicDto> addEmployee(@RequestBody final MechanicDto mechanicDto) {
        Mechanic mechanic = mechanicService.addEmployee(Mechanic.from(mechanicDto));
        return new ResponseEntity<>(MechanicDto.from(mechanic), HttpStatus.OK);
    }

    // TODO - From Super

//    @GetMapping
//    public ResponseEntity<List<MechanicDto>> getEmployees() {
//        List<Mechanic> mechanics = mechanicService.getEmployees();
//        List<MechanicDto> mechanicsDto = mechanics.stream().map(MechanicDto::from).collect(Collectors.toList());
//        return new ResponseEntity<>(mechanicsDto, HttpStatus.OK);
//    }

    @GetMapping(value = "{id}")
    public ResponseEntity<MechanicDto> getEmployee(@PathVariable final Long id) {
        Mechanic mechanic = mechanicService.getEmployee(id);
        return new ResponseEntity<>(MechanicDto.from(mechanic), HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<MechanicDto> deleteEmployee(@PathVariable final Long id) {
        Mechanic mechanic = mechanicService.deleteEmployee(id);
        return new ResponseEntity<>(MechanicDto.from(mechanic), HttpStatus.OK);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<MechanicDto> editEmployee(@PathVariable final Long id,
                                                    @PathVariable final MechanicDto mechanicDto) {
        Mechanic mechanic = mechanicService.editEmployee(id, Mechanic.from(mechanicDto));
        return new ResponseEntity<>(MechanicDto.from(mechanic), HttpStatus.OK);
    }

//    @PostMapping(value = "{id}/tickets/{id}/add")
//    public ResponseEntity<MechanicDto> assignTicket(@PathVariable final Long mechanicId,
//                                                    @PathVariable final Long ticketId) {
//        Mechanic mechanic = mechanicService.assignTicket(mechanicId, ticketId);
//        return new ResponseEntity<>(MechanicDto.from(mechanic), HttpStatus.OK);
//    }
//
//    @DeleteMapping(value = "{id}/tickets/{id}/remove")
//    public ResponseEntity<MechanicDto> removeTicket(@PathVariable final Long mechanicId,
//                                                    @PathVariable final Long ticketId) {
//        Mechanic mechanic = mechanicService.removeTicket(mechanicId, ticketId);
//        return new ResponseEntity<>(MechanicDto.from(mechanic), HttpStatus.OK);
//    }
}
