package edu.towson.cosc457.CarDealership.controller;

import edu.towson.cosc457.CarDealership.model.ServiceTicket;
import edu.towson.cosc457.CarDealership.model.dto.CommentDto;
import edu.towson.cosc457.CarDealership.model.dto.ServiceTicketDto;
import edu.towson.cosc457.CarDealership.service.ServiceTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/tickets")
public class ServiceTicketController {
    private final ServiceTicketService serviceTicketService;

    @Autowired
    public ServiceTicketController(ServiceTicketService serviceTicketService) {
        this.serviceTicketService = serviceTicketService;
    }

    @PostMapping
    public ResponseEntity<ServiceTicketDto> addServiceTicket(@RequestBody final ServiceTicketDto serviceTicketDto) {
        ServiceTicket serviceTicket = serviceTicketService.addServiceTicket(ServiceTicket.from(serviceTicketDto));
        return new ResponseEntity<>(ServiceTicketDto.from(serviceTicket), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ServiceTicketDto>> getServiceTickets() {
        List<ServiceTicket> serviceTickets = serviceTicketService.getServiceTickets();
        List<ServiceTicketDto> serviceTicketsDto = serviceTickets.stream().map(ServiceTicketDto::from)
                .collect(Collectors.toList());
        return new ResponseEntity<>(serviceTicketsDto, HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<ServiceTicketDto> getServiceTicket(@PathVariable final Long id) {
        ServiceTicket serviceTicket = serviceTicketService.getServiceTicket(id);
        return new ResponseEntity<>(ServiceTicketDto.from(serviceTicket), HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<ServiceTicketDto> deleteServiceTicket(@PathVariable final Long id) {
        ServiceTicket serviceTicket = serviceTicketService.deleteServiceTicket(id);
        return new ResponseEntity<>(ServiceTicketDto.from(serviceTicket), HttpStatus.OK);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<ServiceTicketDto> editServiceTicket(@PathVariable final Long id,
                                                              @RequestBody final ServiceTicketDto serviceTicketDto) {
        ServiceTicket serviceTicket = serviceTicketService
                .editServiceTicket(id, ServiceTicket.from(serviceTicketDto));
        return new ResponseEntity<>(ServiceTicketDto.from(serviceTicket), HttpStatus.OK);
    }

    @GetMapping(value = "{id}/comments")
    public ResponseEntity<List<CommentDto>> getComments(@PathVariable final Long id) {
        ServiceTicket serviceTicket = serviceTicketService.getServiceTicket(id);
        List<CommentDto> commentsDto = serviceTicket.getComments()
                .stream().map(CommentDto::from).collect(Collectors.toList());
        return new ResponseEntity<>(commentsDto, HttpStatus.OK);
    }

    @PostMapping(value = "{id}/comments/{id}/add")
    public ResponseEntity<ServiceTicketDto> addCommentToTicket(@PathVariable final Long ticketId,
                                                               @PathVariable final Long commentId) {
        ServiceTicket serviceTicket = serviceTicketService.addCommentToTicket(ticketId, commentId);
        return new ResponseEntity<>(ServiceTicketDto.from(serviceTicket), HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}/comments/{id}/remove")
    public ResponseEntity<ServiceTicketDto> removeCommentFromTicket(@PathVariable final Long ticketId,
                                                                    @PathVariable final Long commentId) {
        ServiceTicket serviceTicket = serviceTicketService.removeCommentFromTicket(ticketId, commentId);
        return new ResponseEntity<>(ServiceTicketDto.from(serviceTicket), HttpStatus.OK);
    }
}
