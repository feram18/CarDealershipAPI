package edu.towson.cosc457.CarDealership.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class EmployeeTest {
    private Manager manager;

    @Test
    void shouldCalculateYearsWorked() {
        LocalDate dateStarted = LocalDate.of(2017, 1, 1);
        manager = Manager.builder()
                .dateStarted(dateStarted)
                .build();

        assertThat(manager.getYearsWorked()).isEqualTo(4);
    }

    @Test
    void shouldCalculateAge() {
        LocalDate dateOfBirth = LocalDate.of(1970, 4, 21);

        manager = Manager.builder()
                .dateOfBirth(dateOfBirth)
                .build();

        assertThat(manager.getAge()).isEqualTo(51);
    }
}
