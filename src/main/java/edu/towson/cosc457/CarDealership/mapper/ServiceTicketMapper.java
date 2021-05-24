package edu.towson.cosc457.CarDealership.mapper;

import edu.towson.cosc457.CarDealership.misc.Status;
import edu.towson.cosc457.CarDealership.model.ServiceTicket;
import edu.towson.cosc457.CarDealership.model.dto.ServiceTicketDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {Status.class, CommentMapper.class})
public interface ServiceTicketMapper {
    /**
     * Map from ServiceTicket entity to ServiceTicketDTO
     * @param serviceTicket ServiceTicket object to be mapped to ServiceTicketDTO
     * @return mapped ServiceTicketDto object
     */
    @Mapping(source = "vehicle.id", target = "vehicleId")
    @Mapping(source = "mechanic.id", target = "mechanicId")
    ServiceTicketDto toDto(ServiceTicket serviceTicket);

    /**
     * Map from ServiceTicketDTO to ServiceTicket entity
     * @param serviceTicketDto Map from ServiceTicketDTO to ServiceTicket entity
     * @return mapped ServiceTicket object
     */
    @InheritInverseConfiguration
    ServiceTicket fromDto(ServiceTicketDto serviceTicketDto);
}
