package edu.towson.cosc457.CarDealership.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import edu.towson.cosc457.CarDealership.controller.AddressController;
import edu.towson.cosc457.CarDealership.model.Address;
import edu.towson.cosc457.CarDealership.service.AddressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class AddressControllerTest {
    public MockMvc mockMvc;
    @Autowired
    public AddressController addressController;
    @MockBean
    public AddressService addressService;
    private final ObjectMapper mapper = new ObjectMapper();
    private Address address;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(addressController).build();

        JavaTimeModule module = new JavaTimeModule();
        mapper.registerModule(module);

        address = Address.builder()
                .id(1L)
                .street("123 Main St.")
                .city("New York City")
                .state("New York")
                .zipCode(12345)
                .build();
    }

    @Test
    void shouldAddAddress() throws Exception {
        when(addressService.addAddress(address)).thenReturn(address);

        mockMvc.perform(post("/api/v1/addresses")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(address)))
                .andExpect(status().is(201));
    }

    @Test
    void shouldGetAllAddresses() throws Exception {
        when(addressService.getAddresses()).thenReturn(Collections.singletonList(address));

        mockMvc.perform(get("/api/v1/addresses"))
                .andExpect(status().is(200));
    }

    @Test
    void shouldGetAddressById() throws Exception {
        when(addressService.getAddress(address.getId())).thenReturn(address);

        mockMvc.perform(get("/api/v1/addresses/{id}", address.getId()))
                .andExpect(status().is(200));
    }

    @Test
    void shouldDeleteAddress() throws Exception {
        when(addressService.deleteAddress(address.getId())).thenReturn(address);

        mockMvc.perform(delete("/api/v1/addresses/{id}", address.getId()))
                .andExpect(status().is(200));
    }

    @Test
    void shouldUpdateAddress() throws Exception {
        Address editedAddress = Address.builder()
                .id(1L)
                .street("123 Main St.")
                .city("San Francisco")
                .state("California")
                .zipCode(71953)
                .build();
        when(addressService.editAddress(address.getId(), editedAddress)).thenReturn(editedAddress);

        mockMvc.perform(put("/api/v1/addresses/{id}", address.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(editedAddress)))
                .andExpect(status().is(200));
    }
}
