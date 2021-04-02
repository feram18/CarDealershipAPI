package edu.towson.cosc457.CarDealership.controller;

import edu.towson.cosc457.CarDealership.model.SiteManager;
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
public class SiteManagerController extends EmployeeController<SiteManager> {
    private final SiteManagerService siteManagerService;

    @Autowired
    public SiteManagerController(SiteManagerService siteManagerService) {
        super(siteManagerService);
        this.siteManagerService = siteManagerService;
    }

    @PostMapping
    public ResponseEntity<SiteManagerDto> addEmployee(@RequestBody final SiteManagerDto managerDto) {
        SiteManager manager = siteManagerService.addEmployee(SiteManager.from(managerDto));
        return new ResponseEntity<>(SiteManagerDto.from(manager), HttpStatus.OK);
    }

    // TODO - From Super

//    @GetMapping
//    public ResponseEntity<List<SiteManagerDto>> getEmployees() {
//        List<SiteManager> siteManagers = siteManagerService.getEmployees();
//        List<SiteManagerDto> siteManagersDto = siteManagers
//                .stream().map(SiteManagerDto::from).collect(Collectors.toList());
//        return new ResponseEntity<>(siteManagersDto, HttpStatus.OK);
//    }

    @GetMapping(value = "{id}")
    public ResponseEntity<SiteManagerDto> getEmployee(@PathVariable final Long id) {
        SiteManager manager = siteManagerService.getEmployee(id);
        return new ResponseEntity<>(SiteManagerDto.from(manager), HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<SiteManagerDto> deleteEmployee(@PathVariable final Long id) {
        SiteManager manager = siteManagerService.deleteEmployee(id);
        return new ResponseEntity<>(SiteManagerDto.from(manager), HttpStatus.OK);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<SiteManagerDto> editEmployee(@PathVariable final Long id,
                                                       @PathVariable final SiteManagerDto managerDto) {
        SiteManager manager = siteManagerService.editEmployee(id, SiteManager.from(managerDto));
        return new ResponseEntity<>(SiteManagerDto.from(manager), HttpStatus.OK);
    }

    @PostMapping(value = "{id}/mechanics/{id}/add")
    public ResponseEntity<SiteManagerDto> assignToManager(@PathVariable final Long managerId,
                                                          @PathVariable final Long mechanicId) {
        SiteManager manager = siteManagerService.assignToManager(managerId, mechanicId);
        return new ResponseEntity<>(SiteManagerDto.from(manager), HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}/mechanics/{id}/remove")
    public ResponseEntity<SiteManagerDto> removeFromManager(@PathVariable final Long managerId,
                                                            @PathVariable final Long mechanicId) {
        SiteManager manager = siteManagerService.removeFromManager(managerId, mechanicId);
        return new ResponseEntity<>(SiteManagerDto.from(manager), HttpStatus.OK);
    }
}
