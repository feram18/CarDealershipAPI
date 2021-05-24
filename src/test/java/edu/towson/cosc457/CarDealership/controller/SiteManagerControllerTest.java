package edu.towson.cosc457.CarDealership.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import edu.towson.cosc457.CarDealership.exceptions.AlreadyAssignedException;
import edu.towson.cosc457.CarDealership.exceptions.NotFoundException;
import edu.towson.cosc457.CarDealership.misc.EmployeeType;
import edu.towson.cosc457.CarDealership.misc.Gender;
import edu.towson.cosc457.CarDealership.model.*;
import edu.towson.cosc457.CarDealership.service.ManagerService;
import edu.towson.cosc457.CarDealership.service.SiteManagerService;
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

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class SiteManagerControllerTest {
    public MockMvc mockMvc;
    @Autowired
    public SiteManagerController siteManagerController;
    @MockBean
    public SiteManagerService siteManagerService;
    @MockBean
    public ManagerService managerService;
    private final ObjectMapper mapper = new ObjectMapper();
    private SiteManager siteManager;
    private SiteManager editedSiteManager;
    private Manager manager;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(siteManagerController).build();

        JavaTimeModule module = new JavaTimeModule();
        mapper.registerModule(module);

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
                .employeeType(EmployeeType.SITE_MANAGER)
                .managedLocation(Location.builder()
                        .id(1L)
                        .build())
                .managers(new ArrayList<>())
                .build();

        editedSiteManager = SiteManager.builder()
                .id(1L)
                .ssn("123-45-6789")
                .firstName("NewName")
                .middleInitial('M')
                .lastName("LastName")
                .gender(Gender.FEMALE)
                .dateOfBirth(LocalDate.now())
                .phoneNumber("123-456-7890")
                .email("new-site.manager@company.com")
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
                .employeeType(EmployeeType.SITE_MANAGER)
                .managedLocation(Location.builder()
                        .id(1L)
                        .build())
                .managers(new ArrayList<>())
                .build();

        manager = Manager.builder()
                .id(2L)
                .ssn("123-45-1515")
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
                        .build())
                .mechanics(new ArrayList<>())
                .build();
    }

    @Test
    @Disabled
    void shouldAddSiteManager() throws Exception {
        when(siteManagerService.addEmployee(siteManager)).thenReturn(siteManager);

        mockMvc.perform(post("/api/v1/site-managers")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(siteManager)))
                .andExpect(status().is(201));
    }

    @Test
    void shouldFailToAddSiteManager_Null() throws Exception {
        mockMvc.perform(post("/api/v1/site-managers")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(null)))
                .andExpect(status().is(400)); // BAD_REQUEST
    }

    @Test
    void shouldGetAllSiteManagers() throws Exception {
        when(siteManagerService.getEmployees()).thenReturn(Collections.singletonList(siteManager));

        mockMvc.perform(get("/api/v1/site-managers")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200));
    }

    @Test
    void shouldGetAllSiteManagers_EmptyList() throws Exception {
        when(siteManagerService.getEmployees()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/v1/site-managers")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", empty()));
    }

    @Test
    void shouldGetSiteManagerById() throws Exception {
        when(siteManagerService.getEmployee(siteManager.getId())).thenReturn(siteManager);

        mockMvc.perform(get("/api/v1/site-managers/{id}", siteManager.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", is(siteManager.getId().intValue())))
                .andExpect(jsonPath("$.firstName", is(siteManager.getFirstName())))
                .andExpect(jsonPath("$.middleInitial", is(String.valueOf(siteManager.getMiddleInitial()))))
                .andExpect(jsonPath("$.lastName", is(siteManager.getLastName())))
                .andExpect(jsonPath("$.gender", is(String.valueOf(siteManager.getGender()))))
                .andExpect(jsonPath("$.dateOfBirth.[0]", is(siteManager.getDateOfBirth().getYear())))
                .andExpect(jsonPath("$.dateOfBirth.[1]", is(siteManager.getDateOfBirth().getMonthValue())))
                .andExpect(jsonPath("$.dateOfBirth.[2]", is(siteManager.getDateOfBirth().getDayOfMonth())))
                .andExpect(jsonPath("$.phoneNumber", is(siteManager.getPhoneNumber())))
                .andExpect(jsonPath("$.email", is(siteManager.getEmail())))
                .andExpect(jsonPath("$.workLocationId", is(siteManager.getWorkLocation().getId().intValue())))
                .andExpect(jsonPath("$.salary", is(siteManager.getSalary())))
                .andExpect(jsonPath("$.dateStarted.[0]", is(siteManager.getDateStarted().getYear())))
                .andExpect(jsonPath("$.dateStarted.[1]", is(siteManager.getDateStarted().getMonthValue())))
                .andExpect(jsonPath("$.dateStarted.[2]", is(siteManager.getDateStarted().getDayOfMonth())))
                .andExpect(jsonPath("$.address").isMap())
                .andExpect(jsonPath("$.address.id", is(siteManager.getAddress().getId().intValue())))
                .andExpect(jsonPath("$.hoursWorked", is(siteManager.getHoursWorked())))
                .andExpect(jsonPath("$.employeeType", is(String.valueOf(siteManager.getEmployeeType()))))
                .andExpect(jsonPath("$.managedLocationId", is(siteManager.getManagedLocation().getId().intValue())));
    }

    @Test
    void shouldFailToGetSiteManagerById_NotFound() throws Exception {
        when(siteManagerService.getEmployee(siteManager.getId())).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/api/v1/site-managers/{id}", siteManager.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(404));
    }

    @Test
    void shouldDeleteSiteManager() throws Exception {
        when(siteManagerService.deleteEmployee(siteManager.getId())).thenReturn(siteManager);

        mockMvc.perform(delete("/api/v1/site-managers/{id}", siteManager.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200));
    }

    @Test
    void shouldFailToDeleteSiteManager_NotFound() throws Exception {
        when(siteManagerService.deleteEmployee(siteManager.getId())).thenThrow(NotFoundException.class);

        mockMvc.perform(delete("/api/v1/site-managers/{id}", siteManager.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(404));
    }

    @Test
    @Disabled
    void shouldUpdateSiteManager() throws Exception {
        when(siteManagerService.editEmployee(siteManager.getId(), editedSiteManager)).thenReturn(editedSiteManager);

        mockMvc.perform(put("/api/v1/site-managers/{id}", siteManager.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(editedSiteManager)))
                .andExpect(status().is(200));
    }

    @Test
    @Disabled
    void shouldFailToUpdateSiteManager_NotFound() throws Exception {
        when(siteManagerService.editEmployee(siteManager.getId(), editedSiteManager)).thenThrow(NotFoundException.class);

        mockMvc.perform(put("/api/v1/site-managers/{id}", siteManager.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(editedSiteManager)))
                .andExpect(status().is(404));
    }

    @Test
    void shouldAssignManagerToSiteManager() throws Exception {
        when(siteManagerService.getEmployee(siteManager.getId())).thenReturn(siteManager);
        when(managerService.getEmployee(manager.getId())).thenReturn(manager);
        when(siteManagerService.assignToManager(siteManager.getId(), manager.getId())).thenReturn(siteManager);

        mockMvc.perform(post("/api/v1/site-managers/{siteManagerId}/managers/{managerId}/add",
                siteManager.getId(),
                manager.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200));
    }

    @Test
    void shouldFailToAssignManagerToSiteManager_AlreadyAssigned() throws Exception {
        when(siteManagerService.getEmployee(siteManager.getId())).thenReturn(siteManager);
        when(managerService.getEmployee(manager.getId())).thenReturn(manager);
        when(siteManagerService.assignToManager(siteManager.getId(), manager.getId()))
                .thenThrow(AlreadyAssignedException.class);

        mockMvc.perform(post("/api/v1/site-managers/{siteManagerId}/managers/{managerId}/add",
                siteManager.getId(),
                manager.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(409));
    }

    @Test
    void shouldFailToAssignManagerToSiteManager_NotFound() throws Exception {
        when(siteManagerService.getEmployee(siteManager.getId())).thenReturn(siteManager);
        when(managerService.getEmployee(manager.getId())).thenReturn(manager);
        when(siteManagerService.assignToManager(siteManager.getId(), manager.getId()))
                .thenThrow(NotFoundException.class);

        mockMvc.perform(post("/api/v1/site-managers/{siteManagerId}/managers/{managerId}/add",
                siteManager.getId(),
                manager.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(404));
    }

    @Test
    void shouldGetAssignedManagers() throws Exception {
        when(siteManagerService.getEmployee(siteManager.getId())).thenReturn(siteManager);
        when(managerService.getEmployee(manager.getId())).thenReturn(manager);
        when(siteManagerService.assignToManager(siteManager.getId(), manager.getId())).thenReturn(siteManager);

        mockMvc.perform(get("/api/v1/site-managers/{id}/managers", siteManager.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200));
    }

    @Test
    void shouldGetAssignedManagers_EmptyList() throws Exception {
        when(siteManagerService.getEmployee(siteManager.getId())).thenReturn(siteManager);

        mockMvc.perform(get("/api/v1/site-managers/{id}/managers", siteManager.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", empty()));
    }

    @Test
    void shouldRemoveManagerFromSiteManager() throws Exception {
        when(siteManagerService.getEmployee(siteManager.getId())).thenReturn(siteManager);
        when(managerService.getEmployee(manager.getId())).thenReturn(manager);
        when(siteManagerService.assignToManager(siteManager.getId(), manager.getId())).thenReturn(siteManager);
        when(siteManagerService.removeFromManager(siteManager.getId(), manager.getId())).thenReturn(siteManager);

        mockMvc.perform(delete("/api/v1/site-managers/{siteManagerId}/managers/{managerId}/remove",
                siteManager.getId(),
                manager.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200));
    }

    @Test
    void shouldFailToRemoveManagerFromSiteManager_NotFound() throws Exception {
        when(siteManagerService.removeFromManager(siteManager.getId(), manager.getId()))
                .thenThrow(NotFoundException.class);

        mockMvc.perform(delete("/api/v1/site-managers/{siteManagerId}/managers/{managerId}/remove",
                siteManager.getId(),
                manager.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(404));
    }
}
