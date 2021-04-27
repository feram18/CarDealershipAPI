package edu.towson.cosc457.CarDealership.mapper;

import edu.towson.cosc457.CarDealership.misc.TransmissionType;
import edu.towson.cosc457.CarDealership.misc.VehicleType;
import edu.towson.cosc457.CarDealership.model.Lot;
import edu.towson.cosc457.CarDealership.model.Vehicle;
import edu.towson.cosc457.CarDealership.model.dto.VehicleDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class VehicleMapperTest {
    private static VehicleMapper mapper;

    @BeforeAll
    public static void setUp() {
        mapper = new VehicleMapperImpl();
    }

    @Test
    void shouldMapToDto() {
        Vehicle vehicle = Vehicle.builder()
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

        VehicleDto vehicleDto = mapper.toDto(vehicle);

        assertAll(() -> {
           assertThat(vehicleDto).isInstanceOf(VehicleDto.class);
           assertEquals(vehicleDto.getId(), vehicle.getId());
           assertEquals(vehicleDto.getVin(), vehicle.getVin());
           assertEquals(vehicleDto.getMake(), vehicle.getMake());
           assertEquals(vehicleDto.getModel(), vehicle.getModel());
           assertEquals(vehicleDto.getYear(), vehicle.getYear());
           assertEquals(vehicleDto.getColor(), vehicle.getColor());
           assertEquals(vehicleDto.getType(), vehicle.getType());
           assertEquals(vehicleDto.getTransmission(), vehicle.getTransmission());
           assertEquals(vehicleDto.getFeatures(), vehicle.getFeatures());
           assertEquals(vehicleDto.getMpg(), vehicle.getMpg());
           assertEquals(vehicleDto.getMileage(), vehicle.getMileage());
           assertEquals(vehicleDto.getPrice(), vehicle.getPrice());
           assertEquals(vehicleDto.getLotId(), vehicle.getLot().getId());
        });
    }

    @Test
    void shouldMapFromDto() {
        VehicleDto vehicleDto = VehicleDto.builder()
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
                .build();

        Vehicle vehicle = mapper.fromDto(vehicleDto);

        assertAll(() -> {
            assertThat(vehicle).isInstanceOf(Vehicle.class);
            assertEquals(vehicle.getId(), vehicleDto.getId());
            assertEquals(vehicle.getVin(), vehicleDto.getVin());
            assertEquals(vehicle.getMake(), vehicleDto.getMake());
            assertEquals(vehicle.getModel(), vehicleDto.getModel());
            assertEquals(vehicle.getYear(), vehicleDto.getYear());
            assertEquals(vehicle.getColor(), vehicleDto.getColor());
            assertEquals(vehicle.getType(), vehicleDto.getType());
            assertEquals(vehicle.getTransmission(), vehicleDto.getTransmission());
            assertEquals(vehicle.getFeatures(), vehicleDto.getFeatures());
            assertEquals(vehicle.getMpg(), vehicleDto.getMpg());
            assertEquals(vehicle.getMileage(), vehicleDto.getMileage());
            assertEquals(vehicle.getPrice(), vehicleDto.getPrice());
        });
    }

    @Test
    void shouldReturnNullEntity() {
        Vehicle vehicle = mapper.fromDto(null);

        assertThat(vehicle).isEqualTo(null);
    }

    @Test
    void shouldReturnNullDto() {
        VehicleDto vehicleDto = mapper.toDto(null);

        assertThat(vehicleDto).isEqualTo(null);
    }
}
