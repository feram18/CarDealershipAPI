package edu.towson.cosc457.CarDealership.controller;

import edu.towson.cosc457.CarDealership.mapper.CommentMapper;
import edu.towson.cosc457.CarDealership.mapper.ServiceTicketMapper;
import edu.towson.cosc457.CarDealership.model.ServiceTicket;
import edu.towson.cosc457.CarDealership.model.dto.CommentDto;
import edu.towson.cosc457.CarDealership.model.dto.ServiceTicketDto;
import edu.towson.cosc457.CarDealership.service.ServiceTicketService;
import lombok.RequiredArgsConstructor;
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

    @PostMapping
    public ResponseEntity<ServiceTicketDto> addServiceTicket(@RequestBody final ServiceTicketDto serviceTicketDto) {
        ServiceTicket serviceTicket = serviceTicketService
                .addServiceTicket(serviceTicketMapper.fromDto(serviceTicketDto));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(serviceTicketMapper.toDto(serviceTicket));
    }

    @GetMapping
    public ResponseEntity<List<ServiceTicketDto>> getServiceTickets() {
        List<ServiceTicket> serviceTickets = serviceTicketService.getServiceTickets();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(serviceTickets.stream().map(serviceTicketMapper::toDto).collect(Collectors.toList()));
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<ServiceTicketDto> getServiceTicket(@PathVariable final Long id) {
        ServiceTicket serviceTicket = serviceTicketService.getServiceTicket(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(serviceTicketMapper.toDto(serviceTicket));
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<ServiceTicketDto> deleteServiceTicket(@PathVariable final Long id) {
        ServiceTicket serviceTicket = serviceTicketService.deleteServiceTicket(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(serviceTicketMapper.toDto(serviceTicket));
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<ServiceTicketDto> editServiceTicket(@PathVariable final Long id,
                                                              @RequestBody final ServiceTicketDto serviceTicketDto) {
        ServiceTicket serviceTicket = serviceTicketService
                .editServiceTicket(id, serviceTicketMapper.fromDto(serviceTicketDto));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(serviceTicketMapper.toDto(serviceTicket));
    }

    @GetMapping(value = "{id}/comments")
    public ResponseEntity<List<CommentDto>> getComments(@PathVariable final Long id) {
        ServiceTicket serviceTicket = serviceTicketService.getServiceTicket(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(serviceTicket.getComments().stream().map(commentMapper::toDto).collect(Collectors.toList()));
    }

    @PostMapping(value = "{ticketId}/comments/{commentId}/add")
    public ResponseEntity<ServiceTicketDto> addCommentToTicket(@PathVariable final Long ticketId,
                                                               @PathVariable final Long commentId) {
        ServiceTicket serviceTicket = serviceTicketService.addCommentToTicket(ticketId, commentId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(serviceTicketMapper.toDto(serviceTicket));
    }

    @DeleteMapping(value = "{ticketId}/comments/{commentId}/remove")
    public ResponseEntity<ServiceTicketDto> removeCommentFromTicket(@PathVariable final Long ticketId,
                                                                    @PathVariable final Long commentId) {
        ServiceTicket serviceTicket = serviceTicketService.removeCommentFromTicket(ticketId, commentId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(serviceTicketMapper.toDto(serviceTicket));
    }
}
