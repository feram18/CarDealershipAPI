package edu.towson.cosc457.CarDealership.mapper;

import edu.towson.cosc457.CarDealership.model.Address;
import edu.towson.cosc457.CarDealership.model.Location;
import edu.towson.cosc457.CarDealership.model.SiteManager;
import edu.towson.cosc457.CarDealership.model.dto.AddressDto;
import edu.towson.cosc457.CarDealership.model.dto.LocationDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocationMapperTest {
    private static LocationMapper locationMapper;
    private static AddressMapper addressMapper;

    @BeforeAll
    public static void setUp() {
        locationMapper = new LocationMapperImpl();
        addressMapper = new AddressMapperImpl();
        ReflectionTestUtils.setField(locationMapper, "addressMapper", addressMapper);
    }

    @Test
    void shouldMapToDto() {
        Location location = Location.builder()
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
                .build();

        LocationDto locationDto = locationMapper.toDto(location);

        assertAll(() -> {
            assertThat(locationDto).isInstanceOf(LocationDto.class);
            assertEquals(locationDto.getId(), location.getId());
            assertEquals(locationDto.getName(), location.getName());
            assertEquals(locationDto.getAddress().getId(), location.getAddress().getId());
            assertEquals(locationDto.getAddress().getStreet(), location.getAddress().getStreet());
            assertEquals(locationDto.getAddress().getCity(), location.getAddress().getCity());
            assertEquals(locationDto.getAddress().getState(), location.getAddress().getState());
            assertEquals(locationDto.getAddress().getZipCode(), location.getAddress().getZipCode());
            assertEquals(locationDto.getSiteManagerId(), location.getSiteManager().getId());
        });
    }

    @Test
    void shouldMapFromDto() {
        LocationDto locationDto = LocationDto.builder()
                .id(1L)
                .name("Location A")
                .address(AddressDto.builder()
                        .id(1L)
                        .street("123 Main St.")
                        .city("New York City")
                        .state("New York")
                        .zipCode(12345)
                        .build())
                .siteManagerId(1L)
                .build();

        Location location = locationMapper.fromDto(locationDto);

        assertAll(() -> {
            assertThat(location).isInstanceOf(Location.class);
            assertEquals(location.getId(), locationDto.getId());
            assertEquals(location.getName(), locationDto.getName());
            assertEquals(location.getAddress().getId(), locationDto.getAddress().getId());
            assertEquals(location.getAddress().getStreet(), locationDto.getAddress().getStreet());
            assertEquals(location.getAddress().getCity(), locationDto.getAddress().getCity());
            assertEquals(location.getAddress().getState(), locationDto.getAddress().getState());
            assertEquals(location.getAddress().getZipCode(), locationDto.getAddress().getZipCode());
        });
    }

    @Test
    void shouldReturnNullEntity() {
        Location location = locationMapper.fromDto(null);

        assertThat(location).isEqualTo(null);
    }

    @Test
    void shouldReturnNullDto() {
        LocationDto locationDto = locationMapper.toDto(null);

        assertThat(locationDto).isEqualTo(null);
    }
}
