package edu.towson.cosc457.CarDealership.controller;

import edu.towson.cosc457.CarDealership.mapper.ClientMapper;
import edu.towson.cosc457.CarDealership.mapper.EmployeeMapper;
import edu.towson.cosc457.CarDealership.model.SalesAssociate;
import edu.towson.cosc457.CarDealership.model.dto.ClientDto;
import edu.towson.cosc457.CarDealership.model.dto.EmployeeDto;
import edu.towson.cosc457.CarDealership.model.dto.SalesAssociateDto;
import edu.towson.cosc457.CarDealership.service.SalesAssociateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/associates")
public class SalesAssociateController extends EmployeeController<SalesAssociateService> {
    private final SalesAssociateService associateService;
    private final EmployeeMapper employeeMapper;
    private final ClientMapper clientMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(SalesAssociateController.class);

    @Autowired
    public SalesAssociateController(SalesAssociateService associateService,
                                    EmployeeMapper employeeMapper,
                                    ClientMapper clientMapper) {
        super(associateService);
        this.associateService = associateService;
        this.employeeMapper = employeeMapper;
        this.clientMapper = clientMapper;
    }

    @PostMapping
    public ResponseEntity<SalesAssociateDto> addEmployee(@RequestBody final SalesAssociateDto salesAssociateDto) {
        LOGGER.info("POST /api/v1/associates/");
        SalesAssociate salesAssociate = associateService
                .addEmployee((SalesAssociate) employeeMapper.fromDto(salesAssociateDto));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body((SalesAssociateDto) employeeMapper.toDto(salesAssociate));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getEmployees() {
        LOGGER.info("GET /api/v1/associates/");
        List<SalesAssociate> salesAssociates = associateService.getEmployees();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(salesAssociates.stream().map(employeeMapper::toDto).collect(Collectors.toList()));
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<SalesAssociateDto> getEmployee(@PathVariable final Long id) {
        LOGGER.info("GET /api/v1/associates/{}", id);
        SalesAssociate salesAssociate = associateService.getEmployee(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body((SalesAssociateDto) employeeMapper.toDto(salesAssociate));
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<SalesAssociateDto> deleteEmployee(@PathVariable final Long id) {
        LOGGER.info("DELETE /api/v1/associates/{}", id);
        SalesAssociate salesAssociate = associateService.deleteEmployee(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body((SalesAssociateDto) employeeMapper.toDto(salesAssociate));
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<SalesAssociateDto> editEmployee(@PathVariable final Long id,
                                                          @PathVariable final SalesAssociateDto salesAssociateDto) {
        LOGGER.info("PUT /api/v1/associates/{}", id);
        SalesAssociate salesAssociate = associateService
                .editEmployee(id, (SalesAssociate) employeeMapper.fromDto(salesAssociateDto));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body((SalesAssociateDto) employeeMapper.toDto(salesAssociate));
    }

    @GetMapping(value = "{id}/clients")
    public ResponseEntity<List<ClientDto>> getAssignedClients(@PathVariable final Long id) {
        LOGGER.info("GET /api/v1/associates/{}/clients", id);
        SalesAssociate salesAssociate = associateService.getEmployee(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(salesAssociate.getClients().stream().map(clientMapper::toDto).collect(Collectors.toList()));
    }

    @PostMapping(value = "{associateId}/clients/{clientId}/add")
    public ResponseEntity<SalesAssociateDto> assignClient(@PathVariable final Long associateId,
                                                          @PathVariable final Long clientId) {
        LOGGER.info("POST /api/v1/associates/{}/clients/{}/add", associateId, clientId);
        SalesAssociate salesAssociate = associateService.assignClient(associateId, clientId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body((SalesAssociateDto) employeeMapper.toDto(salesAssociate));
    }

    @DeleteMapping(value = "{associateId}/clients/{clientId}/remove")
    public ResponseEntity<SalesAssociateDto> removeClient(@PathVariable final Long associateId,
                                                          @PathVariable final Long clientId) {
        LOGGER.info("DELETE /api/v1/associates/{}/clients/{}/add", associateId, clientId);
        SalesAssociate salesAssociate = associateService.removeClient(associateId, clientId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body((SalesAssociateDto) employeeMapper.toDto(salesAssociate));
    }
}
