package edu.towson.cosc457.CarDealership.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import edu.towson.cosc457.CarDealership.exceptions.NotFoundException;
import edu.towson.cosc457.CarDealership.misc.Status;
import edu.towson.cosc457.CarDealership.misc.TransmissionType;
import edu.towson.cosc457.CarDealership.misc.VehicleType;
import edu.towson.cosc457.CarDealership.model.Lot;
import edu.towson.cosc457.CarDealership.model.Mechanic;
import edu.towson.cosc457.CarDealership.model.ServiceTicket;
import edu.towson.cosc457.CarDealership.model.Vehicle;
import edu.towson.cosc457.CarDealership.service.ServiceTicketService;
import edu.towson.cosc457.CarDealership.service.VehicleService;
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
public class VehicleControllerTest {
    public MockMvc mockMvc;
    @Autowired
    public VehicleController vehicleController;
    @MockBean
    public VehicleService vehicleService;
    @MockBean
    public ServiceTicketService serviceTicketService;
    private final ObjectMapper mapper = new ObjectMapper();
    private Vehicle vehicle;
    private Vehicle editedVehicle;
    private ServiceTicket serviceTicket;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(vehicleController).build();

        JavaTimeModule module = new JavaTimeModule();
        mapper.registerModule(module);

        vehicle = Vehicle.builder()
                .id(1L)
                .vin("JH4DA3340KS005705")
                .make("Make")
                .model("Model")
                .year(2021)
                .color("Green")
                .type(VehicleType.SEDAN)
                .transmission(TransmissionType.AUTOMATIC)
                .features("Sunroof")
                .mpg(25)
                .mileage(25)
                .price(29000.00)
                .lot(Lot.builder()
                        .id(1L)
                        .build())
                .tickets(new ArrayList<>())
                .build();

        editedVehicle = Vehicle.builder()
                .id(1L)
                .vin("JH4DA3340KS005705")
                .make("Make")
                .model("Model")
                .year(2021)
                .color("Black")
                .type(VehicleType.SEDAN)
                .transmission(TransmissionType.AUTOMATIC)
                .features("Leather seats")
                .mpg(25)
                .mileage(25)
                .price(35000.99)
                .lot(Lot.builder()
                        .id(2L)
                        .build())
                .tickets(new ArrayList<>())
                .build();

