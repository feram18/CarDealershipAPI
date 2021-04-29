package edu.towson.cosc457.CarDealership.service;

import edu.towson.cosc457.CarDealership.exceptions.AlreadyAssignedException;
import edu.towson.cosc457.CarDealership.misc.EmployeeType;
import edu.towson.cosc457.CarDealership.misc.Gender;
import edu.towson.cosc457.CarDealership.model.*;
import edu.towson.cosc457.CarDealership.repository.LocationRepository;
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
public class LocationServiceTest {
    @InjectMocks
    private LocationService locationService;
    @Mock
    private LocationRepository locationRepository;
    @Mock
    private LotService lotService;
    @Mock
    private DepartmentService departmentService;
    @Mock
    private MechanicService mechanicService;
    @Captor
    private ArgumentCaptor<Location> locationArgumentCaptor;
    private Location location;
    private Lot lot;
    private Department department;
    private Mechanic mechanic;

    @BeforeEach
    public void setUp() {
        location = Location.builder()
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
                .lots(new ArrayList<>())
                .departments(new ArrayList<>())
                .mechanics(new ArrayList<>())
                .build();

        lot = Lot.builder()
                .id(1L)
                .size(100.15)
                .build();

        department = Department.builder()
                .id(1L)
                .name("Department A")
                .manager(Manager.builder()
                        .id(1L)
                        .build())
                .build();

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
                .build();
    }

    @Test
    void shouldSaveLocation() {
        locationService.addLocation(location);

        verify(locationRepository, times(1)).save(locationArgumentCaptor.capture());

        assertThat(locationArgumentCaptor.getValue()).usingRecursiveComparison().isEqualTo(location);
    }

    @Test
    void shouldGetLocationById() {
        Mockito.when(locationRepository.findById(location.getId())).thenReturn(Optional.of(location));

        Location actualLocation = locationService.getLocation(location.getId());
        verify(locationRepository, times(1)).findById(location.getId());

        assertAll(() -> {
            assertThat(actualLocation).isNotNull();
            assertThat(actualLocation).usingRecursiveComparison().isEqualTo(location);
        });
    }

    @Test
    void shouldGetAllLocations() {
        List<Location> expectedLocations = new ArrayList<>();
        expectedLocations.add(location);
        expectedLocations.add(Location.builder()
                .id(1L)
                .build());
        expectedLocations.add(Location.builder()
                .id(2L)
                .build());

        Mockito.when(locationRepository.findAll()).thenReturn(expectedLocations);
        List<Location> actualLocations = locationService.getLocations();
        verify(locationRepository, times(1)).findAll();

        assertAll(() -> {
            assertThat(actualLocations).isNotNull();
            assertThat(actualLocations.size()).isEqualTo(expectedLocations.size());
        });
    }

    @Test
    void shouldDeleteLocationById() {
        Mockito.when(locationRepository.findById(location.getId())).thenReturn(Optional.of(location));

        Location deletedLocation = locationService.deleteLocation(location.getId());

        assertAll(() -> {
            assertThat(deletedLocation).isNotNull();
            assertThat(deletedLocation).usingRecursiveComparison().isEqualTo(location);
        });
    }

    @Test
    void shouldUpdateLocation() {
        Mockito.when(locationRepository.findById(location.getId())).thenReturn(Optional.of(location));

        Location editedLocation = Location.builder()
                .id(1L)
                .name("Location B")
                .address(Address.builder()
                        .id(2L)
                        .street("123 Main St.")
                        .city("New York City")
                        .state("New York")
                        .zipCode(12345)
                        .build())
                .siteManager(SiteManager.builder()
                        .id(1L)
                        .build())
                .lots(new ArrayList<>())
                .departments(new ArrayList<>())
                .mechanics(new ArrayList<>())
                .build();

        Location updatedLocation = locationService.editLocation(location.getId(), editedLocation);

        assertAll(() -> {
            assertThat(updatedLocation).isNotNull();
            assertThat(updatedLocation).usingRecursiveComparison().isEqualTo(editedLocation);
        });
    }

    @Test
    void shouldAddLotToLocation() {
        Mockito.when(locationRepository.findById(location.getId())).thenReturn(Optional.of(location));
        Mockito.when(lotService.getLot(location.getId())).thenReturn(lot);

        Location updatedLocation = locationService.addLotToLocation(location.getId(), lot.getId());

        assertAll(() -> {
            assertThat(updatedLocation.getLots().size()).isEqualTo(1);
            assertThat(updatedLocation.getLots().get(0)).usingRecursiveComparison().isEqualTo(lot);
        });
    }

