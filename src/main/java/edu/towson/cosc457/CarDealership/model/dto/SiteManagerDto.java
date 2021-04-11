package edu.towson.cosc457.CarDealership.model.dto;

import edu.towson.cosc457.CarDealership.model.Location;
import edu.towson.cosc457.CarDealership.model.SiteManager;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode(callSuper = true)
public class SiteManagerDto extends EmployeeDto {
    private Location location;
    private List<ManagerDto> managers = new ArrayList<>();

    public SiteManagerDto() { }

    public static SiteManagerDto from(SiteManager siteManager) {
        SiteManagerDto siteManagerDto = new SiteManagerDto();
        siteManagerDto.setId(siteManager.getId());
        siteManagerDto.setFirstName(siteManager.getFirstName());
        siteManagerDto.setMiddleInitial(siteManager.getMiddleInitial());
        siteManagerDto.setLastName(siteManager.getLastName());
        siteManagerDto.setGender(siteManager.getGender());
        siteManagerDto.setDateOfBirth(siteManager.getDateOfBirth());
        siteManagerDto.setPhoneNumber(siteManager.getPhoneNumber());
        siteManagerDto.setEmail(siteManager.getEmail());
        siteManagerDto.setWorkLocation(siteManager.getWorkLocation());
        siteManagerDto.setSalary(siteManager.getSalary());
        siteManagerDto.setDateStarted(siteManager.getDateStarted());
        siteManagerDto.setAddress(siteManager.getAddress());
        siteManagerDto.setHoursWorked(siteManager.getHoursWorked());
        siteManagerDto.setUsername(siteManager.getUsername());
        siteManagerDto.setLocation(siteManager.getLocation());
        siteManagerDto.setManagers(siteManager.getManagers()
                .stream().map(ManagerDto::from).collect(Collectors.toList()));
        return siteManagerDto;
    }
}
