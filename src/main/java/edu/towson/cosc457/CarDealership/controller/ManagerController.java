package edu.towson.cosc457.CarDealership.controller;

import edu.towson.cosc457.CarDealership.model.Manager;
import edu.towson.cosc457.CarDealership.model.dto.ManagerDto;
import edu.towson.cosc457.CarDealership.service.EmployeeService;
import edu.towson.cosc457.CarDealership.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/managers")
public class ManagerController extends EmployeeController<Manager> {
    private final ManagerService managerService;

    @Autowired
    public ManagerController(ManagerService managerService) {
        super(managerService);
        this.managerService = managerService;
    }

    @PostMapping
    public ResponseEntity<ManagerDto> addEmployee(@RequestBody final ManagerDto managerDto) {
        Manager manager = managerService.addEmployee(Manager.from(managerDto));
        return new ResponseEntity<>(ManagerDto.from(manager), HttpStatus.OK);
    }
// TODO - From Super

//    @GetMapping
//    public ResponseEntity<List<ManagerDto>> getEmployees() {
//        List<Manager> managers = managerService.getEmployees();
//        List<ManagerDto> managersDto = managers.stream().map(ManagerDto::from).collect(Collectors.toList());
//        return new ResponseEntity<>(managersDto, HttpStatus.OK);
//    }

    @GetMapping(value = "{id}")
    public ResponseEntity<ManagerDto> getEmployee(@PathVariable final Long id) {
        Manager manager = managerService.getEmployee(id);
        return new ResponseEntity<>(ManagerDto.from(manager), HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<ManagerDto> deleteEmployee(@PathVariable final Long id) {
        Manager manager = managerService.deleteEmployee(id);
        return new ResponseEntity<>(ManagerDto.from(manager), HttpStatus.OK);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<ManagerDto> editEmployee(@PathVariable final Long id,
                                                   @PathVariable final ManagerDto managerDto) {
        Manager manager = managerService.editEmployee(id, Manager.from(managerDto));
        return new ResponseEntity<>(ManagerDto.from(manager), HttpStatus.OK);
    }

    @PostMapping(value = "{id}/mechanics/{id}/add")
    public ResponseEntity<ManagerDto> assignToManager(@PathVariable final Long managerId,
                                                      @PathVariable final Long mechanicId) {
        Manager manager = managerService.assignToManager(managerId, mechanicId);
        return new ResponseEntity<>(ManagerDto.from(manager), HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}/mechanics/{id}/remove")
    public ResponseEntity<ManagerDto> removeFromManager(@PathVariable final Long managerId,
                                                        @PathVariable final Long mechanicId) {
        Manager manager = managerService.removeFromManager(managerId, mechanicId);
        return new ResponseEntity<>(ManagerDto.from(manager), HttpStatus.OK);
    }
}
