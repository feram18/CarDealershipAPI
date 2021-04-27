package edu.towson.cosc457.CarDealership.mapper;

import edu.towson.cosc457.CarDealership.model.Department;
import edu.towson.cosc457.CarDealership.model.dto.DepartmentDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {
    @Mapping(source = "manager.id", target = "managerId")
    @Mapping(source = "location.id", target = "locationId")
    DepartmentDto toDto(Department department);

    @InheritInverseConfiguration
    Department fromDto(DepartmentDto departmentDto);
}
