package edu.towson.cosc457.CarDealership.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import edu.towson.cosc457.CarDealership.controller.DepartmentController;
import edu.towson.cosc457.CarDealership.misc.EmployeeType;
import edu.towson.cosc457.CarDealership.misc.Gender;
import edu.towson.cosc457.CarDealership.model.*;
import edu.towson.cosc457.CarDealership.service.DepartmentService;
import edu.towson.cosc457.CarDealership.service.MechanicService;
import org.junit.jupiter.api.BeforeEach;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class DepartmentControllerTest {
    public MockMvc mockMvc;
    @Autowired
    public DepartmentController departmentController;
    @MockBean
    public DepartmentService departmentService;
    @MockBean
    public MechanicService mechanicService;
    private final ObjectMapper mapper = new ObjectMapper();
    private Department department;
    private Mechanic mechanic;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(departmentController).build();

        JavaTimeModule module = new JavaTimeModule();
        mapper.registerModule(module);

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
    void shouldAddDepartment() throws Exception {
        when(departmentService.addDepartment(department)).thenReturn(department);

        mockMvc.perform(post("/api/v1/departments")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(department)))
                .andExpect(status().is(201));
    }

    @Test
    void shouldGetAllDepartments() throws Exception {
        when(departmentService.getDepartments()).thenReturn(Collections.singletonList(department));

        mockMvc.perform(get("/api/v1/departments"))
                .andExpect(status().is(200));
    }

    @Test
    void shouldGetDepartmentById() throws Exception {
        when(departmentService.getDepartment(department.getId())).thenReturn(department);

        mockMvc.perform(get("/api/v1/departments/{id}", department.getId()))
                .andExpect(status().is(200));
    }

    @Test
    void shouldDeleteDepartment() throws Exception {
        when(departmentService.deleteDepartment(department.getId())).thenReturn(department);

        mockMvc.perform(delete("/api/v1/departments/{id}", department.getId()))
                .andExpect(status().is(200));
    }

    @Test
    void shouldUpdateDepartment() throws Exception {
        Department editedDepartment = Department.builder()
                .id(1L)
                .name("Department E")
                .manager(Manager.builder()
                        .id(1L)
                        .build())
                .location(Location.builder()
                        .id(1L)
                        .build())
                .mechanics(new ArrayList<>())
                .build();
        when(departmentService.editDepartment(department.getId(), editedDepartment)).thenReturn(editedDepartment);

        mockMvc.perform(put("/api/v1/departments/{id}", department.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(editedDepartment)))
                .andExpect(status().is(200));
    }

    @Test
    void shouldAddMechanicToDepartment() throws Exception {
        when(departmentService.getDepartment(department.getId())).thenReturn(department);
        when(mechanicService.getEmployee(mechanic.getId())).thenReturn(mechanic);
        when(departmentService.assignMechanic(department.getId(), mechanic.getId())).thenReturn(department);

        mockMvc.perform(
                post("/api/v1/departments/{departmentId}/mechanics/{mechanicId}/add",
                        department.getId(),
                        mechanic.getId())
        ).andExpect(status().is(200));
    }

    @Test
    void shouldGetMechanics() throws Exception {
        when(departmentService.getDepartment(department.getId())).thenReturn(department);
        when(mechanicService.getEmployee(mechanic.getId())).thenReturn(mechanic);
        when(departmentService.assignMechanic(department.getId(), mechanic.getId())).thenReturn(department);

        mockMvc.perform(get("/api/v1/departments/{departmentId}/mechanics", department.getId()))
                .andExpect(status().is(200));
    }

    @Test
    void shouldRemoveMechanicFromDepartment() throws Exception {
        when(departmentService.getDepartment(department.getId())).thenReturn(department);
        when(mechanicService.getEmployee(mechanic.getId())).thenReturn(mechanic);
        when(departmentService.assignMechanic(department.getId(), mechanic.getId())).thenReturn(department);
        when(departmentService.removeMechanic(department.getId(), mechanic.getId())).thenReturn(department);


        mockMvc.perform(
                delete("/api/v1/departments/{departmentId}/mechanics/{mechanicId}/remove",
                        department.getId(),
                        mechanic.getId())
        ).andExpect(status().is(200));
    }
}
