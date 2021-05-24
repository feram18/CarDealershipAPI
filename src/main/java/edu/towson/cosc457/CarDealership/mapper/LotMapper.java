package edu.towson.cosc457.CarDealership.mapper;

import edu.towson.cosc457.CarDealership.model.Lot;
import edu.towson.cosc457.CarDealership.model.dto.LotDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LotMapper {
    /**
     * Map from Lot entity to LotDTO
     * @param lot Lot object to be mapped to LotDTO
     * @return mapped LotDto object
     */
    @Mapping(source = "location.id", target = "locationId")
    LotDto toDto(Lot lot);

    /**
     * Map from LotDTO to Lot entity
     * @param lotDto LotDTO object to be mapped to Lot entity
     * @return mapped Lot object
     */
    @InheritInverseConfiguration
    Lot fromDto(LotDto lotDto);
}
