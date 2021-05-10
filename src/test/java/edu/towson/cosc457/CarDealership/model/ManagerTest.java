package edu.towson.cosc457.CarDealership.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class ManagerTest {
    private Manager manager;
    private Mechanic mechanic;

    @BeforeEach
    void setUp() {
        manager = Manager.builder()
                .id(1L)
                .mechanics(new ArrayList<>())
                .build();

        mechanic = Mechanic.builder()
                .id(2L)
                .build();
    }

    @Test
    void shouldAssignMechanic() {
        manager.assignMechanics(mechanic);

        assertThat(manager.getMechanics()).usingRecursiveComparison()
                .isEqualTo(Collections.singletonList(mechanic));
    }

    @Test
    void shouldRemoveMechanics() {
        manager.assignMechanics(mechanic);
        manager.removeMechanics(mechanic);

        assertThat(manager.getMechanics().isEmpty()).isTrue();
    }

    @Test
    void shouldFailToRemoveMechanics() {
        manager.assignMechanics(mechanic);
        manager.removeMechanics(Mechanic.builder()
                .id(3L)
                .build());

        assertThat(manager.getMechanics().isEmpty()).isFalse();
    }
}
