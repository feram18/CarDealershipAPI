package edu.towson.cosc457.CarDealership.controller;

import edu.towson.cosc457.CarDealership.mapper.CommentMapper;
import edu.towson.cosc457.CarDealership.mapper.ServiceTicketMapper;
import edu.towson.cosc457.CarDealership.model.ServiceTicket;
import edu.towson.cosc457.CarDealership.model.dto.CommentDto;
import edu.towson.cosc457.CarDealership.model.dto.ServiceTicketDto;
import edu.towson.cosc457.CarDealership.service.ServiceTicketService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/tickets")
@RequiredArgsConstructor
public class ServiceTicketController {
    private final ServiceTicketService serviceTicketService;
    private final ServiceTicketMapper serviceTicketMapper;
    private final CommentMapper commentMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceTicketController.class);

    @ApiOperation(value = "Add Service Ticket", response = ServiceTicketDto.class)
    @PostMapping
    public ResponseEntity<ServiceTicketDto> addServiceTicket(@RequestBody final ServiceTicketDto serviceTicketDto) {
        LOGGER.info("POST /api/v1/tickets/");
        ServiceTicket serviceTicket = serviceTicketService
                .addServiceTicket(serviceTicketMapper.fromDto(serviceTicketDto));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(serviceTicketMapper.toDto(serviceTicket));
    }

    @ApiOperation(value = "Fetch All Service Tickets", response = Iterable.class)
    @GetMapping
    public ResponseEntity<List<ServiceTicketDto>> getServiceTickets() {
        LOGGER.info("GET /api/v1/tickets/");
        List<ServiceTicket> serviceTickets = serviceTicketService.getServiceTickets();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(serviceTickets.stream().map(serviceTicketMapper::toDto).collect(Collectors.toList()));
    }

    @ApiOperation(value = "Fetch Service Ticket by Id", response = ServiceTicketDto.class)
    @GetMapping(value = "{id}")
    public ResponseEntity<ServiceTicketDto> getServiceTicket(@PathVariable final Long id) {
        LOGGER.info("GET /api/v1/tickets/{}", id);
        ServiceTicket serviceTicket = serviceTicketService.getServiceTicket(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(serviceTicketMapper.toDto(serviceTicket));
    }

    @ApiOperation(value = "Delete Service Ticket", response = ServiceTicketDto.class)
    @DeleteMapping(value = "{id}")
    public ResponseEntity<ServiceTicketDto> deleteServiceTicket(@PathVariable final Long id) {
        LOGGER.info("DELETE /api/v1/tickets/{}", id);
        ServiceTicket serviceTicket = serviceTicketService.deleteServiceTicket(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(serviceTicketMapper.toDto(serviceTicket));
    }

    @ApiOperation(value = "Update Service Ticket", response = ServiceTicketDto.class)
    @PutMapping(value = "{id}")
    public ResponseEntity<ServiceTicketDto> editServiceTicket(@PathVariable final Long id,
                                                              @RequestBody final ServiceTicketDto serviceTicketDto) {
        LOGGER.info("PUT /api/v1/tickets/{}", id);
        ServiceTicket serviceTicket = serviceTicketService
                .editServiceTicket(id, serviceTicketMapper.fromDto(serviceTicketDto));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(serviceTicketMapper.toDto(serviceTicket));
    }

    @ApiOperation(value = "Fetch All Comments in Service Ticket", response = Iterable.class)
    @GetMapping(value = "{id}/comments")
    public ResponseEntity<List<CommentDto>> getComments(@PathVariable final Long id) {
        LOGGER.info("GET /api/v1/tickets/{}/comments", id);
        ServiceTicket serviceTicket = serviceTicketService.getServiceTicket(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(serviceTicket.getComments().stream().map(commentMapper::toDto).collect(Collectors.toList()));
    }

    @ApiOperation(value = "Add Comment to Service Ticket", response = ServiceTicketDto.class)
    @PostMapping(value = "{ticketId}/comments/{commentId}/add")
    public ResponseEntity<ServiceTicketDto> addCommentToTicket(@PathVariable final Long ticketId,
                                                               @PathVariable final Long commentId) {
        LOGGER.info("POST /api/v1/tickets/{}/comments/{}/add", ticketId, commentId);
        ServiceTicket serviceTicket = serviceTicketService.addCommentToTicket(ticketId, commentId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(serviceTicketMapper.toDto(serviceTicket));
    }

    @ApiOperation(value = "Remove Comment from Service Ticket", response = ServiceTicketDto.class)
    @DeleteMapping(value = "{ticketId}/comments/{commentId}/remove")
    public ResponseEntity<ServiceTicketDto> removeCommentFromTicket(@PathVariable final Long ticketId,
                                                                    @PathVariable final Long commentId) {
        LOGGER.info("DELETE /api/v1/tickets/{}/comments/{}/remove", ticketId, commentId);
        ServiceTicket serviceTicket = serviceTicketService.removeCommentFromTicket(ticketId, commentId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(serviceTicketMapper.toDto(serviceTicket));
    }
}
