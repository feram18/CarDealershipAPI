package edu.towson.cosc457.CarDealership.mapper;

import edu.towson.cosc457.CarDealership.model.Lot;
import edu.towson.cosc457.CarDealership.model.dto.LotDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LotMapper {
    @Mapping(source = "location.id", target = "locationId")
    LotDto toDto(Lot lot);

    @InheritInverseConfiguration
    Lot fromDto(LotDto lotDto);
}
