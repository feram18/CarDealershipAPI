package edu.towson.cosc457.CarDealership.service;

import edu.towson.cosc457.CarDealership.exceptions.AlreadyAssignedException;
import edu.towson.cosc457.CarDealership.misc.EmployeeType;
import edu.towson.cosc457.CarDealership.misc.Gender;
import edu.towson.cosc457.CarDealership.model.*;
import edu.towson.cosc457.CarDealership.repository.DepartmentRepository;
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
public class DepartmentServiceTest {
    @InjectMocks
    private DepartmentService departmentService;
    @Mock
    private MechanicService mechanicService;
    @Mock
    private DepartmentRepository departmentRepository;
    @Captor
    private ArgumentCaptor<Department> departmentArgumentCaptor;
    private Department department;
    private Mechanic mechanic;

    @BeforeEach
    public void setUp() {
        department = Department.builder()
                .id(1L)
                .name("Department A")
                .manager(Manager.builder()
                        .id(1L)
                        .build())
                .location(Location.builder()
                        .id(1L)
                        .build())
                .mechanics(new ArrayList<>())
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
                .build();
    }

    @Test
    void shouldSaveDepartment() {
        departmentService.addDepartment(department);

        verify(departmentRepository, times(1)).save(departmentArgumentCaptor.capture());

        assertThat(departmentArgumentCaptor.getValue()).usingRecursiveComparison().isEqualTo(department);
    }

    @Test
    void shouldGetDepartmentById() {
        Mockito.when(departmentRepository.findById(department.getId())).thenReturn(Optional.of(department));

        Department actualDepartment = departmentService.getDepartment(department.getId());
        verify(departmentRepository, times(1)).findById(department.getId());

        assertAll(() -> {
            assertThat(actualDepartment).isNotNull();
            assertThat(actualDepartment).usingRecursiveComparison().isEqualTo(department);
        });
    }

    @Test
    void shouldGetAllDepartments() {
        List<Department> expectedDepartments = new ArrayList<>();
        expectedDepartments.add(department);
        expectedDepartments.add(Department.builder()
                .id(2L)
                .build());
        expectedDepartments.add(Department.builder()
                .id(3L)
                .build());

        Mockito.when(departmentRepository.findAll()).thenReturn(expectedDepartments);
        List<Department> actualDepartments = departmentService.getDepartments();
        verify(departmentRepository, times(1)).findAll();

        assertAll(() -> {
            assertThat(actualDepartments).isNotNull();
            assertThat(actualDepartments.size()).isEqualTo(expectedDepartments.size());
        });
    }

    @Test
    void shouldDeleteDepartment() {
        Mockito.when(departmentRepository.findById(department.getId())).thenReturn(Optional.of(department));

        Department deletedDepartment = departmentService.deleteDepartment(department.getId());

        verify(departmentRepository, times(1)).delete(department);

        assertAll(() -> {
            assertThat(deletedDepartment).isNotNull();
            assertThat(deletedDepartment).usingRecursiveComparison().isEqualTo(department);
        });
    }

    @Test
    void shouldUpdateDepartment() {
        Mockito.when(departmentRepository.findById(department.getId())).thenReturn(Optional.of(department));

        Department editedDepartment = Department.builder()
                .id(1L)
                .name("Department A")
                .manager(Manager.builder()
                        .id(2L)
                        .build())
                .location(Location.builder()
                        .id(1L)
                        .build())
                .mechanics(new ArrayList<>())
                .build();

        Department updatedDepartment = departmentService.editDepartment(department.getId(), editedDepartment);

        assertAll(() -> {
            assertThat(updatedDepartment).isNotNull();
            assertThat(updatedDepartment).usingRecursiveComparison().isEqualTo(editedDepartment);
        });
    }

    @Test
    void shouldAssignMechanicToDepartment() {
        Mockito.when(departmentRepository.findById(department.getId())).thenReturn(Optional.of(department));
        Mockito.when(mechanicService.getEmployee(mechanic.getId())).thenReturn(mechanic);

        Department updatedDepartment = departmentService.assignMechanic(department.getId(), mechanic.getId());

        assertAll(() -> {
            assertThat(updatedDepartment.getMechanics().size()).isEqualTo(1);
            assertThat(updatedDepartment.getMechanics().get(0)).usingRecursiveComparison().isEqualTo(mechanic);
        });
    }

    @Test
    void shouldFailToAssignMechanicToDepartment() {
        Mockito.when(departmentRepository.findById(department.getId())).thenReturn(Optional.of(department));
        Mockito.when(mechanicService.getEmployee(mechanic.getId())).thenReturn(mechanic);

        departmentService.assignMechanic(department.getId(), mechanic.getId()); // Assign once

        assertThrows(AlreadyAssignedException.class,
                () -> departmentService.assignMechanic(department.getId(), mechanic.getId())); // Attempt to assign again
    }

    @Test
    void shouldRemoveMechanicFromDepartment() {
        Mockito.when(departmentRepository.findById(department.getId())).thenReturn(Optional.of(department));
        Mockito.when(mechanicService.getEmployee(mechanic.getId())).thenReturn(mechanic);

        departmentService.assignMechanic(department.getId(), mechanic.getId());
        Department updatedDepartment = departmentService.removeMechanic(department.getId(), mechanic.getId());

        assertThat(updatedDepartment.getMechanics().size()).isEqualTo(0);
    }
}
