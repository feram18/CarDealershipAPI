package edu.towson.cosc457.CarDealership.service;

import edu.towson.cosc457.CarDealership.exceptions.AlreadyAssignedException;
import edu.towson.cosc457.CarDealership.misc.TransmissionType;
import edu.towson.cosc457.CarDealership.misc.VehicleType;
import edu.towson.cosc457.CarDealership.model.Location;
import edu.towson.cosc457.CarDealership.model.Lot;
import edu.towson.cosc457.CarDealership.model.Vehicle;
import edu.towson.cosc457.CarDealership.repository.LotRepository;
import org.junit.jupiter.api.AfterEach;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class LotServiceTest {
    @InjectMocks
    private LotService lotService;
    @Mock
    private LotRepository lotRepository;
    @Mock
    private VehicleService vehicleService;
    @Captor
    private ArgumentCaptor<Lot> lotArgumentCaptor;
    private Lot lot;
    private Vehicle vehicle;

    @BeforeEach
    public void setUp() {
        lot = Lot.builder()
                .id(1L)
                .size(100.15)
                .location(Location.builder()
                        .id(1L)
                        .build())
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
                .build();
    }

    @Test
    void shouldSaveLot() {
        lotService.addLot(lot);

        verify(lotRepository, times(1)).save(lotArgumentCaptor.capture());

        assertThat(lotArgumentCaptor.getValue()).usingRecursiveComparison().isEqualTo(lot);
    }

    @Test
    void shouldGetLotById() {
        Mockito.when(lotRepository.findById(lot.getId())).thenReturn(Optional.of(lot));

        Lot actualLot = lotService.getLot(lot.getId());
        verify(lotRepository, times(1)).findById(lot.getId());

        assertAll(() -> {
            assertThat(actualLot).isNotNull();
            assertThat(actualLot).usingRecursiveComparison().isEqualTo(lot);
        });
    }

    @Test
    void shouldGetAllLots() {
        List<Lot> expectedLots = new ArrayList<>();
        expectedLots.add(lot);
        expectedLots.add(Lot.builder()
                .id(2L)
                .build());
        expectedLots.add(Lot.builder()
                .id(3L)
                .build());

        Mockito.when(lotRepository.findAll()).thenReturn(expectedLots);
        List<Lot> actualLots = lotService.getLots();
        verify(lotRepository, times(1)).findAll();

        assertAll(() -> {
            assertThat(actualLots).isNotNull();
            assertThat(actualLots.size()).isEqualTo(expectedLots.size());
        });
    }

    @Test
    void shouldDeleteLotById() {
        Mockito.when(lotRepository.findById(lot.getId())).thenReturn(Optional.of(lot));

        Lot deletedLot = lotService.deleteLot(lot.getId());

        verify(lotRepository, times(1)).delete(lot);

        assertAll(() -> {
            assertThat(deletedLot).isNotNull();
            assertThat(deletedLot).usingRecursiveComparison().isEqualTo(lot);
        });
    }

    @Test
    void shouldUpdateLot() {
        Mockito.when(lotRepository.findById(lot.getId())).thenReturn(Optional.of(lot));

        Lot editedLot = Lot.builder()
                .id(1L)
                .size(300.56)
                .location(Location.builder()
                        .id(1L)
                        .build())
                .vehicles(new ArrayList<Vehicle>())
                .build();

        Lot updatedLot = lotService.editLot(lot.getId(), editedLot);

        assertAll(() -> {
            assertThat(updatedLot).isNotNull();
            assertThat(updatedLot).usingRecursiveComparison().isEqualTo(editedLot);
        });
    }

    @Test
    void shouldAddVehicleToLot() {
        Mockito.when(lotRepository.findById(lot.getId())).thenReturn(Optional.of(lot));
        Mockito.when(vehicleService.getVehicle(vehicle.getId())).thenReturn(vehicle);

        Lot updatedLot = lotService.addVehicleToLot(lot.getId(), vehicle.getId());

        assertAll(() -> {
            assertThat(updatedLot.getVehicles().size()).isEqualTo(1);
            assertThat(updatedLot.getVehicles().get(0)).usingRecursiveComparison().isEqualTo(vehicle);
        });
    }

    @Test
    void shouldFailToAddVehicleToLot() {
        Mockito.when(lotRepository.findById(lot.getId())).thenReturn(Optional.of(lot));
        Mockito.when(vehicleService.getVehicle(vehicle.getId())).thenReturn(vehicle);

        lotService.addVehicleToLot(lot.getId(), vehicle.getId()); // Assign once

        assertThrows(AlreadyAssignedException.class,
                () -> lotService.addVehicleToLot(lot.getId(), vehicle.getId())); // Attempt to assign again
    }

    @Test
    void shouldRemoveVehicleFromLot() {
        Mockito.when(lotRepository.findById(lot.getId())).thenReturn(Optional.of(lot));
        Mockito.when(vehicleService.getVehicle(vehicle.getId())).thenReturn(vehicle);

        lotService.addVehicleToLot(lot.getId(), vehicle.getId());
        Lot updatedLot = lotService.removeVehicleFromLot(lot.getId(), vehicle.getId());

        assertThat(updatedLot.getVehicles().size()).isEqualTo(0);
    }
}
