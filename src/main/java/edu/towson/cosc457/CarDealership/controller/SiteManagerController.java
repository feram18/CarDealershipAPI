package edu.towson.cosc457.CarDealership.controller;

import edu.towson.cosc457.CarDealership.model.SiteManager;
import edu.towson.cosc457.CarDealership.model.dto.ManagerDto;
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

    @Autowired
    public SiteManagerController(SiteManagerService siteManagerService) {
        super(siteManagerService);
        this.siteManagerService = siteManagerService;
    }

    @PostMapping
    public ResponseEntity<SiteManagerDto> addEmployee(@RequestBody final SiteManagerDto siteManagerDto) {
        SiteManager siteManager = siteManagerService.addEmployee(SiteManager.from(siteManagerDto));
        return new ResponseEntity<>(SiteManagerDto.from(siteManager), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<SiteManagerDto>> getEmployees() {
        List<SiteManager> siteManagers = siteManagerService.getEmployees();
        List<SiteManagerDto> siteManagersDto = siteManagers
                .stream().map(SiteManagerDto::from).collect(Collectors.toList());
        return new ResponseEntity<>(siteManagersDto, HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<SiteManagerDto> getEmployee(@PathVariable final Long id) {
        SiteManager siteManager = siteManagerService.getEmployee(id);
        return new ResponseEntity<>(SiteManagerDto.from(siteManager), HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<SiteManagerDto> deleteEmployee(@PathVariable final Long id) {
        SiteManager siteManager = siteManagerService.deleteEmployee(id);
        return new ResponseEntity<>(SiteManagerDto.from(siteManager), HttpStatus.OK);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<SiteManagerDto> editEmployee(@PathVariable final Long id,
                                                       @PathVariable final SiteManagerDto siteManagerDto) {
        SiteManager siteManager = siteManagerService.editEmployee(id, SiteManager.from(siteManagerDto));
        return new ResponseEntity<>(SiteManagerDto.from(siteManager), HttpStatus.OK);
    }

    @GetMapping(value = "{id}/managers")
    public ResponseEntity<List<ManagerDto>> getAssignedManagers(@PathVariable final Long id) {
        SiteManager siteManager = siteManagerService.getEmployee(id);
        List<ManagerDto> managersDto = siteManager.getManagers()
                .stream().map(ManagerDto::from).collect(Collectors.toList());
        return new ResponseEntity<>(managersDto, HttpStatus.OK);
    }

    @PostMapping(value = "{siteManagerId}/managers/{managerId}/add")
    public ResponseEntity<SiteManagerDto> assignToManager(@PathVariable final Long siteManagerId,
                                                          @PathVariable final Long managerId) {
        SiteManager siteManager = siteManagerService.assignToManager(siteManagerId, managerId);
        return new ResponseEntity<>(SiteManagerDto.from(siteManager), HttpStatus.OK);
    }

    @DeleteMapping(value = "{siteManagerId}/managers/{managerId}/remove")
    public ResponseEntity<SiteManagerDto> removeFromManager(@PathVariable final Long siteManagerId,
                                                            @PathVariable final Long managerId) {
        SiteManager siteManager = siteManagerService.removeFromManager(siteManagerId, managerId);
        return new ResponseEntity<>(SiteManagerDto.from(siteManager), HttpStatus.OK);
    }
}
