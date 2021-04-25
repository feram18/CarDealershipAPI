package edu.towson.cosc457.CarDealership.controller;

import edu.towson.cosc457.CarDealership.model.Department;
import edu.towson.cosc457.CarDealership.model.dto.DepartmentDto;
import edu.towson.cosc457.CarDealership.model.dto.MechanicDto;
import edu.towson.cosc457.CarDealership.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/departments")
@RequiredArgsConstructor

public class DepartmentController {
    private final DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<DepartmentDto> addDepartment(@RequestBody final DepartmentDto departmentDto) {
        Department location = departmentService.addDepartment(Department.from(departmentDto));
        return new ResponseEntity<>(DepartmentDto.from(location), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<DepartmentDto>> getDepartments() {
        List<Department> locations = departmentService.getDepartments();
        List<DepartmentDto> locationsDto = locations.stream().map(DepartmentDto::from).collect(Collectors.toList());
        return new ResponseEntity<>(locationsDto, HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<DepartmentDto> getDepartment(@PathVariable final Long id) {
        Department location = departmentService.getDepartment(id);
        return new ResponseEntity<>(DepartmentDto.from(location), HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<DepartmentDto> deleteDepartment(@PathVariable final Long id) {
        Department location = departmentService.deleteDepartment(id);
        return new ResponseEntity<>(DepartmentDto.from(location), HttpStatus.OK);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<DepartmentDto> editDepartment(@PathVariable final Long id,
                                                        @RequestBody final DepartmentDto departmentDto) {
        Department location = departmentService.editDepartment(id, Department.from(departmentDto));
        return new ResponseEntity<>(DepartmentDto.from(location), HttpStatus.OK);
    }

    @GetMapping(value = "{id}/mechanics")
    public ResponseEntity<List<MechanicDto>> getMechanics(@PathVariable final Long id) {
        Department department = departmentService.getDepartment(id);
        List<MechanicDto> mechanicsDto = department.getMechanics()
                .stream().map(MechanicDto::from).collect(Collectors.toList());
        return new ResponseEntity<>(mechanicsDto, HttpStatus.OK);
    }

    @PostMapping(value = "{departmentId}/mechanics/{mechanicId}/add")
    public ResponseEntity<DepartmentDto> addMechanicToDepartment(@PathVariable final Long departmentId,
                                                                 @PathVariable final Long mechanicId) {
        Department department = departmentService.assignMechanic(departmentId, mechanicId);
        return new ResponseEntity<>(DepartmentDto.from(department), HttpStatus.OK);
    }

    @DeleteMapping(value = "{departmentId}/mechanics/{mechanicId}/remove")
    public ResponseEntity<DepartmentDto> removeMechanicFromDepartment(@PathVariable final Long departmentId,
                                                                      @PathVariable final Long mechanicId) {
        Department department = departmentService.removeMechanic(departmentId, mechanicId);
        return new ResponseEntity<>(DepartmentDto.from(department), HttpStatus.OK);
    }
}
