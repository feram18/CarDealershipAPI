package edu.towson.cosc457.CarDealership.mapper;

import edu.towson.cosc457.CarDealership.misc.Gender;
import edu.towson.cosc457.CarDealership.model.Address;
import edu.towson.cosc457.CarDealership.model.Client;
import edu.towson.cosc457.CarDealership.model.SalesAssociate;
import edu.towson.cosc457.CarDealership.model.dto.AddressDto;
import edu.towson.cosc457.CarDealership.model.dto.ClientDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClientMapperTest {
    private static ClientMapper clientMapper;
    private static AddressMapper addressMapper;

    @BeforeAll
    public static void setUp() {
        clientMapper = new ClientMapperImpl();
        addressMapper = new AddressMapperImpl();
        ReflectionTestUtils.setField(clientMapper, "addressMapper", addressMapper);
    }

    @Test
    void shouldMapToDto() {
        Client client = Client.builder()
                .id(1L)
                .ssn("123-45-6789")
                .firstName("FirstName")
                .lastName("LastName")
                .gender(Gender.MALE)
                .email("email@company.com")
                .phoneNumber("123-456-7890")
                .address(Address.builder()
                        .id(1L)
                        .street("123 Main St.")
                        .city("New York City")
                        .state("New York")
                        .zipCode(12345)
                        .build())
                .salesAssociate(SalesAssociate.builder()
                        .id(1L)
                        .build())
                .minimumPrice(10000.01)
                .maximumPrice(15000.01)
                .build();

        ClientDto clientDto = clientMapper.toDto(client);

        assertAll(() -> {
            assertThat(clientDto).isInstanceOf(ClientDto.class);
            assertEquals(clientDto.getId(), client.getId());
            assertEquals(clientDto.getFirstName(), client.getFirstName());
            assertEquals(clientDto.getLastName(), client.getLastName());
            assertEquals(clientDto.getGender(), client.getGender());
            assertEquals(clientDto.getEmail(), client.getEmail());
            assertEquals(clientDto.getPhoneNumber(), client.getPhoneNumber());
            assertEquals(clientDto.getAddress().getId(), client.getAddress().getId());
            assertEquals(clientDto.getSalesAssociateId(), client.getSalesAssociate().getId());
            assertEquals(clientDto.getMinimumPrice(), client.getMinimumPrice());
            assertEquals(clientDto.getMaximumPrice(), client.getMaximumPrice());
        });
    }

    @Test
    void shouldMapFromDto() {
        ClientDto clientDto = ClientDto.builder()
                .id(1L)
                .firstName("FirstName")
                .lastName("LastName")
                .gender(Gender.FEMALE)
                .email("client@company.com")
                .phoneNumber("123-456-0000")
                .address(AddressDto.builder()
                        .id(1L)
                        .street("123 Main St.")
                        .city("New York City")
                        .state("New York")
                        .zipCode(12345)
                        .build())
                .salesAssociateId(1L)
                .minimumPrice(1000.01)
                .maximumPrice(1500.05)
                .build();

        Client client = clientMapper.fromDto(clientDto);

        assertAll(() -> {
            assertThat(client).isInstanceOf(Client.class);
            assertEquals(client.getId(), clientDto.getId());
            assertEquals(client.getFirstName(), clientDto.getFirstName());
            assertEquals(client.getLastName(), clientDto.getLastName());
            assertEquals(client.getGender(), clientDto.getGender());
            assertEquals(client.getEmail(), clientDto.getEmail());
            assertEquals(client.getPhoneNumber(), clientDto.getPhoneNumber());
            assertEquals(client.getAddress().getId(), clientDto.getAddress().getId());
            assertEquals(client.getMinimumPrice(), clientDto.getMinimumPrice());
            assertEquals(client.getMaximumPrice(), clientDto.getMaximumPrice());
        });
    }

    @Test
    void shouldReturnNullEntity() {
        Client client = clientMapper.fromDto(null);

        assertThat(client).isEqualTo(null);
    }

    @Test
    void shouldReturnNullDto() {
        ClientDto clientDto = clientMapper.toDto(null);

        assertThat(clientDto).isEqualTo(null);
    }
}
