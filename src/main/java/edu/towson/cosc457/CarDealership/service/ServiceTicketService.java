package edu.towson.cosc457.CarDealership.service;

import edu.towson.cosc457.CarDealership.exceptions.AlreadyAssignedException;
import edu.towson.cosc457.CarDealership.exceptions.NotFoundException;
import edu.towson.cosc457.CarDealership.misc.Entity;
import edu.towson.cosc457.CarDealership.model.Comment;
import edu.towson.cosc457.CarDealership.model.ServiceTicket;
import edu.towson.cosc457.CarDealership.repository.ServiceTicketRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    public ServiceTicket addServiceTicket(ServiceTicket serviceTicket) {
        return serviceTicketRepository.save(serviceTicket);
    }

    public List<ServiceTicket> getServiceTickets() {
        return StreamSupport
                .stream(serviceTicketRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public ServiceTicket getServiceTicket(Long id) {
        return serviceTicketRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Entity.SERVICE_TICKET.toString(), id, HttpStatus.NOT_FOUND));
    }

    public ServiceTicket deleteServiceTicket(Long id) {
        ServiceTicket serviceTicket = getServiceTicket(id);
        serviceTicketRepository.delete(serviceTicket);
        return serviceTicket;
    }

    @Transactional
    public ServiceTicket editServiceTicket(Long id, ServiceTicket serviceTicket) {
        ServiceTicket serviceTicketToEdit = getServiceTicket(id);
        serviceTicketToEdit.setId(serviceTicket.getId());
        serviceTicketToEdit.setVehicle(serviceTicket.getVehicle());
        serviceTicketToEdit.setMechanic(serviceTicket.getMechanic());
        serviceTicketToEdit.setComments(serviceTicket.getComments());
        serviceTicketToEdit.setDateCreated(serviceTicket.getDateCreated());
        return serviceTicketToEdit;
    }

    @Transactional
    public ServiceTicket addCommentToTicket(Long ticketId, Long commentId) {
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
        ServiceTicket serviceTicket = getServiceTicket(ticketId);
        Comment comment = commentService.getComment(commentId);
        serviceTicket.removeComment(comment);
        return serviceTicket;
    }
}
