package edu.towson.cosc457.CarDealership.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.towson.cosc457.CarDealership.exceptions.NotFoundException;
import edu.towson.cosc457.CarDealership.misc.Gender;
import edu.towson.cosc457.CarDealership.model.Address;
import edu.towson.cosc457.CarDealership.model.Client;
import edu.towson.cosc457.CarDealership.model.SalesAssociate;
import edu.towson.cosc457.CarDealership.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Collections;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
    private Client editedClient;

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

        editedClient = Client.builder()
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
    }

    @Test
    void shouldAddClient() throws Exception {
        when(clientService.addClient(client)).thenReturn(client);

        mockMvc.perform(post("/api/v1/clients")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(client)))
                .andExpect(status().is(201));
    }

    @Test
    void shouldFailToAddClient_Null() throws Exception {
        mockMvc.perform(post("/api/v1/clients")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(null)))
                .andExpect(status().is(400)); // BAD_REQUEST
    }

    @Test
    void shouldGetAllClients() throws Exception {
        when(clientService.getClients()).thenReturn(Collections.singletonList(client));

        mockMvc.perform(get("/api/v1/clients")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.size()", is(1)));
    }

    @Test
    void shouldGetAllClients_EmptyList() throws Exception {
        when(clientService.getClients()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/v1/clients")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", empty()));
    }

    @Test
    void shouldGetClientById() throws Exception {
        when(clientService.getClient(client.getId())).thenReturn(client);

        mockMvc.perform(get("/api/v1/clients/{id}", client.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", is(client.getId().intValue())))
                .andExpect(jsonPath("$.firstName", is(client.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(client.getLastName())))
                .andExpect(jsonPath("$.gender", is(String.valueOf(client.getGender()))))
                .andExpect(jsonPath("$.email", is(client.getEmail())))
                .andExpect(jsonPath("$.phoneNumber", is(client.getPhoneNumber())))
                .andExpect(jsonPath("$.address").isMap())
                .andExpect(jsonPath("$.address.id", is(client.getAddress().getId().intValue())))
                .andExpect(jsonPath("$.salesAssociateId", is(client.getSalesAssociate().getId().intValue())))
                .andExpect(jsonPath("$.minimumPrice", is(client.getMinimumPrice())))
                .andExpect(jsonPath("$.maximumPrice", is(client.getMaximumPrice())));
    }

    @Test
    void shouldFailToGetClientById_NotFound() throws Exception {
        when(clientService.getClient(client.getId())).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/api/v1/clients/{id}", client.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(404));
    }

    @Test
    void shouldDeleteClient() throws Exception {
        when(clientService.deleteClient(client.getId())).thenReturn(client);

        mockMvc.perform(delete("/api/v1/clients/{id}", client.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200));
    }

    @Test
    void shouldFailToDeleteClient_NotFound() throws Exception {
        when(clientService.deleteClient(client.getId())).thenThrow(NotFoundException.class);

        mockMvc.perform(delete("/api/v1/clients/{id}", client.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(404));
    }

    @Test
    void shouldUpdateClient() throws Exception {
        when(clientService.editClient(client.getId(), editedClient)).thenReturn(editedClient);

        mockMvc.perform(put("/api/v1/clients/{id}", client.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(editedClient)))
                .andExpect(status().is(200));
    }

    @Test
    @Disabled
    void shouldFailToUpdateClient_NotFound() throws Exception {
        when(clientService.editClient(client.getId(), editedClient)).thenThrow(NotFoundException.class);

        mockMvc.perform(put("/api/v1/clients/{id}", client.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(editedClient)))
                .andExpect(status().is(404));
    }
}
