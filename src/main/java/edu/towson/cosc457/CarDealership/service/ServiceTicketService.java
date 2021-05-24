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

    /**
     * Create a new ServiceTicket in the database
     * @param serviceTicket ServiceTicket object to be added to database
     * @return ServiceTicket saved on repository
     */
    public ServiceTicket addServiceTicket(ServiceTicket serviceTicket) {
        LOGGER.info("Create new Service Ticket in the database");
        return serviceTicketRepository.save(serviceTicket);
    }

    /**
     * Get All ServiceTickets
     * @return List of ServiceTickets
     */
    public List<ServiceTicket> getServiceTickets() {
        LOGGER.info("Get all Service Tickets");
        return StreamSupport
                .stream(serviceTicketRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    /**
     * Get ServiceTicket by Id
     * @param id identifier of ServiceTicket to be fetched
     * @return fetched ServiceTicket
     * @throws NotFoundException if no ServiceTicket with matching id found
     */
    public ServiceTicket getServiceTicket(Long id) {
        LOGGER.info("Get Service Ticket with id {}", id);
        return serviceTicketRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Entity.SERVICE_TICKET.toString(), id, HttpStatus.NOT_FOUND));
    }

    /**
     * Delete ServiceTicket by Id
     * @param id identifier of ServiceTicket to be deleted
     * @return deleted ServiceTicket
     * @throws NotFoundException if no ServiceTicket with matching id found
     */
    public ServiceTicket deleteServiceTicket(Long id) {
        LOGGER.info("Delete Service Ticket with id {}", id);
        ServiceTicket serviceTicket = getServiceTicket(id);
        serviceTicketRepository.delete(serviceTicket);
        return serviceTicket;
    }

    /**
     * Update ServiceTicket
     * @param id identifier of ServiceTicket to be updated
     * @param serviceTicket ServiceTicket object with updated fields
     * @return updated ServiceTicket
     * @throws NotFoundException if no ServiceTicket with matching id found
     */
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

    /**
     * Add Comment to ServiceTicket
     * @param ticketId identifier of ServiceTicket to be updated
     * @param commentId identifier of Comment to be assigned to ServiceTicket
     * @return updated ServiceTicket entity
     */
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

    /**
     * Remove Comment from ServiceTicket
     * @param ticketId identifier of ServiceTicket to be updated
     * @param commentId identifier of Comment to be removed from ServiceTicket
     * @return updated ServiceTicket entity
     */
    @Transactional
    public ServiceTicket removeCommentFromTicket(Long ticketId, Long commentId) {
        LOGGER.info("Remove Comment with id {} from Service Ticket with id {}", commentId, ticketId);
        ServiceTicket serviceTicket = getServiceTicket(ticketId);
        Comment comment = commentService.getComment(commentId);
        serviceTicket.removeComment(comment);
        return serviceTicket;
    }
}
