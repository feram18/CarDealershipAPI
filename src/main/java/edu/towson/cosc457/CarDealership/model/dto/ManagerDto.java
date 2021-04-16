package edu.towson.cosc457.CarDealership.model.dto;

import edu.towson.cosc457.CarDealership.model.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ManagerDto extends EmployeeDto {
    private SiteManager siteManager;
    private Department department;
    private List<MechanicDto> mechanicsDto = new ArrayList<>();
    private List<SalesAssociateDto> salesAssociatesDto = new ArrayList<>();

    public static ManagerDto from (Manager manager) {
        ManagerDto managerDto = new ManagerDto();
        managerDto.setId(manager.getId());
        managerDto.setFirstName(manager.getFirstName());
        managerDto.setMiddleInitial(manager.getMiddleInitial());
        managerDto.setLastName(manager.getLastName());
        managerDto.setGender(manager.getGender());
        managerDto.setDateOfBirth(manager.getDateOfBirth());
        managerDto.setPhoneNumber(manager.getPhoneNumber());
        managerDto.setEmail(manager.getEmail());
        managerDto.setWorkLocation(manager.getWorkLocation());
        managerDto.setSalary(manager.getSalary());
        managerDto.setDateStarted(manager.getDateStarted());
        managerDto.setAddress(manager.getAddress());
        managerDto.setHoursWorked(manager.getHoursWorked());
        managerDto.setEmployeeType(manager.getEmployeeType());
        managerDto.setUsername(manager.getUsername());
        managerDto.setSiteManager(manager.getSiteManager());
        managerDto.setDepartment(manager.getDepartment());
        managerDto.setMechanicsDto(manager.getMechanics()
                .stream().map(MechanicDto::from).collect(Collectors.toList()));
        managerDto.setSalesAssociatesDto(manager.getSalesAssociates()
                .stream().map(SalesAssociateDto::from).collect(Collectors.toList()));
        return managerDto;
    }
}
