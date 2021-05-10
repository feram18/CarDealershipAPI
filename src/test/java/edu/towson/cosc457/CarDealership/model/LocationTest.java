package edu.towson.cosc457.CarDealership.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class LocationTest {
    private Location location;
    private Lot lot;
    private Department department;
    private Mechanic mechanic;

    @BeforeEach
    void setUp() {
        location = Location.builder()
                .id(1L)
                .name("Location A")
                .address(Address.builder()
                        .id(1L)
                        .street("123 Main St.")
                        .city("New York City")
                        .state("New York")
                        .zipCode(12345)
                        .build())
                .siteManager(SiteManager.builder()
                        .id(1L)
                        .build())
                .lots(new ArrayList<>())
                .departments(new ArrayList<>())
                .mechanics(new ArrayList<>())
                .build();

        lot = Lot.builder()
                .id(1L)
                .size(450.05)
                .location(null)
                .build();

        department = Department.builder()
                .id(1L)
                .name("Name")
                .manager(Manager.builder()
                        .id(1L)
                        .build())
                .location(null)
                .mechanics(new ArrayList<>())
                .salesAssociates(new ArrayList<>())
                .build();

        mechanic = Mechanic.builder()
                .id(2L)
                .workLocation(null)
                .build();
    }

    @Test
    void shouldAssignLot() {
        location.addLot(lot);

        assertThat(location.getLots()).usingRecursiveComparison()
                .isEqualTo(Collections.singletonList(lot));
    }

    @Test
    void shouldRemoveLot() {
        location.addLot(lot);
        location.removeLot(lot);

        assertThat(location.getLots().isEmpty()).isTrue();
    }

    @Test
    void shouldAddDepartment() {
        location.addDepartment(department);

        assertThat(location.getDepartments()).usingRecursiveComparison()
                .isEqualTo(Collections.singletonList(department));
    }

    @Test
    void shouldRemoveDepartment() {
        location.addDepartment(department);
        location.removeDepartment(department);

        assertThat(location.getDepartments().isEmpty()).isTrue();
    }

    @Test
    void shouldAssignMechanic() {
        location.assignMechanic(mechanic);

        assertThat(location.getMechanics()).usingRecursiveComparison()
                .isEqualTo(Collections.singletonList(mechanic));
    }

    @Test
    void shouldRemoveMechanic() {
        location.assignMechanic(mechanic);
        location.removeMechanic(mechanic);

        assertThat(location.getMechanics().isEmpty()).isTrue();
    }
}
