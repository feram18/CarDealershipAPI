package edu.towson.cosc457.CarDealership.controller;

import edu.towson.cosc457.CarDealership.mapper.EmployeeMapper;
import edu.towson.cosc457.CarDealership.model.SiteManager;
import edu.towson.cosc457.CarDealership.model.dto.EmployeeDto;
import edu.towson.cosc457.CarDealership.model.dto.SiteManagerDto;
import edu.towson.cosc457.CarDealership.service.SiteManagerService;
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
@RequestMapping("/api/v1/site-managers")
public class SiteManagerController extends EmployeeController<SiteManagerService> {
    private final SiteManagerService siteManagerService;
    private final EmployeeMapper employeeMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(SiteManagerController.class);

    @Autowired
    public SiteManagerController(SiteManagerService siteManagerService,
                                 EmployeeMapper employeeMapper) {
        super(siteManagerService);
        this.siteManagerService = siteManagerService;
        this.employeeMapper = employeeMapper;
    }

    @ApiOperation(value = "Add Site Manager", response = SiteManagerDto.class)
    @PostMapping
    public ResponseEntity<SiteManagerDto> addEmployee(@RequestBody final SiteManagerDto siteManagerDto) {
        LOGGER.info("POST /api/v1/site-managers/");
        SiteManager siteManager = siteManagerService.addEmployee((SiteManager) employeeMapper.fromDto(siteManagerDto));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body((SiteManagerDto) employeeMapper.toDto(siteManager));
    }

    @ApiOperation(value = "Fetch All Site Managers", response = Iterable.class)
    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getEmployees() {
        LOGGER.info("GET /api/v1/site-managers/");
        List<SiteManager> siteManagers = siteManagerService.getEmployees();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(siteManagers.stream().map(employeeMapper::toDto).collect(Collectors.toList()));
    }

    @ApiOperation(value = "Fetch Site Manager by Id", response = SiteManagerDto.class)
    @GetMapping(value = "{id}")
    public ResponseEntity<SiteManagerDto> getEmployee(@PathVariable final Long id) {
        LOGGER.info("GET /api/v1/site-managers/{}", id);
        SiteManager siteManager = siteManagerService.getEmployee(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body((SiteManagerDto) employeeMapper.toDto(siteManager));
    }

    @ApiOperation(value = "Delete Site Manager", response = SiteManagerDto.class)
    @DeleteMapping(value = "{id}")
    public ResponseEntity<SiteManagerDto> deleteEmployee(@PathVariable final Long id) {
        LOGGER.info("DELETE /api/v1/site-managers/{}", id);
        SiteManager siteManager = siteManagerService.deleteEmployee(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body((SiteManagerDto) employeeMapper.toDto(siteManager));
    }

    @ApiOperation(value = "Update Site Manager", response = SiteManagerDto.class)
    @PutMapping(value = "{id}")
    public ResponseEntity<SiteManagerDto> editEmployee(@PathVariable final Long id,
                                                       @PathVariable final SiteManagerDto siteManagerDto) {
        LOGGER.info("PUT /api/v1/site-managers/{}", id);
        SiteManager siteManager = siteManagerService.editEmployee(id,(SiteManager) employeeMapper.fromDto(siteManagerDto));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body((SiteManagerDto) employeeMapper.toDto(siteManager));
    }

    @ApiOperation(value = "Fetch All Managers assigned to Site Manager", response = Iterable.class)
    @GetMapping(value = "{id}/managers")
    public ResponseEntity<List<EmployeeDto>> getAssignedManagers(@PathVariable final Long id) {
        LOGGER.info("GET /api/v1/site-managers/{}/managers", id);
        SiteManager siteManager = siteManagerService.getEmployee(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(siteManager.getManagers().stream().map(employeeMapper::toDto).collect(Collectors.toList()));
    }

    @ApiOperation(value = "Add Manager to Site Manager", response = SiteManagerDto.class)
    @PostMapping(value = "{siteManagerId}/managers/{managerId}/add")
    public ResponseEntity<SiteManagerDto> assignToManager(@PathVariable final Long siteManagerId,
                                                          @PathVariable final Long managerId) {
        LOGGER.info("POST /api/v1/site-managers/{}/managers/{}/add", siteManagerId, managerId);
        SiteManager siteManager = siteManagerService.assignToManager(siteManagerId, managerId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body((SiteManagerDto) employeeMapper.toDto(siteManager));
    }

    @ApiOperation(value = "Remove Manager from Site Manager", response = SiteManagerDto.class)
    @DeleteMapping(value = "{siteManagerId}/managers/{managerId}/remove")
    public ResponseEntity<SiteManagerDto> removeFromManager(@PathVariable final Long siteManagerId,
                                                            @PathVariable final Long managerId) {
        LOGGER.info("DELETE /api/v1/site-managers/{}/managers/{}/add", siteManagerId, managerId);
        SiteManager siteManager = siteManagerService.removeFromManager(siteManagerId, managerId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body((SiteManagerDto) employeeMapper.toDto(siteManager));
    }
}
