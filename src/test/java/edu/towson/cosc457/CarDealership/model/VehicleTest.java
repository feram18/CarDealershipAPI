package edu.towson.cosc457.CarDealership.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

public class VehicleTest {
    @Test
    void shouldGetVehicleLocation() {
        Location location = Location.builder()
                .id(1L)
                .lots(new ArrayList<>())
                .build();

        Lot lot = Lot.builder()
                .id(1L)
                .location(location)
                .build();

        location.addLot(lot);

        Vehicle vehicle = Vehicle.builder()
                .lot(lot)
                .build();

        assertThat(vehicle.getLocation()).usingRecursiveComparison().isEqualTo(location);
    }
}
