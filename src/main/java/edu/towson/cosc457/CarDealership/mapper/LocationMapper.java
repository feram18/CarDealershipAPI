package edu.towson.cosc457.CarDealership.mapper;

import edu.towson.cosc457.CarDealership.model.Location;
import edu.towson.cosc457.CarDealership.model.dto.LocationDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = AddressMapper.class)
public interface LocationMapper {
    @Mapping(source = "siteManager.id", target = "siteManagerId")
    LocationDto toDto(Location location);

    @InheritInverseConfiguration
    Location fromDto(LocationDto locationDto);
}
