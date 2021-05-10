package edu.towson.cosc457.CarDealership.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class DepartmentTest {
    private Department department;
    private Mechanic mechanic;

    @BeforeEach
    void setUp(){
        department = Department.builder()
                .id(1L)
                .name("Name")
                .manager(Manager.builder()
                        .id(1L)
                        .build())
                .location(Location.builder()
                        .id(1L)
                        .build())
                .mechanics(new ArrayList<>())
                .salesAssociates(new ArrayList<>())
                .build();

        mechanic = Mechanic.builder()
                .id(2L)
                .department(null)
                .build();
    }

    @Test
    void shouldAssignMechanic() {
        department.assignMechanic(mechanic);

        assertThat(department.getMechanics()).usingRecursiveComparison()
                .isEqualTo(Collections.singletonList(mechanic));
    }

    @Test
    void shouldRemoveMechanic() {
        department.assignMechanic(mechanic);
        department.removeMechanic(mechanic);

        assertThat(department.getMechanics().isEmpty()).isTrue();
    }
}
