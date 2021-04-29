package edu.towson.cosc457.CarDealership.service;

import edu.towson.cosc457.CarDealership.exceptions.AlreadyAssignedException;
import edu.towson.cosc457.CarDealership.misc.EmployeeType;
import edu.towson.cosc457.CarDealership.misc.Gender;
import edu.towson.cosc457.CarDealership.model.*;
import edu.towson.cosc457.CarDealership.repository.SiteManagerRepository;
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
public class SiteManagerServiceTest {
    @InjectMocks
    private SiteManagerService siteManagerService;
    @Mock
    private SiteManagerRepository siteManagerRepository;
    @Mock
    private ManagerService managerService;
    @Captor
    private ArgumentCaptor<SiteManager> siteManagerArgumentCaptor;
    private SiteManager siteManager;
    private Manager manager;

    @BeforeEach
    public void setUp() {
        siteManager = SiteManager.builder()
                .id(1L)
                .ssn("123-45-6789")
                .firstName("FirstName")
                .middleInitial('M')
                .lastName("LastName")
                .gender(Gender.FEMALE)
                .dateOfBirth(LocalDate.now())
                .phoneNumber("123-456-7890")
                .email("site.manager@company.com")
                .workLocation(Location.builder()
                        .id(1L)
                        .build())
                .salary(75000.00)
                .dateStarted(LocalDate.now())
                .address(Address.builder()
                        .id(1L)
                        .street("123 Main St.")
                        .city("New York City")
                        .state("New York")
                        .zipCode(12345)
                        .build())
                .hoursWorked(780.00)
                .employeeType(EmployeeType.SITE_MANAGER)
                .managedLocation(Location.builder()
                        .id(1L)
                        .build())
                .managers(new ArrayList<>())
                .build();

        manager = Manager.builder()
                .id(2L)
                .ssn("123-45-0000")
                .firstName("First")
                .middleInitial('M')
                .lastName("Last")
                .gender(Gender.MALE)
                .dateOfBirth(LocalDate.now())
                .phoneNumber("123-456-1515")
                .email("manager@company.com")
                .workLocation(Location.builder()
                        .id(1L)
                        .build())
                .salary(70000.00)
                .dateStarted(LocalDate.now())
                .address(Address.builder()
                        .id(2L)
                        .street("123 Main St.")
                        .city("New York City")
                        .state("New York")
                        .zipCode(12345)
                        .build())
                .hoursWorked(780.00)
                .employeeType(EmployeeType.MANAGER)
                .department(Department.builder()
                        .id(1L)
                        .build()).build();
    }

    @Test
    void shouldSaveSiteManager() {
        siteManagerService.addEmployee(siteManager);

        verify(siteManagerRepository, times(1)).save(siteManagerArgumentCaptor.capture());

        assertThat(siteManagerArgumentCaptor.getValue()).usingRecursiveComparison().isEqualTo(siteManager);
    }

    @Test
    void shouldGetSiteManagerById() {
        Mockito.when(siteManagerRepository.findById(siteManager.getId())).thenReturn(Optional.of(siteManager));

        SiteManager actualSiteManager = siteManagerService.getEmployee(siteManager.getId());
        verify(siteManagerRepository, times(1)).findById(siteManager.getId());

        assertAll(() -> {
            assertThat(actualSiteManager).isNotNull();
            assertThat(actualSiteManager).usingRecursiveComparison().isEqualTo(siteManager);
        });
    }

    @Test
    void shouldGetAllSiteManagers() {
        List<SiteManager> expectedSiteManagers = new ArrayList<>();
        expectedSiteManagers.add(siteManager);
        expectedSiteManagers.add(SiteManager.builder()
                .id(2L)
                .build());
        expectedSiteManagers.add(SiteManager.builder()
                .id(3L)
                .build());

        Mockito.when(siteManagerRepository.findAll()).thenReturn(expectedSiteManagers);
        List<SiteManager> actualSiteManagers = siteManagerService.getEmployees();
        verify(siteManagerRepository, times(1)).findAll();

        assertAll(() -> {
            assertThat(actualSiteManagers).isNotNull();
            assertThat(actualSiteManagers.size()).isEqualTo(expectedSiteManagers.size());
        });
    }

