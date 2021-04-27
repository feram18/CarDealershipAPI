package edu.towson.cosc457.CarDealership.controller;

import edu.towson.cosc457.CarDealership.mapper.EmployeeMapper;
import edu.towson.cosc457.CarDealership.model.SiteManager;
import edu.towson.cosc457.CarDealership.model.dto.EmployeeDto;
import edu.towson.cosc457.CarDealership.model.dto.SiteManagerDto;
import edu.towson.cosc457.CarDealership.service.SiteManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/site-managers")
public class SiteManagerController extends EmployeeController<SiteManagerService> {
    private final SiteManagerService siteManagerService;
    private final EmployeeMapper employeeMapper;

    @Autowired
    public SiteManagerController(SiteManagerService siteManagerService,
                                 EmployeeMapper employeeMapper) {
        super(siteManagerService);
        this.siteManagerService = siteManagerService;
        this.employeeMapper = employeeMapper;
    }

    @PostMapping
    public ResponseEntity<SiteManagerDto> addEmployee(@RequestBody final SiteManagerDto siteManagerDto) {
        SiteManager siteManager = siteManagerService.addEmployee((SiteManager) employeeMapper.fromDto(siteManagerDto));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body((SiteManagerDto) employeeMapper.toDto(siteManager));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getEmployees() {
        List<SiteManager> siteManagers = siteManagerService.getEmployees();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(siteManagers.stream().map(employeeMapper::toDto).collect(Collectors.toList()));
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<SiteManagerDto> getEmployee(@PathVariable final Long id) {
        SiteManager siteManager = siteManagerService.getEmployee(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body((SiteManagerDto) employeeMapper.toDto(siteManager));
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<SiteManagerDto> deleteEmployee(@PathVariable final Long id) {
        SiteManager siteManager = siteManagerService.deleteEmployee(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body((SiteManagerDto) employeeMapper.toDto(siteManager));
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<SiteManagerDto> editEmployee(@PathVariable final Long id,
                                                       @PathVariable final SiteManagerDto siteManagerDto) {
        SiteManager siteManager = siteManagerService.editEmployee(id,(SiteManager) employeeMapper.fromDto(siteManagerDto));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body((SiteManagerDto) employeeMapper.toDto(siteManager));
    }

    @GetMapping(value = "{id}/managers")
    public ResponseEntity<List<EmployeeDto>> getAssignedManagers(@PathVariable final Long id) {
        SiteManager siteManager = siteManagerService.getEmployee(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(siteManager.getManagers().stream().map(employeeMapper::toDto).collect(Collectors.toList()));
    }

    @PostMapping(value = "{siteManagerId}/managers/{managerId}/add")
    public ResponseEntity<SiteManagerDto> assignToManager(@PathVariable final Long siteManagerId,
                                                          @PathVariable final Long managerId) {
        SiteManager siteManager = siteManagerService.assignToManager(siteManagerId, managerId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body((SiteManagerDto) employeeMapper.toDto(siteManager));
    }

    @DeleteMapping(value = "{siteManagerId}/managers/{managerId}/remove")
    public ResponseEntity<SiteManagerDto> removeFromManager(@PathVariable final Long siteManagerId,
                                                            @PathVariable final Long managerId) {
        SiteManager siteManager = siteManagerService.removeFromManager(siteManagerId, managerId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body((SiteManagerDto) employeeMapper.toDto(siteManager));
    }
}
