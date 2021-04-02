package edu.towson.cosc457.CarDealership.model.dto;

import edu.towson.cosc457.CarDealership.model.Department;
import edu.towson.cosc457.CarDealership.model.Location;
import edu.towson.cosc457.CarDealership.model.Manager;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class DepartmentDto {
    private Long id;
    private String name;
    private Manager manager;
    private Location location;
    private List<MechanicDto> mechanicsDto = new ArrayList<>();
    private List<SalesAssociateDto> salesAssociatesDto = new ArrayList<>();

    public static DepartmentDto from (Department department) {
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setId(department.getId());
        departmentDto.setName(department.getName());
        departmentDto.setManager(department.getManager());
        departmentDto.setLocation(department.getLocation());
        departmentDto.setMechanicsDto(department.getMechanics()
                .stream().map(MechanicDto::from).collect(Collectors.toList()));
        departmentDto.setSalesAssociatesDto(department.getSalesAssociates()
                .stream().map(SalesAssociateDto::from).collect(Collectors.toList()));
        return departmentDto;
    }
}
