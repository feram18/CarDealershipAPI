package edu.towson.cosc457.CarDealership.controller;

import edu.towson.cosc457.CarDealership.model.SalesAssociate;
import edu.towson.cosc457.CarDealership.model.dto.ClientDto;
import edu.towson.cosc457.CarDealership.model.dto.SalesAssociateDto;
import edu.towson.cosc457.CarDealership.service.EmployeeService;
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

    @Autowired
    public SalesAssociateController(SalesAssociateService associateService) {
        super(associateService);
        this.associateService = associateService;
    }

    @PostMapping
    public ResponseEntity<SalesAssociateDto> addEmployee(@RequestBody final SalesAssociateDto salesAssociateDto) {
        SalesAssociate salesAssociate = associateService.addEmployee(SalesAssociate.from(salesAssociateDto));
        return new ResponseEntity<>(SalesAssociateDto.from(salesAssociate), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<SalesAssociateDto>> getEmployees() {
        List<SalesAssociate> salesAssociates = associateService.getEmployees();
        List<SalesAssociateDto> salesAssociatesDto = salesAssociates
                .stream().map(SalesAssociateDto::from).collect(Collectors.toList());
        return new ResponseEntity<>(salesAssociatesDto, HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<SalesAssociateDto> getEmployee(@PathVariable final Long id) {
        SalesAssociate salesAssociate = associateService.getEmployee(id);
        return new ResponseEntity<>(SalesAssociateDto.from(salesAssociate), HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<SalesAssociateDto> deleteEmployee(@PathVariable final Long id) {
        SalesAssociate salesAssociate = associateService.deleteEmployee(id);
        return new ResponseEntity<>(SalesAssociateDto.from(salesAssociate), HttpStatus.OK);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<SalesAssociateDto> editEmployee(@PathVariable final Long id,
                                                          @PathVariable final SalesAssociateDto salesAssociateDto) {
        SalesAssociate salesAssociate = associateService.editEmployee(id, SalesAssociate.from(salesAssociateDto));
        return new ResponseEntity<>(SalesAssociateDto.from(salesAssociate), HttpStatus.OK);
    }

    @GetMapping(value = "{id}/clients")
    public ResponseEntity<List<ClientDto>> getAssignedClients(@PathVariable final Long id) {
        SalesAssociate salesAssociate = associateService.getEmployee(id);
        List<ClientDto> clientsDto = salesAssociate.getClients()
                .stream().map(ClientDto::from).collect(Collectors.toList());
        return new ResponseEntity<>(clientsDto, HttpStatus.OK);
    }

    @PostMapping(value = "{id}/clients/{id}/add")
    public ResponseEntity<SalesAssociateDto> assignClient(@PathVariable final Long associateId,
                                                          @PathVariable final Long clientId) {
        SalesAssociate associate = associateService.assignClient(associateId, clientId);
        return new ResponseEntity<>(SalesAssociateDto.from(associate), HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}/clients/{id}/remove")
    public ResponseEntity<SalesAssociateDto> removeClient(@PathVariable final Long associateId,
                                                          @PathVariable final Long clientId) {
        SalesAssociate associate = associateService.removeClient(associateId, clientId);
        return new ResponseEntity<>(SalesAssociateDto.from(associate), HttpStatus.OK);
    }
}
