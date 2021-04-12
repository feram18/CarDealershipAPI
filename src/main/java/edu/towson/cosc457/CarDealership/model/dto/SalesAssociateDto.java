package edu.towson.cosc457.CarDealership.model.dto;

import edu.towson.cosc457.CarDealership.model.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode(callSuper = true)
public class SalesAssociateDto extends EmployeeDto {
    private Manager manager;
    private Department department;
    private List<ClientDto> clientsDto = new ArrayList<>();

    public SalesAssociateDto() { }

    public static SalesAssociateDto from (SalesAssociate salesAssociate) {
        SalesAssociateDto salesAssociateDto = new SalesAssociateDto();
        salesAssociateDto.setId(salesAssociate.getId());
        salesAssociateDto.setFirstName(salesAssociate.getFirstName());
        salesAssociateDto.setMiddleInitial(salesAssociate.getMiddleInitial());
        salesAssociateDto.setLastName(salesAssociate.getLastName());
        salesAssociateDto.setGender(salesAssociate.getGender());
        salesAssociateDto.setDateOfBirth(salesAssociate.getDateOfBirth());
        salesAssociateDto.setPhoneNumber(salesAssociate.getPhoneNumber());
        salesAssociateDto.setEmail(salesAssociate.getEmail());
        salesAssociateDto.setWorkLocation(salesAssociate.getWorkLocation());
        salesAssociateDto.setSalary(salesAssociate.getSalary());
        salesAssociateDto.setDateStarted(salesAssociate.getDateStarted());
        salesAssociateDto.setAddress(salesAssociate.getAddress());
        salesAssociateDto.setHoursWorked(salesAssociate.getHoursWorked());
        salesAssociateDto.setEmployeeType(salesAssociate.getEmployeeType());
        salesAssociateDto.setUsername(salesAssociate.getUsername());
        salesAssociateDto.setManager(salesAssociate.getManager());
        salesAssociateDto.setDepartment(salesAssociate.getDepartment());
        salesAssociateDto.setClientsDto(salesAssociate.getClients()
                .stream().map(ClientDto::from).collect(Collectors.toList()));
        return salesAssociateDto;
    }
}
