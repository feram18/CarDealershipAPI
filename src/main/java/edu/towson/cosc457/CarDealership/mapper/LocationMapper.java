package edu.towson.cosc457.CarDealership.mapper;

import edu.towson.cosc457.CarDealership.model.Location;
import edu.towson.cosc457.CarDealership.model.dto.LocationDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = AddressMapper.class)
public interface LocationMapper {
    /**
     * Map from Location entity to LocationDTO
     * @param location Location object to be mapped to LocationDTO
     * @return mapped LocationDto object
     */
    @Mapping(source = "siteManager.id", target = "siteManagerId")
    LocationDto toDto(Location location);

    /**
     * Map from LocationDTO to Location entity
     * @param locationDto LocationDTO object to be mapped to Location entity
     * @return mapped Location object
     */
    @InheritInverseConfiguration
    Location fromDto(LocationDto locationDto);
}
