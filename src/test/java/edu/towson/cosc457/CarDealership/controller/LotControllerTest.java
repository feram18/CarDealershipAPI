package edu.towson.cosc457.CarDealership.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.towson.cosc457.CarDealership.controller.LotController;
import edu.towson.cosc457.CarDealership.misc.TransmissionType;
import edu.towson.cosc457.CarDealership.misc.VehicleType;
import edu.towson.cosc457.CarDealership.model.Location;
import edu.towson.cosc457.CarDealership.model.Lot;
import edu.towson.cosc457.CarDealership.model.Vehicle;
import edu.towson.cosc457.CarDealership.service.LotService;
import edu.towson.cosc457.CarDealership.service.VehicleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class LotControllerTest {
    public MockMvc mockMvc;
    @Autowired
    public LotController lotController;
    @MockBean
    public LotService lotService;
    @MockBean
    public VehicleService vehicleService;
    private final ObjectMapper mapper = new ObjectMapper();
    private Lot lot;
    private Vehicle vehicle;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(lotController).build();

        lot = Lot.builder()
                .id(1L)
                .size(100.15)
                .location(Location.builder()
                        .id(1L)
                        .build())
                .vehicles(new ArrayList<>())
                .build();

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
                .build();
    }

    @Test
    void shouldAddLot() throws Exception {
        when(lotService.addLot(lot)).thenReturn(lot);

        mockMvc.perform(post("/api/v1/lots")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(lot)))
                .andExpect(status().is(201));
    }

    @Test
    void shouldGetAllLots() throws Exception {
        when(lotService.getLots()).thenReturn(Collections.singletonList(lot));

        mockMvc.perform(get("/api/v1/lots"))
                .andExpect(status().is(200));
    }

    @Test
    void shouldGetLotById() throws Exception {
        when(lotService.getLot(lot.getId())).thenReturn(lot);

        mockMvc.perform(get("/api/v1/lots/{id}", lot.getId()))
                .andExpect(status().is(200));
    }

    @Test
    void shouldDeleteLot() throws Exception {
        when(lotService.deleteLot(lot.getId())).thenReturn(lot);

        mockMvc.perform(delete("/api/v1/lots/{id}", lot.getId()))
                .andExpect(status().is(200));
    }

    @Test
    void shouldUpdateLot() throws Exception {
        Lot editedLot = Lot.builder()
                .id(1L)
                .size(325.65)
                .location(Location.builder()
                        .id(1L)
                        .build())
                .vehicles(new ArrayList<>())
                .build();
        when(lotService.editLot(lot.getId(), editedLot)).thenReturn(editedLot);

        mockMvc.perform(put("/api/v1/lots/{id}", lot.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(editedLot)))
                .andExpect(status().is(200));
    }

    @Test
    void shouldAddVehicleToLot() throws Exception {
        when(lotService.getLot(lot.getId())).thenReturn(lot);
        when(vehicleService.getVehicle(vehicle.getId())).thenReturn(vehicle);
        when(lotService.addVehicleToLot(lot.getId(), vehicle.getId())).thenReturn(lot);

        mockMvc.perform(post("/api/v1/lots/{lotId}/vehicles/{vehicleId}/add", lot.getId(), vehicle.getId()))
                .andExpect(status().is(200));
    }

    @Test
    void shouldGetVehicles() throws Exception {
        when(lotService.getLot(lot.getId())).thenReturn(lot);
        when(vehicleService.getVehicle(vehicle.getId())).thenReturn(vehicle);
        when(lotService.addVehicleToLot(lot.getId(), vehicle.getId())).thenReturn(lot);

        mockMvc.perform(get("/api/v1/lots/{id}/vehicles", lot.getId()))
                .andExpect(status().is(200));
    }

    @Test
    void shouldRemoveVehicleFromLot() throws Exception {
        when(lotService.getLot(lot.getId())).thenReturn(lot);
        when(vehicleService.getVehicle(vehicle.getId())).thenReturn(vehicle);
        when(lotService.addVehicleToLot(lot.getId(), vehicle.getId())).thenReturn(lot);
        when(lotService.removeVehicleFromLot(lot.getId(), vehicle.getId())).thenReturn(lot);

        mockMvc.perform(
                delete("/api/v1/lots/{lotId}/vehicles/{vehicleId}/remove",
                lot.getId(),
                vehicle.getId())).
                andExpect(status().is(200));
    }
}
