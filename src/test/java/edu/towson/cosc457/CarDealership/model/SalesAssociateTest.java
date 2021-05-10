package edu.towson.cosc457.CarDealership.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class SalesAssociateTest {
    private SalesAssociate salesAssociate;
    private Client client;

    @BeforeEach
    void setUp() {
        salesAssociate = SalesAssociate.builder()
                .id(1L)
                .clients(new ArrayList<>())
                .build();

        client = Client.builder()
                .id(1L)
                .build();
    }

    @Test
    void shouldAddClient() {
        salesAssociate.addClient(client);

        assertThat(salesAssociate.getClients()).usingRecursiveComparison()
                .isEqualTo(Collections.singletonList(client));
    }

    @Test
    void shouldRemoveClient() {
        salesAssociate.addClient(client);
        salesAssociate.removeClient(client);

        assertThat(salesAssociate.getClients().isEmpty()).isTrue();
    }
}
