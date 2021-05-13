package edu.towson.cosc457.CarDealership.service;

import edu.towson.cosc457.CarDealership.exceptions.AlreadyAssignedException;
import edu.towson.cosc457.CarDealership.exceptions.NotFoundException;
import edu.towson.cosc457.CarDealership.misc.Status;
import edu.towson.cosc457.CarDealership.misc.TransmissionType;
import edu.towson.cosc457.CarDealership.misc.VehicleType;
import edu.towson.cosc457.CarDealership.model.*;
import edu.towson.cosc457.CarDealership.repository.VehicleRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VehicleServiceTest {
    @InjectMocks
    private VehicleService vehicleService;
    @Mock
    private VehicleRepository vehicleRepository;
    @Mock
    private ServiceTicketService serviceTicketService;
    @Captor
    private ArgumentCaptor<Vehicle> vehicleArgumentCaptor;
    private Vehicle vehicle;
    private Vehicle editedVehicle;
    private ServiceTicket serviceTicket;

    @BeforeEach
    public void setUp() {
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
                .lot(Lot.builder()
                        .id(1L)
                        .build())
                .tickets(new ArrayList<>())
                .build();

        editedVehicle = Vehicle.builder()
                .id(1L)
                .vin("JH4DA3340KS005705")
                .make("Make")
                .model("Model")
                .year(2021)
                .color("Black")
                .type(VehicleType.SEDAN)
                .transmission(TransmissionType.AUTOMATIC)
                .features("Sunroof")
                .mpg(25)
                .mileage(7000)
                .price(29000.00)
                .lot(Lot.builder()
                        .id(1L)
                        .build())
                .tickets(new ArrayList<>())
                .build();

        serviceTicket = ServiceTicket.builder()
                .id(1L)
                .mechanic(Mechanic.builder()
                        .id(1L)
                        .build())
                .dateCreated(LocalDate.now())
                .dateUpdated(LocalDate.now())
                .status(Status.OPEN)
                .build();
    }

    @Test
    void shouldSaveVehicle() {
        vehicleService.addVehicle(vehicle);

        verify(vehicleRepository, times(1)).save(vehicleArgumentCaptor.capture());

        assertThat(vehicleArgumentCaptor.getValue()).usingRecursiveComparison().isEqualTo(vehicle);
    }

    @Test
    void shouldFailToSaveNullVehicle() {
        vehicleService.addVehicle(null);

        verify(vehicleRepository, never()).save(any(Vehicle.class));
    }

    @Test
    void shouldGetVehicleById() {
        Mockito.when(vehicleRepository.findById(vehicle.getId())).thenReturn(Optional.of(vehicle));

        Vehicle actualVehicle = vehicleService.getVehicle(vehicle.getId());
        verify(vehicleRepository, times(1)).findById(vehicle.getId());

        assertAll(() -> {
            assertThat(actualVehicle).isNotNull();
            assertThat(actualVehicle).usingRecursiveComparison().isEqualTo(vehicle);
        });
    }

    @Test
    void shouldFailToGetVehicleById() {
        Mockito.when(vehicleRepository.findById(vehicle.getId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> vehicleService.getVehicle(vehicle.getId()));

        verify(vehicleRepository, times(1)).findById(vehicle.getId());
    }

    @Test
    void shouldGetAllVehicles() {
        List<Vehicle> expectedVehicles = new ArrayList<>();
        expectedVehicles.add(vehicle);
        expectedVehicles.add(Vehicle.builder()
                .id(2L)
                .build());
        expectedVehicles.add(Vehicle.builder()
                .id(3L)
                .build());

        Mockito.when(vehicleRepository.findAll()).thenReturn(expectedVehicles);
        List<Vehicle> actualVehicles = vehicleService.getVehicles();
        verify(vehicleRepository, times(1)).findAll();

        assertAll(() -> {
            assertThat(actualVehicles).isNotNull();
            assertThat(actualVehicles.size()).isEqualTo(expectedVehicles.size());
        });
    }

    @Test
    void shouldGetAllVehicles_EmptyList() {
        Mockito.when(vehicleRepository.findAll()).thenReturn(new ArrayList<>());

        List<Vehicle> actualVehicles = vehicleService.getVehicles();

        assertThat(actualVehicles).isEmpty();
    }

    @Test
    void shouldDeleteVehicle() {
        Mockito.when(vehicleRepository.findById(vehicle.getId())).thenReturn(Optional.of(vehicle));

        Vehicle deletedVehicle = vehicleService.deleteVehicle(vehicle.getId());

        verify(vehicleRepository, times(1)).delete(vehicle);

        assertAll(() -> {
            assertThat(deletedVehicle).isNotNull();
            assertThat(deletedVehicle).usingRecursiveComparison().isEqualTo(vehicle);
        });
    }

    @Test
    void shouldFailToDeleteVehicle() {
        Mockito.when(vehicleRepository.findById(vehicle.getId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> vehicleService.deleteVehicle(vehicle.getId()));

        verify(vehicleRepository, never()).delete(any(Vehicle.class));
    }

    @Test
    void shouldUpdateVehicle() {
        Mockito.when(vehicleRepository.findById(vehicle.getId())).thenReturn(Optional.of(vehicle));

        Vehicle updatedVehicle = vehicleService.editVehicle(vehicle.getId(), editedVehicle);

        assertAll(() -> {
            assertThat(updatedVehicle).isNotNull();
            assertThat(updatedVehicle).usingRecursiveComparison().isEqualTo(editedVehicle);
        });
    }

    @Test
    void shouldFailToUpdateVehicle() {
        Mockito.when(vehicleRepository.findById(vehicle.getId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> vehicleService.editVehicle(vehicle.getId(), editedVehicle));
    }

    @Test
    void shouldAssignTicketToVehicle() {
        Mockito.when(vehicleRepository.findById(vehicle.getId())).thenReturn(Optional.of(vehicle));
        Mockito.when(serviceTicketService.getServiceTicket(serviceTicket.getId())).thenReturn(serviceTicket);

        Vehicle updatedVehicle = vehicleService.assignTicket(vehicle.getId(), serviceTicket.getId());

        assertAll(() -> {
            assertThat(updatedVehicle.getTickets().size()).isEqualTo(1);
            assertThat(updatedVehicle.getTickets().get(0)).usingRecursiveComparison().isEqualTo(serviceTicket);
        });
    }

    @Test
    void shouldFailToAssignTicketToVehicle() {
        Mockito.when(vehicleRepository.findById(vehicle.getId())).thenReturn(Optional.of(vehicle));
        Mockito.when(serviceTicketService.getServiceTicket(serviceTicket.getId())).thenReturn(serviceTicket);

        vehicleService.assignTicket(vehicle.getId(), serviceTicket.getId()); // Assign once

        assertThrows(AlreadyAssignedException.class,
                () -> vehicleService.assignTicket(vehicle.getId(), serviceTicket.getId()));
    }

    @Test
    void shouldRemoveTicketToVehicle() {
        Mockito.when(vehicleRepository.findById(vehicle.getId())).thenReturn(Optional.of(vehicle));
        Mockito.when(serviceTicketService.getServiceTicket(serviceTicket.getId())).thenReturn(serviceTicket);

        vehicleService.assignTicket(vehicle.getId(), serviceTicket.getId());
        Vehicle updatedVehicle = vehicleService.removeTicket(vehicle.getId(), serviceTicket.getId());

        assertThat(updatedVehicle.getTickets().size()).isEqualTo(0);
    }
}