    @Test
    void shouldFailToAddLotToLocation() {
        Mockito.when(locationRepository.findById(location.getId())).thenReturn(Optional.of(location));
        Mockito.when(lotService.getLot(location.getId())).thenReturn(lot);

        locationService.addLotToLocation(location.getId(), lot.getId()); // Assign once

        assertThrows(AlreadyAssignedException.class,
                () -> locationService.addLotToLocation(location.getId(), lot.getId())); // Attempt to assign again
    }

    @Test
    void shouldRemoveLotFromLocation() {
        Mockito.when(locationRepository.findById(location.getId())).thenReturn(Optional.of(location));
        Mockito.when(lotService.getLot(location.getId())).thenReturn(lot);

        locationService.addLotToLocation(location.getId(), lot.getId());
        Location updatedLocation = locationService.removeLotFromLocation(location.getId(), lot.getId());

        assertThat(updatedLocation.getLots().size()).isEqualTo(0);
    }

    @Test
    void shouldAddDepartmentToLocation() {
        Mockito.when(locationRepository.findById(location.getId())).thenReturn(Optional.of(location));
        Mockito.when(departmentService.getDepartment(department.getId())).thenReturn(department);

        Location updatedLocation = locationService.addDepartmentToLocation(location.getId(), department.getId());

        assertAll(() -> {
            assertThat(updatedLocation.getDepartments().size()).isEqualTo(1);
            assertThat(updatedLocation.getDepartments().get(0)).usingRecursiveComparison().isEqualTo(department);
        });
    }

    @Test
    void shouldFailToAddDepartmentToLocation() {
        Mockito.when(locationRepository.findById(location.getId())).thenReturn(Optional.of(location));
        Mockito.when(departmentService.getDepartment(department.getId())).thenReturn(department);

        locationService.addDepartmentToLocation(location.getId(), department.getId());

        assertThrows(AlreadyAssignedException.class,
                () -> locationService.addDepartmentToLocation(location.getId(), department.getId()));
    }

    @Test
    void shouldRemoveDepartmentFromLocation() {
        Mockito.when(locationRepository.findById(location.getId())).thenReturn(Optional.of(location));
        Mockito.when(departmentService.getDepartment(department.getId())).thenReturn(department);

        locationService.addDepartmentToLocation(location.getId(), department.getId());
        Location updatedLocation = locationService.removeDepartmentFromLocation(location.getId(), department.getId());

        assertThat(updatedLocation.getDepartments().size()).isEqualTo(0);
    }

    @Test
    void shouldAssignMechanicToLocation() {
        Mockito.when(locationRepository.findById(location.getId())).thenReturn(Optional.of(location));
        Mockito.when(mechanicService.getEmployee(mechanic.getId())).thenReturn(mechanic);

        Location updatedLocation = locationService.assignMechanic(location.getId(), mechanic.getId());

        assertAll(() -> {
            assertThat(updatedLocation.getMechanics().size()).isEqualTo(1);
            assertThat(updatedLocation.getMechanics().get(0)).usingRecursiveComparison().isEqualTo(mechanic);
        });
    }

    @Test
    void shouldFailToAssignMechanicToLocation() {
        Mockito.when(locationRepository.findById(location.getId())).thenReturn(Optional.of(location));
        Mockito.when(mechanicService.getEmployee(mechanic.getId())).thenReturn(mechanic);

        locationService.assignMechanic(location.getId(), mechanic.getId());

        assertThrows(AlreadyAssignedException.class,
                () -> locationService.assignMechanic(location.getId(), mechanic.getId()));
    }

    @Test
    void shouldRemoveMechanicFromLocation() {
        Mockito.when(locationRepository.findById(location.getId())).thenReturn(Optional.of(location));
        Mockito.when(mechanicService.getEmployee(mechanic.getId())).thenReturn(mechanic);

        locationService.assignMechanic(location.getId(), mechanic.getId());
        Location updatedLocation = locationService.removeMechanic(location.getId(), mechanic.getId());

        assertThat(updatedLocation.getMechanics().size()).isEqualTo(0);
    }
}
