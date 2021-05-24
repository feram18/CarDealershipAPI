package edu.towson.cosc457.CarDealership.mapper;

import edu.towson.cosc457.CarDealership.model.Department;
import edu.towson.cosc457.CarDealership.model.dto.DepartmentDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {
    /**
     * Map from Department entity to DepartmentDTO
     * @param department Department object to be mapped to DepartmentDTO
     * @return mapped DepartmentDto object
     */
    @Mapping(source = "manager.id", target = "managerId")
    @Mapping(source = "location.id", target = "locationId")
    DepartmentDto toDto(Department department);

    /**
     * Map from DepartmentDTO to Department entity
     * @param departmentDto DepartmentDTO object to be mapped to Department entity
     * @return mapped Department object
     */
    @InheritInverseConfiguration
    Department fromDto(DepartmentDto departmentDto);
}
