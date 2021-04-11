package edu.towson.cosc457.CarDealership.model.dto;

import edu.towson.cosc457.CarDealership.misc.Status;
import edu.towson.cosc457.CarDealership.model.Mechanic;
import edu.towson.cosc457.CarDealership.model.ServiceTicket;
import edu.towson.cosc457.CarDealership.model.Vehicle;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ServiceTicketDto {
    private Long id;
    private Vehicle vehicle;
    private Mechanic mechanic;
    private LocalDate dateCreated;
    private LocalDate dateUpdated;
    private Status status;
    private List<CommentDto> comments = new ArrayList<>();

    public ServiceTicketDto() { }

    public static ServiceTicketDto from (ServiceTicket serviceTicket) {
        ServiceTicketDto serviceTicketDto = new ServiceTicketDto();
        serviceTicketDto.setId(serviceTicket.getId());
        serviceTicketDto.setVehicle(serviceTicket.getVehicle());
        serviceTicketDto.setMechanic(serviceTicket.getMechanic());
        serviceTicketDto.setDateCreated(serviceTicket.getDateCreated());
        serviceTicketDto.setDateUpdated(serviceTicket.getDateUpdated());
        serviceTicketDto.setStatus(serviceTicket.getStatus());
        serviceTicketDto.setComments(serviceTicket.getComments()
                .stream().map(CommentDto::from).collect(Collectors.toList()));
        return serviceTicketDto;
    }
}
