package edu.towson.cosc457.CarDealership.mapper;

import edu.towson.cosc457.CarDealership.model.Address;
import edu.towson.cosc457.CarDealership.model.dto.AddressDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    AddressDto toDto(Address address);

    @InheritInverseConfiguration
    Address fromDto(AddressDto addressDto);
}
