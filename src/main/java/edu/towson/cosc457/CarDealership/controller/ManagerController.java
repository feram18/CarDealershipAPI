package edu.towson.cosc457.CarDealership.controller;

import edu.towson.cosc457.CarDealership.mapper.EmployeeMapper;
import edu.towson.cosc457.CarDealership.model.Manager;
import edu.towson.cosc457.CarDealership.model.dto.EmployeeDto;
import edu.towson.cosc457.CarDealership.model.dto.ManagerDto;
import edu.towson.cosc457.CarDealership.service.ManagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/managers")
public class ManagerController extends EmployeeController<ManagerService> {
    private final ManagerService managerService;
    private final EmployeeMapper employeeMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(ManagerController.class);

    @Autowired
    public ManagerController(ManagerService managerService, EmployeeMapper employeeMapper) {
        super(managerService);
        this.managerService = managerService;
        this.employeeMapper = employeeMapper;
    }

    @PostMapping
    public ResponseEntity<ManagerDto> addEmployee(@RequestBody final ManagerDto managerDto) {
        LOGGER.info("POST /api/v1/managers/");
        Manager manager = managerService.addEmployee((Manager) employeeMapper.fromDto(managerDto));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body((ManagerDto) employeeMapper.toDto(manager));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getEmployees() {
        LOGGER.info("GET /api/v1/managers/");
        List<Manager> managers = managerService.getEmployees();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(managers.stream().map(employeeMapper::toDto).collect(Collectors.toList()));
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<ManagerDto> getEmployee(@PathVariable final Long id) {
        LOGGER.info("GET /api/v1/managers/{}", id);
        Manager manager = managerService.getEmployee(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body((ManagerDto) employeeMapper.toDto(manager));
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<ManagerDto> deleteEmployee(@PathVariable final Long id) {
        LOGGER.info("DELETE /api/v1/managers/{}", id);
        Manager manager = managerService.deleteEmployee(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body((ManagerDto) employeeMapper.toDto(manager));
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<ManagerDto> editEmployee(@PathVariable final Long id,
                                                   @PathVariable final ManagerDto managerDto) {
        LOGGER.info("PUT /api/v1/managers/{}", id);
        Manager manager = managerService.editEmployee(id, (Manager) employeeMapper.fromDto(managerDto));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body((ManagerDto) employeeMapper.toDto(manager));
    }

    @GetMapping(value = "{id}/mechanics")
    public ResponseEntity<List<EmployeeDto>> getAssignedMechanics(@PathVariable final Long id) {
        LOGGER.info("GET /api/v1/managers/{}/mechanics", id);
        Manager manager = managerService.getEmployee(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(manager.getMechanics().stream().map(employeeMapper::toDto).collect(Collectors.toList()));
    }

    @PostMapping(value = "{managerId}/mechanics/{mechanicId}/add")
    public ResponseEntity<ManagerDto> assignToManager(@PathVariable final Long managerId,
                                                      @PathVariable final Long mechanicId) {
        LOGGER.info("POST /api/v1/managers/{}/mechanics/{}/add", managerId, mechanicId);
        Manager manager = managerService.assignToManager(managerId, mechanicId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body((ManagerDto) employeeMapper.toDto(manager));
    }

    @DeleteMapping(value = "{managerId}/mechanics/{mechanicId}/remove")
    public ResponseEntity<ManagerDto> removeFromManager(@PathVariable final Long managerId,
                                                        @PathVariable final Long mechanicId) {
        LOGGER.info("DELETE /api/v1/managers/{}/mechanics/{}/remove", managerId, mechanicId);
        Manager manager = managerService.removeFromManager(managerId, mechanicId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body((ManagerDto) employeeMapper.toDto(manager));
    }
}
