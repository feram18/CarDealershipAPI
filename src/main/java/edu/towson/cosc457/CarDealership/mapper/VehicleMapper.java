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
    @Mapping(source = "lot.id", target = "lotId")
    VehicleDto toDto(Vehicle vehicle);

    @InheritInverseConfiguration
    Vehicle fromDto(VehicleDto vehicleDto);
}
