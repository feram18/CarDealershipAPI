package edu.towson.cosc457.CarDealership.service;

import edu.towson.cosc457.CarDealership.exceptions.AlreadyAssignedException;
import edu.towson.cosc457.CarDealership.exceptions.NotFoundException;
import edu.towson.cosc457.CarDealership.misc.Entity;
import edu.towson.cosc457.CarDealership.model.Comment;
import edu.towson.cosc457.CarDealership.model.ServiceTicket;
import edu.towson.cosc457.CarDealership.repository.ServiceTicketRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class ServiceTicketService {
    private final ServiceTicketRepository serviceTicketRepository;
    private final CommentService commentService;
    private static final Logger LOGGER = LoggerFactory.getLogger(AddressService.class);

    public ServiceTicket addServiceTicket(ServiceTicket serviceTicket) {
        LOGGER.info("Create new Service Ticket in the database");
        return serviceTicketRepository.save(serviceTicket);
    }

    public List<ServiceTicket> getServiceTickets() {
        LOGGER.info("Get all Service Tickets");
        return StreamSupport
                .stream(serviceTicketRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public ServiceTicket getServiceTicket(Long id) {
        LOGGER.info("Get Service Ticket with id {}", id);
        return serviceTicketRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Entity.SERVICE_TICKET.toString(), id, HttpStatus.NOT_FOUND));
    }

    public ServiceTicket deleteServiceTicket(Long id) {
        LOGGER.info("Delete Service Ticket with id {}", id);
        ServiceTicket serviceTicket = getServiceTicket(id);
        serviceTicketRepository.delete(serviceTicket);
        return serviceTicket;
    }

    @Transactional
    public ServiceTicket editServiceTicket(Long id, ServiceTicket serviceTicket) {
        LOGGER.info("Update Service Ticket with id {}", id);
        ServiceTicket serviceTicketToEdit = getServiceTicket(id);
        serviceTicketToEdit.setVehicle(serviceTicket.getVehicle());
        serviceTicketToEdit.setMechanic(serviceTicket.getMechanic());
        serviceTicketToEdit.setDateCreated(serviceTicket.getDateCreated());
        serviceTicketToEdit.setDateUpdated(serviceTicket.getDateUpdated());
        serviceTicketToEdit.setStatus(serviceTicket.getStatus());
        serviceTicketToEdit.setComments(serviceTicket.getComments());
        return serviceTicketToEdit;
    }

    @Transactional
    public ServiceTicket addCommentToTicket(Long ticketId, Long commentId) {
        LOGGER.info("Add Comment with id {} to Service Ticket with id {}", commentId, ticketId);
        ServiceTicket ticket = getServiceTicket(ticketId);
        Comment comment = commentService.getComment(commentId);
        if(Objects.nonNull(comment.getServiceTicket())){
            throw new AlreadyAssignedException(
                    Entity.COMMENT.toString(),
                    commentId,
                    Entity.SERVICE_TICKET.toString(),
                    comment.getServiceTicket().getId(),
                    HttpStatus.BAD_REQUEST
            );
        }
        ticket.addComment(comment);
        comment.setServiceTicket(ticket);
        return ticket;
    }

    @Transactional
    public ServiceTicket removeCommentFromTicket(Long ticketId, Long commentId) {
        LOGGER.info("Remove Comment with id {} from Service Ticket with id {}", commentId, ticketId);
        ServiceTicket serviceTicket = getServiceTicket(ticketId);
        Comment comment = commentService.getComment(commentId);
        serviceTicket.removeComment(comment);
        return serviceTicket;
    }
}
