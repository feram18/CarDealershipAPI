package edu.towson.cosc457.CarDealership.controller;

import edu.towson.cosc457.CarDealership.mapper.DepartmentMapper;
import edu.towson.cosc457.CarDealership.mapper.EmployeeMapper;
import edu.towson.cosc457.CarDealership.model.Department;
import edu.towson.cosc457.CarDealership.model.dto.DepartmentDto;
import edu.towson.cosc457.CarDealership.model.dto.EmployeeDto;
import edu.towson.cosc457.CarDealership.service.DepartmentService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final DepartmentMapper departmentMapper;
    private final EmployeeMapper employeeMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentController.class);

    @ApiOperation(value = "Add Department", response = DepartmentDto.class)
    @PostMapping
    public ResponseEntity<DepartmentDto> addDepartment(@RequestBody final DepartmentDto departmentDto) {
        LOGGER.info("POST /api/v1/departments/");
        Department department = departmentService.addDepartment(departmentMapper.fromDto(departmentDto));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(departmentMapper.toDto(department));
    }

    @ApiOperation(value = "Fetch All Departments", response = Iterable.class)
    @GetMapping
    public ResponseEntity<List<DepartmentDto>> getDepartments() {
        LOGGER.info("GET /api/v1/departments/");
        List<Department> departments = departmentService.getDepartments();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(departments.stream().map(departmentMapper::toDto).collect(Collectors.toList()));
    }

    @ApiOperation(value = "Fetch Department by Id", response = DepartmentDto.class)
    @GetMapping(value = "{id}")
    public ResponseEntity<DepartmentDto> getDepartment(@PathVariable final Long id) {
        LOGGER.info("GET /api/v1/departments/{}", id);
        Department department = departmentService.getDepartment(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(departmentMapper.toDto(department));
    }

    @ApiOperation(value = "Delete Department", response = DepartmentDto.class)
    @DeleteMapping(value = "{id}")
    public ResponseEntity<DepartmentDto> deleteDepartment(@PathVariable final Long id) {
        LOGGER.info("DELETE /api/v1/departments/{}", id);
        Department department = departmentService.deleteDepartment(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(departmentMapper.toDto(department));
    }

    @ApiOperation(value = "Update Department", response = DepartmentDto.class)
    @PutMapping(value = "{id}")
    public ResponseEntity<DepartmentDto> editDepartment(@PathVariable final Long id,
                                                        @RequestBody final DepartmentDto departmentDto) {
        LOGGER.info("PUT /api/v1/departments/{}", id);
        Department department = departmentService.editDepartment(id, departmentMapper.fromDto(departmentDto));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(departmentMapper.toDto(department));
    }

    @ApiOperation(value = "Fetch All Mechanics in Department", response = Iterable.class)
    @GetMapping(value = "{id}/mechanics")
    public ResponseEntity<List<EmployeeDto>> getMechanics(@PathVariable final Long id) {
        LOGGER.info("GET /api/v1/departments/{}/mechanics", id);
        Department department = departmentService.getDepartment(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(department.getMechanics().stream().map(employeeMapper::toDto).collect(Collectors.toList()));
    }

    @ApiOperation(value = "Add Mechanic to Department", response = DepartmentDto.class)
    @PostMapping(value = "{departmentId}/mechanics/{mechanicId}/add")
    public ResponseEntity<DepartmentDto> addMechanicToDepartment(@PathVariable final Long departmentId,
                                                                 @PathVariable final Long mechanicId) {
        LOGGER.info("POST /api/v1/departments/{}/mechanics/{}/add", departmentId, mechanicId);
        Department department = departmentService.assignMechanic(departmentId, mechanicId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(departmentMapper.toDto(department));
    }

    @ApiOperation(value = "Delete Mechanic from Department", response = DepartmentDto.class)
    @DeleteMapping(value = "{departmentId}/mechanics/{mechanicId}/remove")
    public ResponseEntity<DepartmentDto> removeMechanicFromDepartment(@PathVariable final Long departmentId,
                                                                      @PathVariable final Long mechanicId) {
        LOGGER.info("DELETE /api/v1/departments/{}/mechanics/{}/remove", departmentId, mechanicId);
        Department department = departmentService.removeMechanic(departmentId, mechanicId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(departmentMapper.toDto(department));
    }
}
