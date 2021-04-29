package edu.towson.cosc457.CarDealership.service;

import edu.towson.cosc457.CarDealership.exceptions.AlreadyAssignedException;
import edu.towson.cosc457.CarDealership.misc.EmployeeType;
import edu.towson.cosc457.CarDealership.misc.Gender;
import edu.towson.cosc457.CarDealership.model.*;
import edu.towson.cosc457.CarDealership.repository.SalesAssociateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class SalesAssociateServiceTest {
    @InjectMocks
    private SalesAssociateService salesAssociateService;
    @Mock
    private SalesAssociateRepository salesAssociateRepository;
    @Mock
    private ClientService clientService;
    @Captor
    private ArgumentCaptor<SalesAssociate> salesAssociateArgumentCaptor;
    private SalesAssociate salesAssociate;
    private Client client;

    @BeforeEach
    public void setUp() {
        salesAssociate = SalesAssociate.builder()
                .id(1L)
                .ssn("123-45-6789")
                .firstName("FirstName")
                .middleInitial('M')
                .lastName("LastName")
                .gender(Gender.FEMALE)
                .dateOfBirth(LocalDate.now())
                .phoneNumber("123-456-7890")
                .email("sales@company.com")
                .workLocation(Location.builder()
                        .id(1L)
                        .build())
                .salary(35000.00)
                .dateStarted(LocalDate.now())
                .address(Address.builder()
                        .id(1L)
                        .street("123 Main St.")
                        .city("New York City")
                        .state("New York")
                        .zipCode(12345)
                        .build())
                .hoursWorked(780.00)
                .employeeType(EmployeeType.SALES_ASSOCIATE)
                .manager(Manager.builder()
                        .id(2L)
                        .build())
                .department(Department.builder()
                        .id(1L)
                        .build())
                .clients(new ArrayList<>())
                .build();

        client = Client.builder()
                .id(1L)
                .firstName("FirstName")
                .lastName("LastName")
                .gender(Gender.FEMALE)
                .email("client@company.com")
                .phoneNumber("123-456-0000")
                .address(Address.builder()
                        .id(2L)
                        .street("123 Main St.")
                        .city("New York City")
                        .state("New York")
                        .zipCode(12345)
                        .build())
                .minimumPrice(1000.01)
                .maximumPrice(1500.05)
                .build();
    }

    @Test
    void shouldSaveSalesAssociate() {
        salesAssociateService.addEmployee(salesAssociate);

        verify(salesAssociateRepository, times(1)).save(salesAssociateArgumentCaptor.capture());

        assertThat(salesAssociateArgumentCaptor.getValue()).usingRecursiveComparison().isEqualTo(salesAssociate);
    }

    @Test
    void shouldGetSalesAssociateById() {
        Mockito.when(salesAssociateRepository.findById(salesAssociate.getId())).thenReturn(Optional.of(salesAssociate));

        SalesAssociate actualSalesAssociate = salesAssociateService.getEmployee(salesAssociate.getId());
        verify(salesAssociateRepository, times(1)).findById(salesAssociate.getId());

        assertAll(() -> {
            assertThat(actualSalesAssociate).isNotNull();
            assertThat(actualSalesAssociate).usingRecursiveComparison().isEqualTo(salesAssociate);
        });
    }

    @Test
    void shouldGetAllSalesAssociates() {
        List<SalesAssociate> expectedSalesAssociates = new ArrayList<>();
        expectedSalesAssociates.add(salesAssociate);
        expectedSalesAssociates.add(SalesAssociate.builder()
                .id(2L)
                .build());
        expectedSalesAssociates.add(SalesAssociate.builder()
                .id(3L)
                .build());

        Mockito.when(salesAssociateRepository.findAll()).thenReturn(expectedSalesAssociates);
        List<SalesAssociate> actualSalesAssociates = salesAssociateService.getEmployees();
        verify(salesAssociateRepository, times(1)).findAll();

        assertAll(() -> {
            assertThat(actualSalesAssociates).isNotNull();
            assertThat(actualSalesAssociates.size()).isEqualTo(expectedSalesAssociates.size());
        });
    }

    @Test
    void shouldDeleteSalesAssociate() {
        Mockito.when(salesAssociateRepository.findById(salesAssociate.getId())).thenReturn(Optional.of(salesAssociate));

        SalesAssociate deletedSalesAssociate = salesAssociateService.deleteEmployee(salesAssociate.getId());

        verify(salesAssociateRepository, times(1)).delete(salesAssociate);

        assertAll(() -> {
            assertThat(deletedSalesAssociate).isNotNull();
            assertThat(deletedSalesAssociate).usingRecursiveComparison().isEqualTo(salesAssociate);
        });
    }

    @Test
    void shouldUpdateSalesAssociate() {
        Mockito.when(salesAssociateRepository.findById(salesAssociate.getId())).thenReturn(Optional.of(salesAssociate));

        SalesAssociate editedSalesAssociate = SalesAssociate.builder()
                .id(1L)
                .ssn("123-45-6789")
                .firstName("NewName")
                .middleInitial('M')
                .lastName("NewLName")
                .gender(Gender.FEMALE)
                .dateOfBirth(LocalDate.now())
                .phoneNumber("123-456-7890")
                .email("new-sales@company.com")
                .workLocation(Location.builder()
                        .id(1L)
                        .build())
                .salary(35000.00)
                .dateStarted(LocalDate.now())
                .address(Address.builder()
                        .id(1L)
                        .street("123 Main St.")
                        .city("New York City")
                        .state("New York")
                        .zipCode(12345)
                        .build())
                .hoursWorked(780.00)
                .employeeType(EmployeeType.SALES_ASSOCIATE)
                .manager(Manager.builder()
                        .id(2L)
                        .build())
                .department(Department.builder()
                        .id(1L)
                        .build())
                .clients(new ArrayList<>())
                .build();

        SalesAssociate updatedSalesAssociate =
                salesAssociateService.editEmployee(salesAssociate.getId(), editedSalesAssociate);

        assertAll(() -> {
            assertThat(updatedSalesAssociate).isNotNull();
            assertThat(updatedSalesAssociate).usingRecursiveComparison().isEqualTo(editedSalesAssociate);
        });
    }

    @Test
    void shouldAssignClientToSalesAssociate() {
        Mockito.when(salesAssociateRepository.findById(salesAssociate.getId())).thenReturn(Optional.of(salesAssociate));
        Mockito.when(clientService.getClient(client.getId())).thenReturn(client);

        SalesAssociate updatedSalesAssociate =
                salesAssociateService.assignClient(salesAssociate.getId(), client.getId());

        assertAll(() -> {
            assertThat(updatedSalesAssociate.getClients().size()).isEqualTo(1);
            assertThat(updatedSalesAssociate.getClients().get(0)).usingRecursiveComparison().isEqualTo(client);
        });
    }

    @Test
    void shouldFailToAssignClientToSalesAssociate() {
        Mockito.when(salesAssociateRepository.findById(salesAssociate.getId())).thenReturn(Optional.of(salesAssociate));
        Mockito.when(clientService.getClient(client.getId())).thenReturn(client);

        salesAssociateService.assignClient(salesAssociate.getId(), client.getId()); // Assign once

        // Attempt to assign again
        assertThrows(AlreadyAssignedException.class,
                () -> salesAssociateService.assignClient(salesAssociate.getId(), client.getId()));
    }

    @Test
    void shouldRemoveClientFromSalesAssociate() {
        Mockito.when(salesAssociateRepository.findById(salesAssociate.getId())).thenReturn(Optional.of(salesAssociate));
        Mockito.when(clientService.getClient(client.getId())).thenReturn(client);

        salesAssociateService.assignClient(salesAssociate.getId(), client.getId());
        SalesAssociate updatedSalesAssociate =
                salesAssociateService.removeClient(salesAssociate.getId(), client.getId());

        assertThat(updatedSalesAssociate.getClients().size()).isEqualTo(0);
    }
}
