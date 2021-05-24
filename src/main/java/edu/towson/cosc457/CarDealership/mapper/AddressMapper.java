package edu.towson.cosc457.CarDealership.mapper;

import edu.towson.cosc457.CarDealership.model.Address;
import edu.towson.cosc457.CarDealership.model.dto.AddressDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    /**
     * Map from Address entity to AddressDTO
     * @param address Address object to be mapped to AddressDTO
     * @return mapped AddressDto object
     */
    AddressDto toDto(Address address);

    /**
     * Map from AddressDTO to Address entity
     * @param addressDto Map from AddressDTO to Address entity
     * @return mapped Address object
     */
    @InheritInverseConfiguration
    Address fromDto(AddressDto addressDto);
}
