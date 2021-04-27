package edu.towson.cosc457.CarDealership.mapper;

import edu.towson.cosc457.CarDealership.model.Address;
import edu.towson.cosc457.CarDealership.model.dto.AddressDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddressMapperTest {
    private static AddressMapper mapper;

    @BeforeAll
    public static void setUp() {
        mapper = new AddressMapperImpl();
    }

    @Test
    @DisplayName("Test should pass when an Address entity is mapped to an AddressDTO")
    void shouldMapToDto() {
        Address address = Address.builder()
                .id(1L)
                .street("123 Main St.")
                .city("New York City")
                .state("New York")
                .zipCode(12345)
                .build();

        AddressDto addressDto = mapper.toDto(address);

        assertAll(() -> {
            assertThat(addressDto).isInstanceOf(AddressDto.class);
            assertEquals(addressDto.getId(), address.getId());
            assertEquals(addressDto.getStreet(), address.getStreet());
            assertEquals(addressDto.getCity(), address.getCity());
            assertEquals(addressDto.getState(), address.getState());
            assertEquals(addressDto.getZipCode(), address.getZipCode());
        });
    }

    @Test
    @DisplayName("Test should pass when an AddressDTO is mapped to Address entity")
    void shouldMapFromDto() {
        AddressDto addressDto = AddressDto.builder()
                .id(1L)
                .street("123 Main St.")
                .city("New York City")
                .state("New York")
                .zipCode(12345)
                .build();

        Address address = mapper.fromDto(addressDto);

        assertAll(() -> {
            assertThat(address).isInstanceOf(Address.class);
            assertEquals(address.getId(), addressDto.getId());
            assertEquals(address.getStreet(), addressDto.getStreet());
            assertEquals(address.getCity(), addressDto.getCity());
            assertEquals(address.getState(), addressDto.getState());
            assertEquals(address.getZipCode(), addressDto.getZipCode());
        });
    }

    @Test
    void shouldReturnNullEntity() {
        Address address = mapper.fromDto(null);

        assertThat(address).isEqualTo(null);
    }

    @Test
    void shouldReturnNullDto() {
        AddressDto addressDto = mapper.toDto(null);

        assertThat(addressDto).isEqualTo(null);
    }
}
