package edu.towson.cosc457.CarDealership.controller;

import edu.towson.cosc457.CarDealership.mapper.EmployeeMapper;
import edu.towson.cosc457.CarDealership.mapper.ServiceTicketMapper;
import edu.towson.cosc457.CarDealership.model.Mechanic;
import edu.towson.cosc457.CarDealership.model.dto.EmployeeDto;
import edu.towson.cosc457.CarDealership.model.dto.MechanicDto;
import edu.towson.cosc457.CarDealership.model.dto.ServiceTicketDto;
import edu.towson.cosc457.CarDealership.service.MechanicService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/mechanics")
public class MechanicController extends EmployeeController<MechanicService> {
    private final MechanicService mechanicService;
    private final EmployeeMapper employeeMapper;
    private final ServiceTicketMapper serviceTicketMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(MechanicController.class);

    @Autowired
    public MechanicController(MechanicService mechanicService,
                              EmployeeMapper employeeMapper,
                              ServiceTicketMapper serviceTicketMapper) {
        super(mechanicService);
        this.mechanicService = mechanicService;
        this.employeeMapper = employeeMapper;
        this.serviceTicketMapper = serviceTicketMapper;
    }

    @ApiOperation(value = "Add Mechanic", response = MechanicDto.class)
    @PostMapping
    public ResponseEntity<MechanicDto> addEmployee(@RequestBody final MechanicDto mechanicDto) {
        LOGGER.info("POST /api/v1/mechanics/");
        Mechanic mechanic = mechanicService.addEmployee((Mechanic) employeeMapper.fromDto(mechanicDto));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body((MechanicDto) employeeMapper.toDto(mechanic));
    }

    @ApiOperation(value = "Fetch All Mechanics", response = Iterable.class)
    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getEmployees() {
        LOGGER.info("GET /api/v1/mechanics/");
        List<Mechanic> mechanics = mechanicService.getEmployees();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(mechanics.stream().map(employeeMapper::toDto).collect(Collectors.toList()));
    }

    @ApiOperation(value = "Fetch Mechanic by Id", response = MechanicDto.class)
    @GetMapping(value = "{id}")
    public ResponseEntity<MechanicDto> getEmployee(@PathVariable final Long id) {
        LOGGER.info("GET /api/v1/mechanics/{}", id);
        Mechanic mechanic = mechanicService.getEmployee(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body((MechanicDto) employeeMapper.toDto(mechanic));
    }

    @ApiOperation(value = "Delete Mechanic", response = MechanicDto.class)
    @DeleteMapping(value = "{id}")
    public ResponseEntity<MechanicDto> deleteEmployee(@PathVariable final Long id) {
        LOGGER.info("DELETE /api/v1/mechanics/{}", id);
        Mechanic mechanic = mechanicService.deleteEmployee(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body((MechanicDto) employeeMapper.toDto(mechanic));
    }

    @ApiOperation(value = "Update Mechanic", response = MechanicDto.class)
    @PutMapping(value = "{id}")
    public ResponseEntity<MechanicDto> editEmployee(@PathVariable final Long id,
                                                    @PathVariable final MechanicDto mechanicDto) {
        LOGGER.info("PUT /api/v1/mechanics/{}", id);
        Mechanic mechanic = mechanicService.editEmployee(id, (Mechanic) employeeMapper.fromDto(mechanicDto));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body((MechanicDto) employeeMapper.toDto(mechanic));
    }

    @ApiOperation(value = "Fetch All Service Tickets assigned to Mechanic", response = Iterable.class)
    @GetMapping(value = "{id}/tickets")
    public ResponseEntity<List<ServiceTicketDto>> getAssignedTickets(@PathVariable final Long id) {
        LOGGER.info("GET /api/v1/mechanics/{}/tickets", id);
        Mechanic mechanic = mechanicService.getEmployee(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(mechanic.getTickets().stream().map(serviceTicketMapper::toDto).collect(Collectors.toList()));
    }

    @ApiOperation(value = "Add Service Ticket to Mechanic", response = MechanicDto.class)
    @PostMapping(value = "{mechanicId}/tickets/{ticketId}/add")
    public ResponseEntity<MechanicDto> assignTicket(@PathVariable final Long mechanicId,
                                                    @PathVariable final Long ticketId) {
        LOGGER.info("POST /api/v1/mechanics/{}/tickets/{}/add", mechanicId, ticketId);
        Mechanic mechanic = mechanicService.assignTicket(mechanicId, ticketId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body((MechanicDto) employeeMapper.toDto(mechanic));
    }

    @ApiOperation(value = "Remove Service Ticket from Mechanic", response = MechanicDto.class)
    @DeleteMapping(value = "{mechanicId}/tickets/{ticketId}/remove")
    public ResponseEntity<MechanicDto> removeTicket(@PathVariable final Long mechanicId,
                                                    @PathVariable final Long ticketId) {
        LOGGER.info("DELETE /api/v1/mechanics/{}/tickets/{}/add", mechanicId, ticketId);
        Mechanic mechanic = mechanicService.removeTicket(mechanicId, ticketId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body((MechanicDto) employeeMapper.toDto(mechanic));
    }
}
