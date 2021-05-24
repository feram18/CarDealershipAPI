package edu.towson.cosc457.CarDealership.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import edu.towson.cosc457.CarDealership.exceptions.NotFoundException;
import edu.towson.cosc457.CarDealership.misc.EmployeeType;
import edu.towson.cosc457.CarDealership.misc.Gender;
import edu.towson.cosc457.CarDealership.model.*;
import edu.towson.cosc457.CarDealership.service.ManagerService;
import edu.towson.cosc457.CarDealership.service.MechanicService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class ManagerControllerTest {
    public MockMvc mockMvc;
    @Autowired
    public ManagerController managerController;
    @MockBean
    public ManagerService managerService;
    @MockBean
    public MechanicService mechanicService;
    private final ObjectMapper mapper = new ObjectMapper();
    private Manager manager;
    private Manager editedManager;
    private Mechanic mechanic;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(managerController).build();

        JavaTimeModule module = new JavaTimeModule();
        mapper.registerModule(module);

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
                .firstName("NewName")
                .middleInitial('M')
                .lastName("NewLName")
                .gender(Gender.MALE)
                .dateOfBirth(LocalDate.now())
                .phoneNumber("123-456-7890")
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
                .id(3L)
                .ssn("123-45-0000")
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
                        .id(2L)
                        .street("123 Main St.")
                        .city("New York City")
                        .state("New York")
                        .zipCode(12345)
                        .build())
                .hoursWorked(780.00)
                .employeeType(EmployeeType.MECHANIC)
                .department(Department.builder()
                        .id(1L)
                        .build())
                .build();
    }

    @Test
    @Disabled
    void shouldAddManager() throws Exception {
        when(managerService.addEmployee(manager)).thenReturn(manager);

        mockMvc.perform(post("/api/v1/managers")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(manager)))
                .andExpect(status().is(201));
    }

    @Test
    void shouldFailToAddManager_Null() throws Exception {
        mockMvc.perform(post("/api/v1/managers")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(null)))
                .andExpect(status().is(400)); // BAD_REQUEST
    }

    @Test
    void shouldGetAllManagers() throws Exception {
        when(managerService.getEmployees()).thenReturn(Collections.singletonList(manager));

        mockMvc.perform(get("/api/v1/managers")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200));
    }

    @Test
    void shouldGetAllManagers_EmptyList() throws Exception {
        when(managerService.getEmployees()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/v1/managers")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", empty()));
    }

    @Test
    void shouldGetManagerById() throws Exception {
        when(managerService.getEmployee(manager.getId())).thenReturn(manager);

        mockMvc.perform(get("/api/v1/managers/{id}", manager.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", is(manager.getId().intValue())))
                .andExpect(jsonPath("$.firstName", is(manager.getFirstName())))
                .andExpect(jsonPath("$.middleInitial", is(String.valueOf(manager.getMiddleInitial()))))
                .andExpect(jsonPath("$.lastName", is(manager.getLastName())))
                .andExpect(jsonPath("$.gender", is(String.valueOf(manager.getGender()))))
                .andExpect(jsonPath("$.dateOfBirth.[0]", is(manager.getDateOfBirth().getYear())))
                .andExpect(jsonPath("$.dateOfBirth.[1]", is(manager.getDateOfBirth().getMonthValue())))
                .andExpect(jsonPath("$.dateOfBirth.[2]", is(manager.getDateOfBirth().getDayOfMonth())))
                .andExpect(jsonPath("$.phoneNumber", is(manager.getPhoneNumber())))
                .andExpect(jsonPath("$.email", is(manager.getEmail())))
                .andExpect(jsonPath("$.workLocationId", is(manager.getWorkLocation().getId().intValue())))
                .andExpect(jsonPath("$.salary", is(manager.getSalary())))
                .andExpect(jsonPath("$.dateStarted.[0]", is(manager.getDateStarted().getYear())))
                .andExpect(jsonPath("$.dateStarted.[1]", is(manager.getDateStarted().getMonthValue())))
                .andExpect(jsonPath("$.dateStarted.[2]", is(manager.getDateStarted().getDayOfMonth())))
                .andExpect(jsonPath("$.address").isMap())
                .andExpect(jsonPath("$.address.id", is(manager.getAddress().getId().intValue())))
                .andExpect(jsonPath("$.hoursWorked", is(manager.getHoursWorked())))
                .andExpect(jsonPath("$.employeeType", is(String.valueOf(manager.getEmployeeType()))))
                .andExpect(jsonPath("$.siteManagerId", is(manager.getSiteManager().getId().intValue())))
                .andExpect(jsonPath("$.departmentId", is(manager.getDepartment().getId().intValue())));
    }

    @Test
    void shouldFailToGetManagerById_NotFound() throws Exception {
        when(managerService.getEmployee(manager.getId())).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/api/v1/managers/{id}", manager.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(404));
    }

    @Test
    void shouldDeleteManager() throws Exception {
        when(managerService.deleteEmployee(manager.getId())).thenReturn(manager);

        mockMvc.perform(delete("/api/v1/managers/{id}", manager.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200));
    }

    @Test
    void shouldFailToDeleteManager_NotFound() throws Exception {
        when(managerService.deleteEmployee(manager.getId())).thenThrow(NotFoundException.class);

        mockMvc.perform(delete("/api/v1/managers/{id}", manager.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(404));
    }

    @Test
    @Disabled
    void shouldUpdateManager() throws Exception {
        when(managerService.editEmployee(manager.getId(), editedManager)).thenReturn(editedManager);

        mockMvc.perform(put("/api/v1/managers/{id}", manager.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(editedManager)))
                .andExpect(status().is(200));
    }

    @Test
    @Disabled
    void shouldFailToUpdateManager_NotFound() throws Exception {
        when(managerService.editEmployee(manager.getId(), editedManager)).thenThrow(NotFoundException.class);

        System.out.println(mockMvc.perform(put("/api/v1/managers/{id}", manager.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(editedManager)))
                .andExpect(status().is(404)));
    }

    @Test
    void shouldAssignMechanicToManager() throws Exception {
        when(managerService.getEmployee(manager.getId())).thenReturn(manager);
        when(mechanicService.getEmployee(mechanic.getId())).thenReturn(mechanic);
        when(managerService.assignToManager(manager.getId(), mechanic.getId())).thenReturn(manager);

        mockMvc.perform(post("/api/v1/managers/{managerId}/mechanics/{mechanicId}/add",
                manager.getId(),
                mechanic.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200));
    }

    @Test
    void shouldFailToAssignMechanicToManager_NotFound() throws Exception {
        when(managerService.assignToManager(manager.getId(), mechanic.getId())).thenThrow(NotFoundException.class);

        mockMvc.perform(post("/api/v1/managers/{managerId}/mechanics/{mechanicId}/add",
                manager.getId(),
                mechanic.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(404));
    }

    @Test
    void shouldGetAssignedMechanics() throws Exception {
        when(managerService.getEmployee(manager.getId())).thenReturn(manager);
        when(mechanicService.getEmployee(mechanic.getId())).thenReturn(mechanic);
        when(managerService.assignToManager(manager.getId(), mechanic.getId())).thenReturn(manager);

        mockMvc.perform(get("/api/v1/managers/{id}/mechanics", manager.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200));
    }

    @Test
    void shouldGetAssignedMechanics_EmptyList() throws Exception {
        when(managerService.getEmployee(manager.getId())).thenReturn(manager);

        mockMvc.perform(get("/api/v1/managers/{id}/mechanics", manager.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", empty()));
    }

    @Test
    void shouldRemoveMechanicFromManager() throws Exception {
        when(managerService.getEmployee(manager.getId())).thenReturn(manager);
        when(mechanicService.getEmployee(mechanic.getId())).thenReturn(mechanic);
        when(managerService.assignToManager(manager.getId(), mechanic.getId())).thenReturn(manager);
        when(managerService.removeFromManager(manager.getId(), mechanic.getId())).thenReturn(manager);

        mockMvc.perform(delete("/api/v1/managers/{managerId}/mechanics/{mechanicId}/remove",
                manager.getId(),
                mechanic.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200));
    }

    @Test
    void shouldFailToRemoveMechanicFromManager_NotFound() throws Exception {
        when(managerService.removeFromManager(manager.getId(), mechanic.getId())).thenThrow(NotFoundException.class);

        mockMvc.perform(delete("/api/v1/managers/{managerId}/mechanics/{mechanicId}/remove",
                manager.getId(),
                mechanic.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(404));
    }
}
