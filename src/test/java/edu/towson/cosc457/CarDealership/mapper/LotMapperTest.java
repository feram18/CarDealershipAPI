package edu.towson.cosc457.CarDealership.mapper;

import edu.towson.cosc457.CarDealership.model.Location;
import edu.towson.cosc457.CarDealership.model.Lot;
import edu.towson.cosc457.CarDealership.model.dto.LotDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LotMapperTest {
    private static LotMapper mapper;

    @BeforeAll
    public static void setUp() {
        mapper = new LotMapperImpl();
    }

    @Test
    void shouldMapToDto() {
        Lot lot = Lot.builder()
                .id(1L)
                .size(100.15)
                .location(Location.builder()
                        .id(1L)
                        .build())
                .build();

        LotDto lotDto = mapper.toDto(lot);

        assertAll(() -> {
            assertThat(lotDto).isInstanceOf(LotDto.class);
            assertEquals(lotDto.getId(), lot.getId());
            assertEquals(lotDto.getSize(), lot.getSize());
            assertEquals(lotDto.getLocationId(), lot.getLocation().getId());
        });
    }

    @Test
    void shouldMapFromDto() {
        LotDto lotDto = LotDto.builder()
                .id(1L)
                .size(100.15)
                .locationId(1L)
                .build();

        Lot lot = mapper.fromDto(lotDto);

        assertAll(() -> {
            assertThat(lot).isInstanceOf(Lot.class);
            assertEquals(lot.getId(), lotDto.getId());
            assertEquals(lot.getSize(), lotDto.getSize());
        });
    }

    @Test
    void shouldReturnNullEntity() {
        Lot lot = mapper.fromDto(null);

        assertThat(lot).isEqualTo(null);
    }

    @Test
    void shouldReturnNullDto() {
        LotDto lotDto = mapper.toDto(null);

        assertThat(lotDto).isEqualTo(null);
    }
}
