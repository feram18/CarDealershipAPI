package edu.towson.cosc457.CarDealership.model.dto;

import edu.towson.cosc457.CarDealership.model.Address;
import edu.towson.cosc457.CarDealership.model.Location;
import edu.towson.cosc457.CarDealership.model.SiteManager;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class LocationDto {
    private Long id;
    private String name;
    private Address address;
    private SiteManager siteManager;
    private List<LotDto> lotsDto = new ArrayList<>();
    private List<DepartmentDto> departmentsDto = new ArrayList<>();
    private List<MechanicDto> mechanicsDto = new ArrayList<>();
    private List<SalesAssociateDto> salesAssociatesDto = new ArrayList<>();

    public LocationDto() { }

    public static LocationDto from (Location location) {
        LocationDto locationDto = new LocationDto();
        locationDto.setId(location.getId());
        locationDto.setName(location.getName());
        locationDto.setAddress(location.getAddress());
        locationDto.setSiteManager(location.getSiteManager());
        locationDto.setLotsDto(location.getLots()
                .stream().map(LotDto::from).collect(Collectors.toList()));
        locationDto.setDepartmentsDto(location.getDepartments()
                .stream().map(DepartmentDto::from).collect(Collectors.toList()));
        locationDto.setMechanicsDto(location.getMechanics()
                .stream().map(MechanicDto::from).collect(Collectors.toList()));
        locationDto.setSalesAssociatesDto(location.getSalesAssociates()
                .stream().map(SalesAssociateDto::from).collect(Collectors.toList()));
        return locationDto;
    }
}