    @Test
    void shouldDeleteSiteManagerById() {
        Mockito.when(siteManagerRepository.findById(siteManager.getId())).thenReturn(Optional.of(siteManager));

        SiteManager deletedSiteManager = siteManagerService.deleteEmployee(siteManager.getId());

        verify(siteManagerRepository, times(1)).delete(siteManager);

        assertAll(() -> {
            assertThat(deletedSiteManager).isNotNull();
            assertThat(deletedSiteManager).usingRecursiveComparison().isEqualTo(siteManager);
        });
    }

    @Test
    void shouldUpdateSiteManager() {
        Mockito.when(siteManagerRepository.findById(siteManager.getId())).thenReturn(Optional.of(siteManager));

        SiteManager editedSiteManager = SiteManager.builder()
                .id(1L)
                .ssn("123-45-6789")
                .firstName("NewName")
                .middleInitial('M')
                .lastName("LastName")
                .gender(Gender.FEMALE)
                .dateOfBirth(LocalDate.now())
                .phoneNumber("123-456-4545")
                .email("site.manager@company.com")
                .workLocation(Location.builder()
                        .id(1L)
                        .build())
                .salary(75000.00)
                .dateStarted(LocalDate.now())
                .address(Address.builder()
                        .id(1L)
                        .street("123 Main St.")
                        .city("New York City")
                        .state("New York")
                        .zipCode(12345)
                        .build())
                .hoursWorked(780.00)
                .employeeType(EmployeeType.SITE_MANAGER)
                .managedLocation(Location.builder()
                        .id(1L)
                        .build())
                .managers(new ArrayList<>())
                .build();

        SiteManager updatedSiteManager = siteManagerService.editEmployee(siteManager.getId(), editedSiteManager);

        assertAll(() -> {
            assertThat(updatedSiteManager).isNotNull();
            assertThat(updatedSiteManager).usingRecursiveComparison().isEqualTo(editedSiteManager);
        });
    }

    @Test
    void shouldAssignManagerToSiteManager() {
        Mockito.when(siteManagerRepository.findById(siteManager.getId())).thenReturn(Optional.of(siteManager));
        Mockito.when(managerService.getEmployee(manager.getId())).thenReturn(manager);

        SiteManager updatedSiteManager = siteManagerService.assignToManager(siteManager.getId(), manager.getId());

        assertAll(() -> {
            assertThat(updatedSiteManager.getManagers().size()).isEqualTo(1);
            assertThat(updatedSiteManager.getManagers().get(0)).usingRecursiveComparison().isEqualTo(manager);
        });
    }

    @Test
    void shouldFailToAssignManagerToSiteManager() {
        Mockito.when(siteManagerRepository.findById(siteManager.getId())).thenReturn(Optional.of(siteManager));
        Mockito.when(managerService.getEmployee(manager.getId())).thenReturn(manager);

        siteManagerService.assignToManager(siteManager.getId(), manager.getId()); // Assign once

        // Attempt to assign again
        assertThrows(AlreadyAssignedException.class,
                () -> siteManagerService.assignToManager(siteManager.getId(), manager.getId()));
    }

    @Test
    void shouldRemoveManagerFromSiteManager() {
        Mockito.when(siteManagerRepository.findById(siteManager.getId())).thenReturn(Optional.of(siteManager));
        Mockito.when(managerService.getEmployee(manager.getId())).thenReturn(manager);

        siteManagerService.assignToManager(siteManager.getId(), manager.getId());
        SiteManager updatedSiteManager = siteManagerService.removeFromManager(siteManager.getId(), manager.getId());

        assertThat(updatedSiteManager.getManagers().size()).isEqualTo(0);

    }
}
