package edu.towson.cosc457.CarDealership.controller;

import edu.towson.cosc457.CarDealership.mapper.ClientMapper;
import edu.towson.cosc457.CarDealership.mapper.EmployeeMapper;
import edu.towson.cosc457.CarDealership.model.SalesAssociate;
import edu.towson.cosc457.CarDealership.model.dto.ClientDto;
import edu.towson.cosc457.CarDealership.model.dto.EmployeeDto;
import edu.towson.cosc457.CarDealership.model.dto.SalesAssociateDto;
import edu.towson.cosc457.CarDealership.service.SalesAssociateService;
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
        SalesAssociate salesAssociate = associateService.addEmployee((SalesAssociate) employeeMapper.fromDto(salesAssociateDto));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body((SalesAssociateDto) employeeMapper.toDto(salesAssociate));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getEmployees() {
        List<SalesAssociate> salesAssociates = associateService.getEmployees();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(salesAssociates.stream().map(employeeMapper::toDto).collect(Collectors.toList()));
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<SalesAssociateDto> getEmployee(@PathVariable final Long id) {
        SalesAssociate salesAssociate = associateService.getEmployee(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body((SalesAssociateDto) employeeMapper.toDto(salesAssociate));
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<SalesAssociateDto> deleteEmployee(@PathVariable final Long id) {
        SalesAssociate salesAssociate = associateService.deleteEmployee(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body((SalesAssociateDto) employeeMapper.toDto(salesAssociate));
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<SalesAssociateDto> editEmployee(@PathVariable final Long id,
                                                          @PathVariable final SalesAssociateDto salesAssociateDto) {
        SalesAssociate salesAssociate = associateService.editEmployee(id, (SalesAssociate) employeeMapper.fromDto(salesAssociateDto));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body((SalesAssociateDto) employeeMapper.toDto(salesAssociate));
    }

    @GetMapping(value = "{id}/clients")
    public ResponseEntity<List<ClientDto>> getAssignedClients(@PathVariable final Long id) {
        SalesAssociate salesAssociate = associateService.getEmployee(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(salesAssociate.getClients().stream().map(clientMapper::toDto).collect(Collectors.toList()));
    }

    @PostMapping(value = "{associateId}/clients/{clientId}/add")
    public ResponseEntity<SalesAssociateDto> assignClient(@PathVariable final Long associateId,
                                                          @PathVariable final Long clientId) {
        SalesAssociate salesAssociate = associateService.assignClient(associateId, clientId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body((SalesAssociateDto) employeeMapper.toDto(salesAssociate));
    }

    @DeleteMapping(value = "{associateId}/clients/{clientId}/remove")
    public ResponseEntity<SalesAssociateDto> removeClient(@PathVariable final Long associateId,
                                                          @PathVariable final Long clientId) {
        SalesAssociate salesAssociate = associateService.removeClient(associateId, clientId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body((SalesAssociateDto) employeeMapper.toDto(salesAssociate));
    }
}
