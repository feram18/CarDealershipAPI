package edu.towson.cosc457.CarDealership.service;

import edu.towson.cosc457.CarDealership.misc.Gender;
import edu.towson.cosc457.CarDealership.model.Address;
import edu.towson.cosc457.CarDealership.model.Client;
import edu.towson.cosc457.CarDealership.model.SalesAssociate;
import edu.towson.cosc457.CarDealership.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {
    @InjectMocks
    private ClientService clientService;
    @Mock
    private ClientRepository clientRepository;
    @Captor
    private ArgumentCaptor<Client> clientArgumentCaptor;
    private Client client;

    @BeforeEach
    public void setUp() {
        client = Client.builder()
                .id(1L)
                .ssn("123-45-6789")
                .firstName("FirstName")
                .lastName("LastName")
                .gender(Gender.FEMALE)
                .email("client@company.com")
                .phoneNumber("123-456-7890")
                .address(Address.builder()
                        .id(1L)
                        .street("St")
                        .city("City")
                        .state("State")
                        .zipCode(12345)
                        .build())
                .salesAssociate(SalesAssociate.builder()
                        .id(1L)
                        .build())
                .minimumPrice(1200.05)
                .maximumPrice(5000.12)
                .build();
    }

    @Test
    void shouldSaveClient() {
        clientService.addClient(client);

        verify(clientRepository, times(1)).save(clientArgumentCaptor.capture());

        assertThat(clientArgumentCaptor.getValue()).usingRecursiveComparison().isEqualTo(client);
    }

    @Test
    void shouldGetClientById() {
        Mockito.when(clientRepository.findById(client.getId())).thenReturn(Optional.of(client));

        Client actualClient = clientService.getClient(client.getId());
        verify(clientRepository, times(1)).findById(client.getId());

        assertAll(() -> {
            assertThat(actualClient).isNotNull();
            assertThat(actualClient).usingRecursiveComparison().isEqualTo(client);
        });
    }

    @Test
    void shouldGetAllClients() {
        List<Client> expectedClients = new ArrayList<>();
        expectedClients.add(client);
        expectedClients.add(Client.builder()
                .id(2L)
                .build());
        expectedClients.add(Client.builder()
                .id(3L)
                .build());

        Mockito.when(clientRepository.findAll()).thenReturn(expectedClients);
        List<Client> actualClients = clientService.getClients();
        verify(clientRepository, times(1)).findAll();

        assertAll(() -> {
            assertThat(actualClients).isNotNull();
            assertThat(actualClients.size()).isEqualTo(expectedClients.size());
        });
    }

    @Test
    void shouldDeleteClientById() {
        Mockito.when(clientRepository.findById(client.getId())).thenReturn(Optional.of(client));

        Client deletedClient = clientService.deleteClient(client.getId());
        verify(clientRepository, times(1)).delete(client);

        assertAll(() -> {
           assertThat(deletedClient).isNotNull();
           assertThat(deletedClient).usingRecursiveComparison().isEqualTo(client);
        });
    }

    @Test
    void shouldUpdateClient() {
        Mockito.when(clientRepository.findById(client.getId())).thenReturn(Optional.of(client));

        Client editedClient = Client.builder()
                .id(1L)
                .ssn("123-45-6789")
                .firstName("NewFirstName")
                .lastName("NewLastName")
                .gender(Gender.FEMALE)
                .email("client-2@company.com")
                .phoneNumber("123-456-0000")
                .address(Address.builder()
                        .id(1L)
                        .street("St")
                        .city("City")
                        .state("State")
                        .zipCode(12345)
                        .build())
                .salesAssociate(SalesAssociate.builder()
                        .id(1L)
                        .build())
                .minimumPrice(1200.05)
                .maximumPrice(8000.36)
                .build();

        Client updatedClient = clientService.editClient(client.getId(), editedClient);

        assertAll(() -> {
            assertThat(updatedClient).isNotNull();
            assertThat(updatedClient).usingRecursiveComparison().isEqualTo(editedClient);
        });
    }
}
