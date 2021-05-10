package edu.towson.cosc457.CarDealership.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class MechanicTest {
    private Mechanic mechanic;
    private ServiceTicket serviceTicket;

    @BeforeEach
    void setUp() {
        mechanic = Mechanic.builder()
                .id(1L)
                .tickets(new ArrayList<>())
                .build();

        serviceTicket = ServiceTicket.builder()
                .id(1L)
                .build();
    }

    @Test
    void shouldAddServiceTicket() {
        mechanic.addServiceTicket(serviceTicket);

        assertThat(mechanic.getTickets()).usingRecursiveComparison()
                .isEqualTo(Collections.singletonList(serviceTicket));
    }

    @Test
    void shouldRemoveServiceTicket() {
        mechanic.addServiceTicket(serviceTicket);
        mechanic.removeServiceTicket(serviceTicket);

        assertThat(mechanic.getTickets().isEmpty()).isTrue();
    }
}
