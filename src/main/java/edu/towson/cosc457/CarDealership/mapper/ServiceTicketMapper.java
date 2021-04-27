package edu.towson.cosc457.CarDealership.mapper;

import edu.towson.cosc457.CarDealership.misc.Status;
import edu.towson.cosc457.CarDealership.model.ServiceTicket;
import edu.towson.cosc457.CarDealership.model.dto.ServiceTicketDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {Status.class, CommentMapper.class})
public interface ServiceTicketMapper {
    @Mapping(source = "vehicle.id", target = "vehicleId")
    @Mapping(source = "mechanic.id", target = "mechanicId")
    ServiceTicketDto toDto(ServiceTicket serviceTicket);

    @InheritInverseConfiguration
    ServiceTicket fromDto(ServiceTicketDto serviceTicketDto);
}
