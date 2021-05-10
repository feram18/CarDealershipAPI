package edu.towson.cosc457.CarDealership.model;

import edu.towson.cosc457.CarDealership.misc.TransmissionType;
import edu.towson.cosc457.CarDealership.misc.VehicleType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class LotTest {
    private Lot lot;
    private Vehicle vehicle;

    @BeforeEach
    void setUp() {
        lot = Lot.builder()
                .id(1L)
                .size(450.05)
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
                .lot(null)
                .build();
    }

    @Test
    void shouldAddVehicleToLot() {
        lot.addVehicleToLot(vehicle);

        assertThat(lot.getVehicles()).usingRecursiveComparison()
                .isEqualTo(Collections.singletonList(vehicle));
    }

    @Test
    void shouldRemoveVehicleFromLot() {
        lot.addVehicleToLot(vehicle);
        lot.removeVehicleFromLot(vehicle);

        assertThat(lot.getVehicles().isEmpty()).isTrue();
    }
}
