package edu.towson.cosc457.CarDealership.mapper;

import edu.towson.cosc457.CarDealership.misc.TransmissionType;
import edu.towson.cosc457.CarDealership.misc.VehicleType;
import edu.towson.cosc457.CarDealership.model.Vehicle;
import edu.towson.cosc457.CarDealership.model.dto.VehicleDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {VehicleType.class, TransmissionType.class})
public interface VehicleMapper {
    /**
     * Map from Vehicle entity to VehicleDTO
     * @param vehicle Vehicle object to be mapped to VehicleDTO
     * @return mapped VehicleDto object
     */
    @Mapping(source = "lot.id", target = "lotId")
    VehicleDto toDto(Vehicle vehicle);

    /**
     * Map from VehicleDTO to Vehicle entity
     * @param vehicleDto Map from VehicleDTO to Vehicle entity
     * @return mapped Vehicle object
     */
    @InheritInverseConfiguration
    Vehicle fromDto(VehicleDto vehicleDto);
}
