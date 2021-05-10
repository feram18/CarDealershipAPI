package edu.towson.cosc457.CarDealership.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class VehicleTest {
    private Vehicle vehicle;
    private final Lot lot = Lot.builder()
            .id(1L)
            .build();
    private ServiceTicket serviceTicket;

    @BeforeEach
    void setUp() {
        vehicle = Vehicle.builder()
                .id(1L)
                .lot(lot)
                .tickets(new ArrayList<>())
                .build();

        serviceTicket = ServiceTicket.builder()
                .id(1L)
                .build();
    }

    @Test
    void shouldGetVehicleLocation() {
        Location location = Location.builder()
                .id(1L)
                .lots(Collections.singletonList(lot))
                .build();

        lot.setLocation(location);

        assertThat(vehicle.getLocation()).usingRecursiveComparison().isEqualTo(location);
    }

    @Test
    void shouldAssignTicket() {
        vehicle.assignTicket(serviceTicket);

        assertThat(vehicle.getTickets()).usingRecursiveComparison()
                .isEqualTo(Collections.singletonList(serviceTicket));
    }

    @Test
    void shouldRemoveTicket() {
        vehicle.assignTicket(serviceTicket);
        vehicle.removeTicket(serviceTicket);

        assertThat(vehicle.getTickets().isEmpty()).isTrue();
    }
}
