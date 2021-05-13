package edu.towson.cosc457.CarDealership.service;

import edu.towson.cosc457.CarDealership.exceptions.AlreadyAssignedException;
import edu.towson.cosc457.CarDealership.exceptions.NotFoundException;
import edu.towson.cosc457.CarDealership.misc.EmployeeType;
import edu.towson.cosc457.CarDealership.misc.Gender;
import edu.towson.cosc457.CarDealership.model.*;
import edu.towson.cosc457.CarDealership.repository.ManagerRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ManagerServiceTest {
    @InjectMocks
    private ManagerService managerService;
    @Mock
    private ManagerRepository managerRepository;
    @Mock
    private MechanicService mechanicService;
    @Captor
    private ArgumentCaptor<Manager> managerArgumentCaptor;
    private Manager manager;
    private Manager editedManager;
    private Mechanic mechanic;

    @BeforeEach
    public void setUp() {
        manager = Manager.builder()
                .id(1L)
                .ssn("123-45-6789")
                .firstName("FirstName")
                .middleInitial('M')
                .lastName("LastName")
                .gender(Gender.MALE)
                .dateOfBirth(LocalDate.now())
                .phoneNumber("123-456-7890")
                .email("manager@company.com")
                .workLocation(Location.builder()
                        .id(1L)
                        .build())
                .salary(70000.00)
                .dateStarted(LocalDate.now())
                .address(Address.builder()
                        .id(1L)
                        .street("123 Main St.")
                        .city("New York City")
                        .state("New York")
                        .zipCode(12345)
                        .build())
                .hoursWorked(780.00)
                .employeeType(EmployeeType.MANAGER)
                .siteManager(SiteManager.builder()
                        .id(2L)
                        .build())
                .department(Department.builder()
                        .id(1L)
                        .build())
                .mechanics(new ArrayList<>())
                .build();

        editedManager = Manager.builder()
                .id(1L)
                .ssn("123-45-6789")
                .firstName("FirstName")
                .middleInitial('M')
                .lastName("LastName")
                .gender(Gender.MALE)
                .dateOfBirth(LocalDate.now())
                .phoneNumber("123-456-9999")
                .email("new-manager@company.com")
                .workLocation(Location.builder()
                        .id(1L)
                        .build())
                .salary(70000.00)
                .dateStarted(LocalDate.now())
                .address(Address.builder()
                        .id(1L)
                        .street("123 Main St.")
                        .city("New York City")
                        .state("New York")
                        .zipCode(12345)
                        .build())
                .hoursWorked(780.00)
                .employeeType(EmployeeType.MANAGER)
                .siteManager(SiteManager.builder()
                        .id(2L)
                        .build())
                .department(Department.builder()
                        .id(1L)
                        .build())
                .mechanics(new ArrayList<>())
                .build();

        mechanic = Mechanic.builder()
                .build();
    }

    @Test
    void shouldSaveManager() {
        managerService.addEmployee(manager);

        verify(managerRepository, times(1)).save(managerArgumentCaptor.capture());

        assertThat(managerArgumentCaptor.getValue()).usingRecursiveComparison().isEqualTo(manager);
    }

    @Test
    void shouldFailToSaveNullManager() {
        managerService.addEmployee(null);

        verify(managerRepository, never()).save(any(Manager.class));
    }

    @Test
    void shouldGetManagerById() {
        Mockito.when(managerRepository.findById(manager.getId())).thenReturn(Optional.of(manager));

        Manager actualManager = managerService.getEmployee(manager.getId());
        verify(managerRepository, times(1)).findById(manager.getId());

        assertAll(() -> {
            assertThat(actualManager).isNotNull();
            assertThat(actualManager).usingRecursiveComparison().isEqualTo(manager);
        });
    }

    @Test
    void shouldFailToGetManagerById() {
        Mockito.when(managerRepository.findById(manager.getId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> managerService.getEmployee(manager.getId()));

        verify(managerRepository, times(1)).findById(manager.getId());
    }

    @Test
    void shouldGetAllManagers() {
        List<Manager> expectedManagers = new ArrayList<>();
        expectedManagers.add(manager);
        expectedManagers.add(Manager.builder()
                .id(2L)
                .build());
        expectedManagers.add(Manager.builder()
                .id(3L)
                .build());

        Mockito.when(managerRepository.findAll()).thenReturn(expectedManagers);
        List<Manager> actualManagers = managerService.getEmployees();
        verify(managerRepository, times(1)).findAll();

        assertAll(() -> {
            assertThat(actualManagers).isNotNull();
            assertThat(actualManagers.size()).isEqualTo(expectedManagers.size());
        });
    }

    @Test
    void shouldGetAllManagers_EmptyList() {
        Mockito.when(managerRepository.findAll()).thenReturn(new ArrayList<>());

        List<Manager> actualManagers = managerService.getEmployees();

        assertThat(actualManagers).isEmpty();
    }

    @Test
    void shouldDeleteManager() {
        Mockito.when(managerRepository.findById(manager.getId())).thenReturn(Optional.of(manager));

        Manager deletedManager = managerService.deleteEmployee(manager.getId());

        verify(managerRepository, times(1)).delete(manager);

        assertAll(() -> {
            assertThat(deletedManager).isNotNull();
            assertThat(deletedManager).usingRecursiveComparison().isEqualTo(manager);
        });
    }

    @Test
    void shouldFailToDeleteManager() {
        Mockito.when(managerRepository.findById(manager.getId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> managerService.deleteEmployee(manager.getId()));

        verify(managerRepository, never()).delete(any(Manager.class));
    }

    @Test
    void shouldUpdateManager() {
        Mockito.when(managerRepository.findById(manager.getId())).thenReturn(Optional.of(manager));

        Manager updatedManager = managerService.editEmployee(manager.getId(), editedManager);

        assertAll(() -> {
            assertThat(updatedManager).isNotNull();
            assertThat(updatedManager).usingRecursiveComparison().isEqualTo(editedManager);
        });
    }

    @Test
    void shouldFailToUpdateManager() {
        Mockito.when(managerRepository.findById(manager.getId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> managerService.editEmployee(manager.getId(), editedManager));
    }

    @Test
    void shouldAssignEmployeeToManager() {
        Mockito.when(managerRepository.findById(manager.getId())).thenReturn(Optional.of(manager));
        Mockito.when(mechanicService.getEmployee(mechanic.getId())).thenReturn(mechanic);

        Manager updatedManager = managerService.assignToManager(manager.getId(), mechanic.getId());

        assertAll(() -> {
            assertThat(updatedManager.getMechanics().size()).isEqualTo(1);
            assertThat(updatedManager.getMechanics().get(0)).usingRecursiveComparison().isEqualTo(mechanic);
        });
    }

    @Test
    void shouldFailToAssignEmployeeToManager() {
        Mockito.when(managerRepository.findById(manager.getId())).thenReturn(Optional.of(manager));
        Mockito.when(mechanicService.getEmployee(mechanic.getId())).thenReturn(mechanic);

        managerService.assignToManager(manager.getId(), mechanic.getId()); // Assign once

        assertThrows(AlreadyAssignedException.class,
                () -> managerService.assignToManager(manager.getId(), mechanic.getId())); // Attempt to assign again
    }

    @Test
    void shouldRemoveEmployeeFromManager() {
        Mockito.when(managerRepository.findById(manager.getId())).thenReturn(Optional.of(manager));
        Mockito.when(mechanicService.getEmployee(mechanic.getId())).thenReturn(mechanic);

        managerService.assignToManager(manager.getId(), mechanic.getId());
        Manager updatedManager = managerService.removeFromManager(manager.getId(), mechanic.getId());

        assertThat(updatedManager.getMechanics().size()).isEqualTo(0);
    }
}