        serviceTicket = ServiceTicket.builder()
                .id(1L)
                .mechanic(Mechanic.builder()
                        .id(1L)
                        .build())
                .dateCreated(LocalDate.now())
                .dateUpdated(LocalDate.now())
                .status(Status.OPEN)
                .build();
    }

    @Test
    void shouldAddVehicle() throws Exception {
        when(vehicleService.addVehicle(vehicle)).thenReturn(vehicle);

        mockMvc.perform(post("/api/v1/vehicles")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(vehicle)))
                .andExpect(status().is(201));
    }

    @Test
    void shouldFailToAddVehicle_Null() throws Exception {
        mockMvc.perform(post("/api/v1/vehicles")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(null)))
                .andExpect(status().is(400)); // BAD_REQUEST
    }

    @Test
    void shouldGetAllVehicles() throws Exception {
        when(vehicleService.getVehicles()).thenReturn(Collections.singletonList(vehicle));

        mockMvc.perform(get("/api/v1/vehicles")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200));
    }

    @Test
    void shouldGetAllVehicles_EmptyList() throws Exception {
        when(vehicleService.getVehicles()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/v1/vehicles")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", empty()));
    }

    @Test
    void shouldGetVehicleById() throws Exception {
        when(vehicleService.getVehicle(vehicle.getId())).thenReturn(vehicle);

        mockMvc.perform(get("/api/v1/vehicles/{id}", vehicle.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", is(vehicle.getId().intValue())))
                .andExpect(jsonPath("$.vin", is(vehicle.getVin())))
                .andExpect(jsonPath("$.make", is(vehicle.getMake())))
                .andExpect(jsonPath("$.model", is(vehicle.getModel())))
                .andExpect(jsonPath("$.year", is(vehicle.getYear())))
                .andExpect(jsonPath("$.color", is(vehicle.getColor())))
                .andExpect(jsonPath("$.type", is(String.valueOf(vehicle.getType()))))
                .andExpect(jsonPath("$.transmission", is(String.valueOf(vehicle.getTransmission()))))
                .andExpect(jsonPath("$.features", is(vehicle.getFeatures())))
                .andExpect(jsonPath("$.mpg", is(vehicle.getMpg())))
                .andExpect(jsonPath("$.mileage", is(vehicle.getMileage())))
                .andExpect(jsonPath("$.price", is(vehicle.getPrice())))
                .andExpect(jsonPath("$.lotId", is(vehicle.getLot().getId().intValue())));
    }

    @Test
    void shouldFailToGetVehicleById_NotFound() throws Exception {
        when(vehicleService.getVehicle(vehicle.getId())).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/api/v1/vehicles/{id}", vehicle.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(404));
    }

    @Test
    void shouldDeleteVehicle() throws Exception {
        when(vehicleService.deleteVehicle(vehicle.getId())).thenReturn(vehicle);

        mockMvc.perform(delete("/api/v1/vehicles/{id}", vehicle.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200));
    }

    @Test
    void shouldFailToDeleteVehicle_NotFound() throws Exception {
        when(vehicleService.deleteVehicle(vehicle.getId())).thenThrow(NotFoundException.class);

        mockMvc.perform(delete("/api/v1/vehicles/{id}", vehicle.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(404));
    }

    @Test
    void shouldUpdateVehicle() throws Exception {
        when(vehicleService.editVehicle(vehicle.getId(), editedVehicle)).thenReturn(editedVehicle);

        mockMvc.perform(put("/api/v1/vehicles/{id}", vehicle.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(editedVehicle)))
                .andExpect(status().is(200));
    }

    @Test
    @Disabled
    void shouldFailToUpdateVehicle_NotFound() throws Exception {
        when(vehicleService.editVehicle(vehicle.getId(), editedVehicle)).thenThrow(NotFoundException.class);

        mockMvc.perform(put("/api/v1/vehicles/{id}", vehicle.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(editedVehicle)))
                .andExpect(status().is(404));
    }

    @Test
    void shouldAssignTicketToVehicle() throws Exception {
        when(vehicleService.getVehicle(vehicle.getId())).thenReturn(vehicle);
        when(serviceTicketService.getServiceTicket(vehicle.getId())).thenReturn(serviceTicket);
        when(vehicleService.assignTicket(vehicle.getId(), serviceTicket.getId())).thenReturn(vehicle);

        mockMvc.perform(post("/api/v1/vehicles/{vehicleId}/tickets/{ticketId}/add",
                        vehicle.getId(),
                        serviceTicket.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200));
    }

    @Test
    void shouldFailToAssignTicketToVehicle_NotFound() throws Exception {
        when(vehicleService.assignTicket(vehicle.getId(), serviceTicket.getId())).thenThrow(NotFoundException.class);

        mockMvc.perform(post("/api/v1/vehicles/{vehicleId}/tickets/{ticketId}/add",
                vehicle.getId(),
                serviceTicket.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(404));
    }

    @Test
    void shouldGetAssignedTickets() throws Exception {
        when(vehicleService.getVehicle(vehicle.getId())).thenReturn(vehicle);
        when(serviceTicketService.getServiceTicket(vehicle.getId())).thenReturn(serviceTicket);
        when(vehicleService.assignTicket(vehicle.getId(), vehicle.getId())).thenReturn(vehicle);

        mockMvc.perform(get("/api/v1/vehicles/{id}/tickets", vehicle.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200));
    }

    @Test
    void shouldGetAssignedTickets_EmptyList() throws Exception {
        when(vehicleService.getVehicle(vehicle.getId())).thenReturn(vehicle);

        mockMvc.perform(get("/api/v1/vehicles/{id}/tickets", vehicle.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", empty()));
    }

    @Test
    void shouldRemoveTicketFromVehicle() throws Exception {
        when(vehicleService.getVehicle(vehicle.getId())).thenReturn(vehicle);
        when(serviceTicketService.getServiceTicket(serviceTicket.getId())).thenReturn(serviceTicket);
        when(vehicleService.assignTicket(vehicle.getId(), serviceTicket.getId())).thenReturn(vehicle); // Assign ticket
        when(vehicleService.removeTicket(vehicle.getId(), serviceTicket.getId())).thenReturn(vehicle); // then remove

        mockMvc.perform(delete("/api/v1/vehicles/{vehicleId}/tickets/{ticketId}/remove",
                        vehicle.getId(),
                        serviceTicket.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200));
    }

    @Test
    void shouldFailToRemoveTicketFromVehicle_NotFound() throws Exception {
        when(vehicleService.removeTicket(vehicle.getId(), serviceTicket.getId())).thenThrow(NotFoundException.class);

        mockMvc.perform(delete("/api/v1/vehicles/{vehicleId}/tickets/{ticketId}/remove",
                vehicle.getId(),
                serviceTicket.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(404));
    }
}
