package edu.towson.cosc457.CarDealership.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import edu.towson.cosc457.CarDealership.controller.ClientController;
import edu.towson.cosc457.CarDealership.misc.Gender;
import edu.towson.cosc457.CarDealership.model.Address;
import edu.towson.cosc457.CarDealership.model.Client;
import edu.towson.cosc457.CarDealership.model.SalesAssociate;
import edu.towson.cosc457.CarDealership.service.ClientService;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class ClientControllerTest {
    public MockMvc mockMvc;
    @Autowired
    public ClientController clientController;
    @MockBean
    public ClientService clientService;
    private final ObjectMapper mapper = new ObjectMapper();
    private Client client;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(clientController).build();

        client = Client.builder()
                .id(1L)
                .ssn("123-45-6789")
                .firstName("FirstName")
                .lastName("LastName")
                .gender(Gender.MALE)
                .email("email@company.com")
                .phoneNumber("123-456-7890")
                .address(Address.builder()
                        .id(1L)
                        .street("123 Main St.")
                        .city("New York City")
                        .state("New York")
                        .zipCode(12345)
                        .build())
                .salesAssociate(SalesAssociate.builder()
                        .id(1L)
                        .build())
                .minimumPrice(10000.01)
                .maximumPrice(15000.01)
                .build();
    }

    @Test
    void shouldAddClient() throws Exception {
        when(clientService.addClient(client)).thenReturn(client);

        mockMvc.perform(post("/api/v1/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(client)))
                .andExpect(status().is(201));
    }

    @Test
    void shouldGetAllClients() throws Exception {
        when(clientService.getClients()).thenReturn(Collections.singletonList(client));

        mockMvc.perform(get("/api/v1/clients"))
                .andExpect(status().is(200));
    }

    @Test
    void shouldGetClientById() throws Exception {
        when(clientService.getClient(client.getId())).thenReturn(client);

        mockMvc.perform(get("/api/v1/clients/{id}", client.getId()))
                .andExpect(status().is(200));
    }

    @Test
    void shouldDeleteClient() throws Exception {
        when(clientService.addClient(client)).thenReturn(client);

        mockMvc.perform(delete("/api/v1/clients/{id}", client.getId()))
                .andExpect(status().is(200));
    }

    @Test
    void shouldUpdateClient() throws Exception {
        Client editedClient = Client.builder()
                .id(1L)
                .ssn("123-45-6789")
                .firstName("NewName")
                .lastName("NewLName")
                .gender(Gender.MALE)
                .email("new-email@company.com")
                .phoneNumber("123-456-7890")
                .address(Address.builder()
                        .id(1L)
                        .street("123 Main St.")
                        .city("New York City")
                        .state("New York")
                        .zipCode(12345)
                        .build())
                .salesAssociate(SalesAssociate.builder()
                        .id(1L)
                        .build())
                .minimumPrice(21000.00)
                .maximumPrice(35000.00)
                .build();
        when(clientService.editClient(client.getId(), editedClient)).thenReturn(editedClient);

        mockMvc.perform(put("/api/v1/clients/{id}", client.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(editedClient)))
                .andExpect(status().is(200));
    }
}
