package edu.towson.cosc457.CarDealership.service;

import edu.towson.cosc457.CarDealership.exceptions.AlreadyAssignedException;
import edu.towson.cosc457.CarDealership.misc.EmployeeType;
import edu.towson.cosc457.CarDealership.misc.Gender;
import edu.towson.cosc457.CarDealership.misc.Status;
import edu.towson.cosc457.CarDealership.model.*;
import edu.towson.cosc457.CarDealership.repository.MechanicRepository;
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
public class MechanicServiceTest {
    @InjectMocks
    private MechanicService mechanicService;
    @Mock
    private MechanicRepository mechanicRepository;
    @Mock
    private ServiceTicketService serviceTicketService;
    @Captor
    private ArgumentCaptor<Mechanic> mechanicArgumentCaptor;
    private Mechanic mechanic;
    private ServiceTicket serviceTicket;

    @BeforeEach
    public void setUp() {
        mechanic = Mechanic.builder()
                .id(1L)
                .ssn("123-45-6789")
                .firstName("FirstName")
                .middleInitial('M')
                .lastName("LastName")
                .gender(Gender.MALE)
                .dateOfBirth(LocalDate.now())
                .phoneNumber("123-456-7890")
                .email("mechanic@company.com")
                .workLocation(Location.builder()
                        .id(1L)
                        .build())
                .salary(45000.00)
                .dateStarted(LocalDate.now())
                .address(Address.builder()
                        .id(1L)
                        .street("123 Main St.")
                        .city("New York City")
                        .state("New York")
                        .zipCode(12345)
                        .build())
                .hoursWorked(780.00)
                .employeeType(EmployeeType.MECHANIC)
                .manager(Manager.builder()
                        .id(2L)
                        .build())
                .department(Department.builder()
                        .id(1L)
                        .build())
                .tickets(new ArrayList<>())
                .build();

        serviceTicket = ServiceTicket.builder()
                .id(1L)
                .vehicle(Vehicle.builder()
                        .id(1L)
                        .build())
                .dateCreated(LocalDate.now())
                .dateUpdated(LocalDate.now())
                .status(Status.OPEN)
                .build();
    }

    @Test
    void shouldSaveMechanic() {
        mechanicService.addEmployee(mechanic);

        verify(mechanicRepository, times(1)).save(mechanicArgumentCaptor.capture());

        assertThat(mechanicArgumentCaptor.getValue()).usingRecursiveComparison().isEqualTo(mechanic);
    }

    @Test
    void shouldGetMechanicById() {
        Mockito.when(mechanicRepository.findById(mechanic.getId())).thenReturn(Optional.of(mechanic));

        Mechanic actualMechanic = mechanicService.getEmployee(mechanic.getId());
        verify(mechanicRepository, times(1)).findById(mechanic.getId());

        assertAll(() -> {
            assertThat(actualMechanic).isNotNull();
            assertThat(actualMechanic).usingRecursiveComparison().isEqualTo(mechanic);
        });
    }

    @Test
    void shouldGetAllMechanics() {
        List<Mechanic> expectedMechanics = new ArrayList<>();
        expectedMechanics.add(mechanic);
        expectedMechanics.add(Mechanic.builder()
                .id(2L)
                .build());
        expectedMechanics.add(Mechanic.builder()
                .id(3L)
                .build());

        Mockito.when(mechanicRepository.findAll()).thenReturn(expectedMechanics);
        List<Mechanic> actualMechanics = mechanicService.getEmployees();
        verify(mechanicRepository, times(1)).findAll();

        assertAll(() -> {
            assertThat(actualMechanics).isNotNull();
            assertThat(actualMechanics.size()).isEqualTo(expectedMechanics.size());
        });
    }

    @Test
    void shouldDeleteMechanic() {
        Mockito.when(mechanicRepository.findById(mechanic.getId())).thenReturn(Optional.of(mechanic));

        Mechanic deletedMechanic = mechanicService.deleteEmployee(mechanic.getId());

        verify(mechanicRepository, times(1)).delete(mechanic);

        assertAll(() -> {
            assertThat(deletedMechanic).isNotNull();
            assertThat(deletedMechanic).usingRecursiveComparison().isEqualTo(mechanic);
        });
    }

    @Test
    void shouldUpdateMechanic() {
        Mockito.when(mechanicRepository.findById(mechanic.getId())).thenReturn(Optional.of(mechanic));

        Mechanic editedMechanic = Mechanic.builder()
                .id(1L)
                .ssn("123-45-6789")
                .firstName("NewName")
                .middleInitial('M')
                .lastName("NewLName")
                .gender(Gender.MALE)
                .dateOfBirth(LocalDate.now())
                .phoneNumber("123-456-9899")
                .email("new-mechanic-email@company.com")
                .workLocation(Location.builder()
                        .id(1L)
                        .build())
                .salary(45000.00)
                .dateStarted(LocalDate.now())
                .address(Address.builder()
                        .id(1L)
                        .street("123 Main St.")
                        .city("New York City")
                        .state("New York")
                        .zipCode(12345)
                        .build())
                .hoursWorked(780.00)
                .employeeType(EmployeeType.MECHANIC)
                .manager(Manager.builder()
                        .id(2L)
                        .build())
                .department(Department.builder()
                        .id(1L)
                        .build())
                .tickets(new ArrayList<>())
                .build();

        Mechanic updatedMechanic = mechanicService.editEmployee(mechanic.getId(), editedMechanic);

        assertAll(() -> {
            assertThat(updatedMechanic).isNotNull();
            assertThat(updatedMechanic).usingRecursiveComparison().isEqualTo(editedMechanic);
        });
    }

    @Test
    void shouldAssignTicketToMechanic() {
        Mockito.when(mechanicRepository.findById(mechanic.getId())).thenReturn(Optional.of(mechanic));
        Mockito.when(serviceTicketService.getServiceTicket(serviceTicket.getId())).thenReturn(serviceTicket);

        Mechanic updatedMechanic = mechanicService.assignTicket(mechanic.getId(), serviceTicket.getId());

        assertAll(() -> {
            assertThat(updatedMechanic.getTickets().size()).isEqualTo(1);
            assertThat(updatedMechanic.getTickets().get(0)).usingRecursiveComparison().isEqualTo(serviceTicket);
        });
    }

    @Test
    void shouldFailToAssignTicketToMechanic() {
        Mockito.when(mechanicRepository.findById(mechanic.getId())).thenReturn(Optional.of(mechanic));
        Mockito.when(serviceTicketService.getServiceTicket(serviceTicket.getId())).thenReturn(serviceTicket);

        mechanicService.assignTicket(mechanic.getId(), serviceTicket.getId()); // Assign once

        assertThrows(AlreadyAssignedException.class,
                () -> mechanicService.assignTicket(mechanic.getId(), serviceTicket.getId())); // Attempt to assign again
    }

    @Test
    void shouldRemoveTicketFromMechanic() {
        Mockito.when(mechanicRepository.findById(mechanic.getId())).thenReturn(Optional.of(mechanic));
        Mockito.when(serviceTicketService.getServiceTicket(serviceTicket.getId())).thenReturn(serviceTicket);

        mechanicService.assignTicket(mechanic.getId(), serviceTicket.getId());
        Mechanic updatedMechanic = mechanicService.removeTicket(mechanic.getId(), serviceTicket.getId());

        assertThat(updatedMechanic.getTickets().size()).isEqualTo(0);
    }
}
