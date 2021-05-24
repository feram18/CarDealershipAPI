package edu.towson.cosc457.CarDealership.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import edu.towson.cosc457.CarDealership.exceptions.NotFoundException;
import edu.towson.cosc457.CarDealership.misc.Status;
import edu.towson.cosc457.CarDealership.model.Comment;
import edu.towson.cosc457.CarDealership.model.Mechanic;
import edu.towson.cosc457.CarDealership.model.ServiceTicket;
import edu.towson.cosc457.CarDealership.model.Vehicle;
import edu.towson.cosc457.CarDealership.service.CommentService;
import edu.towson.cosc457.CarDealership.service.ServiceTicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class ServiceTicketControllerTest {
    public MockMvc mockMvc;
    @Autowired
    public ServiceTicketController serviceTicketController;
    @MockBean
    public ServiceTicketService serviceTicketService;
    @MockBean
    public CommentService commentService;
    private final ObjectMapper mapper = new ObjectMapper();
    private ServiceTicket serviceTicket;
    private ServiceTicket editedServiceTicket;
    private Comment comment;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(serviceTicketController).build();

        JavaTimeModule module = new JavaTimeModule();
        mapper.registerModule(module);

        serviceTicket = ServiceTicket.builder()
                .id(1L)
                .vehicle(Vehicle.builder()
                        .id(1L)
                        .build())
                .mechanic(Mechanic.builder()
                        .id(1L)
                        .build())
                .dateCreated(LocalDate.now())
                .dateUpdated(LocalDate.now())
                .status(Status.OPEN)
                .comments(new ArrayList<>())
                .build();

        editedServiceTicket = ServiceTicket.builder()
                .id(1L)
                .vehicle(Vehicle.builder()
                        .id(1L)
                        .build())
                .mechanic(Mechanic.builder()
                        .id(2L)
                        .build())
                .dateCreated(LocalDate.now())
                .dateUpdated(LocalDate.now())
                .status(Status.PENDING)
                .comments(new ArrayList<>())
                .build();

        comment = Comment.builder()
                .id(1L)
                .mechanic(Mechanic.builder()
                        .id(1L)
                        .build())
                .dateCreated(LocalDate.now())
                .content("Content")
                .build();
    }

    @Test
    void shouldAddServiceTicket() throws Exception {
        when(serviceTicketService.addServiceTicket(serviceTicket)).thenReturn(serviceTicket);

        mockMvc.perform(post("/api/v1/tickets")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(serviceTicket)))
                .andExpect(status().is(201));
    }

    @Test
    void shouldFailToAddServiceTicket_Null() throws Exception {
        mockMvc.perform(post("/api/v1/tickets")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(null)))
                .andExpect(status().is(400)); // BAD_REQUEST
    }

    @Test
    void shouldGetAllServiceTickets() throws Exception {
        when(serviceTicketService.getServiceTickets()).thenReturn(Collections.singletonList(serviceTicket));

        mockMvc.perform(get("/api/v1/tickets")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200));
    }

    @Test
    void shouldGetAllServiceTickets_EmptyList() throws Exception {
        when(serviceTicketService.getServiceTickets()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/v1/tickets")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", empty()));
    }

    @Test
    void shouldGetServiceTicketById() throws Exception {
        when(serviceTicketService.getServiceTicket(serviceTicket.getId())).thenReturn(serviceTicket);

        mockMvc.perform(get("/api/v1/tickets/{id}", serviceTicket.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", is(serviceTicket.getId().intValue())))
                .andExpect(jsonPath("$.vehicleId", is(serviceTicket.getVehicle().getId().intValue())))
                .andExpect(jsonPath("$.mechanicId", is(serviceTicket.getMechanic().getId().intValue())))
                .andExpect(jsonPath("$.dateCreated.[0]", is(serviceTicket.getDateCreated().getYear())))
                .andExpect(jsonPath("$.dateCreated.[1]", is(serviceTicket.getDateCreated().getMonthValue())))
                .andExpect(jsonPath("$.dateCreated.[2]", is(serviceTicket.getDateCreated().getDayOfMonth())))
                .andExpect(jsonPath("$.dateUpdated.[0]", is(serviceTicket.getDateUpdated().getYear())))
                .andExpect(jsonPath("$.dateUpdated.[1]", is(serviceTicket.getDateUpdated().getMonthValue())))
                .andExpect(jsonPath("$.dateUpdated.[2]", is(serviceTicket.getDateUpdated().getDayOfMonth())))
                .andExpect(jsonPath("$.status", is(String.valueOf(serviceTicket.getStatus()))))
                .andExpect(jsonPath("$.comments", hasSize(0)));
    }

    @Test
    void shouldFailToGetServiceTicketById_NotFound() throws Exception {
        when(serviceTicketService.getServiceTicket(serviceTicket.getId())).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/api/v1/tickets/{id}", serviceTicket.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(404));
    }

    @Test
    void shouldDeleteServiceTicket() throws Exception {
        when(serviceTicketService.deleteServiceTicket(serviceTicket.getId())).thenReturn(serviceTicket);

        mockMvc.perform(delete("/api/v1/tickets/{id}", serviceTicket.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200));
    }

    @Test
    void shouldFailToDeleteServiceTicket_NotFound() throws Exception {
        when(serviceTicketService.deleteServiceTicket(serviceTicket.getId())).thenThrow(NotFoundException.class);

        mockMvc.perform(delete("/api/v1/tickets/{id}", serviceTicket.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(404));
    }

    @Test
    void shouldUpdateServiceTicket() throws Exception {
        when(serviceTicketService.editServiceTicket(serviceTicket.getId(),editedServiceTicket))
                .thenReturn(editedServiceTicket);

        mockMvc.perform(put("/api/v1/tickets/{id}", serviceTicket.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(editedServiceTicket)))
                .andExpect(status().is(200));
    }

    @Test
    @Disabled
    void shouldFailToUpdateServiceTicket_NotFound() throws Exception {
        when(serviceTicketService.editServiceTicket(serviceTicket.getId(),editedServiceTicket))
                .thenThrow(NotFoundException.class);

        mockMvc.perform(put("/api/v1/tickets/{id}", serviceTicket.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(editedServiceTicket)))
                .andExpect(status().is(404));
    }

    @Test
    void shouldAddCommentToTicket() throws Exception {
        when(serviceTicketService.getServiceTicket(serviceTicket.getId())).thenReturn(serviceTicket);
        when(commentService.getComment(comment.getId())).thenReturn(comment);
        when(serviceTicketService.addCommentToTicket(serviceTicket.getId(), comment.getId()))
                .thenReturn(serviceTicket);

        mockMvc.perform(post("/api/v1/tickets/{ticketId}/comments/{commentId}/add",
                serviceTicket.getId(),
                comment.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200));
    }

    @Test
    void shouldFailToAddCommentToTicket_NotFound() throws Exception {
        when(serviceTicketService.addCommentToTicket(serviceTicket.getId(), comment.getId()))
                .thenThrow(NotFoundException.class);

        mockMvc.perform(post("/api/v1/tickets/{ticketId}/comments/{commentId}/add",
                serviceTicket.getId(),
                comment.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(404));
    }

    @Test
    void shouldGetCommentsInTicket() throws Exception {
        when(serviceTicketService.getServiceTicket(serviceTicket.getId())).thenReturn(serviceTicket);
        when(commentService.getComment(comment.getId())).thenReturn(comment);
        when(serviceTicketService.addCommentToTicket(serviceTicket.getId(), comment.getId()))
                .thenReturn(serviceTicket);

        mockMvc.perform(get("/api/v1/tickets/{id}/comments", serviceTicket.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200));
    }

    @Test
    void shouldGetCommentsInTicket_EmptyList() throws Exception {
        when(serviceTicketService.getServiceTicket(serviceTicket.getId())).thenReturn(serviceTicket);

        mockMvc.perform(get("/api/v1/tickets/{id}/comments", serviceTicket.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", empty()));
    }

    @Test
    void shouldRemoveCommentFromTicket() throws Exception {
        when(serviceTicketService.getServiceTicket(serviceTicket.getId())).thenReturn(serviceTicket);
        when(commentService.getComment(comment.getId())).thenReturn(comment);
        when(serviceTicketService.addCommentToTicket(serviceTicket.getId(), comment.getId()))
                .thenReturn(serviceTicket);
        when(serviceTicketService.removeCommentFromTicket(serviceTicket.getId(), comment.getId()))
                .thenReturn(serviceTicket);

        mockMvc.perform(delete("/api/v1/tickets/{ticketId}/comments/{commentId}/remove",
                serviceTicket.getId(),
                comment.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200));
    }

    @Test
    void shouldFailToRemoveCommentFromTicket_NotFound() throws Exception {
        when(serviceTicketService.removeCommentFromTicket(serviceTicket.getId(), comment.getId()))
                .thenThrow(NotFoundException.class);

        mockMvc.perform(delete("/api/v1/tickets/{ticketId}/comments/{commentId}/remove",
                serviceTicket.getId(),
                comment.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(404));
    }
}
