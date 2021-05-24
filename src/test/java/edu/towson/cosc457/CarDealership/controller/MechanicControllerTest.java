package edu.towson.cosc457.CarDealership.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import edu.towson.cosc457.CarDealership.exceptions.AlreadyAssignedException;
import edu.towson.cosc457.CarDealership.exceptions.NotFoundException;
import edu.towson.cosc457.CarDealership.misc.EmployeeType;
import edu.towson.cosc457.CarDealership.misc.Gender;
import edu.towson.cosc457.CarDealership.misc.Status;
import edu.towson.cosc457.CarDealership.model.*;
import edu.towson.cosc457.CarDealership.service.MechanicService;
import edu.towson.cosc457.CarDealership.service.ServiceTicketService;
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
public class MechanicControllerTest {
    public MockMvc mockMvc;
    @Autowired
    public MechanicController mechanicController;
    @MockBean
    public MechanicService mechanicService;
    @MockBean
    public ServiceTicketService serviceTicketService;
    private final ObjectMapper mapper = new ObjectMapper();
    private Mechanic mechanic;
    private Mechanic editedMechanic;
    private ServiceTicket serviceTicket;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(mechanicController).build();

        JavaTimeModule module = new JavaTimeModule();
        mapper.registerModule(module);

        mechanic = Mechanic.builder()
                .id(1L)
                .ssn("123-45-0000")
                .firstName("FirstName")
                .middleInitial('M')
                .lastName("LastName")
                .gender(Gender.MALE)
                .dateOfBirth(LocalDate.of(1999, 1, 1))
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

        editedMechanic = Mechanic.builder()
                .id(1L)
                .ssn("123-45-0000")
                .firstName("NewName")
                .middleInitial('M')
                .lastName("LastName")
                .gender(Gender.MALE)
                .dateOfBirth(LocalDate.of(1999, 1, 1))
                .phoneNumber("123-456-7890")
                .email("new-mechanic@company.com")
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
    @Disabled
    void shouldAddMechanic() throws Exception {
        when(mechanicService.addEmployee(mechanic)).thenReturn(mechanic);

        mockMvc.perform(post("/api/v1/mechanics")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(mechanic)))
                .andExpect(status().is(201));
    }

    @Test
    void shouldFailToAddMechanic_Null() throws Exception {
        mockMvc.perform(post("/api/v1/mechanics")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(null)))
                .andExpect(status().is(400)); // BAD_REQUEST
    }

    @Test
    void shouldGetAllMechanics() throws Exception {
        when(mechanicService.getEmployees()).thenReturn(Collections.singletonList(mechanic));

        mockMvc.perform(get("/api/v1/mechanics")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200));
    }

    @Test
    void shouldGetAllMechanics_EmptyList() throws Exception {
        when(mechanicService.getEmployees()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/v1/mechanics")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", empty()));
    }

    @Test
    void shouldGetMechanicById() throws Exception {
        when(mechanicService.getEmployee(mechanic.getId())).thenReturn(mechanic);

        mockMvc.perform(get("/api/v1/mechanics/{id}", mechanic.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", is(mechanic.getId().intValue())))
                .andExpect(jsonPath("$.firstName", is(mechanic.getFirstName())))
                .andExpect(jsonPath("$.middleInitial", is(String.valueOf(mechanic.getMiddleInitial()))))
                .andExpect(jsonPath("$.lastName", is(mechanic.getLastName())))
                .andExpect(jsonPath("$.gender", is(String.valueOf(mechanic.getGender()))))
                .andExpect(jsonPath("$.dateOfBirth.[0]", is(mechanic.getDateOfBirth().getYear())))
                .andExpect(jsonPath("$.dateOfBirth.[1]", is(mechanic.getDateOfBirth().getMonthValue())))
                .andExpect(jsonPath("$.dateOfBirth.[2]", is(mechanic.getDateOfBirth().getDayOfMonth())))
                .andExpect(jsonPath("$.phoneNumber", is(mechanic.getPhoneNumber())))
                .andExpect(jsonPath("$.email", is(mechanic.getEmail())))
                .andExpect(jsonPath("$.workLocationId", is(mechanic.getWorkLocation().getId().intValue())))
                .andExpect(jsonPath("$.salary", is(mechanic.getSalary())))
                .andExpect(jsonPath("$.dateStarted.[0]", is(mechanic.getDateStarted().getYear())))
                .andExpect(jsonPath("$.dateStarted.[1]", is(mechanic.getDateStarted().getMonthValue())))
                .andExpect(jsonPath("$.dateStarted.[2]", is(mechanic.getDateStarted().getDayOfMonth())))
                .andExpect(jsonPath("$.address").isMap())
                .andExpect(jsonPath("$.address.id", is(mechanic.getAddress().getId().intValue())))
                .andExpect(jsonPath("$.hoursWorked", is(mechanic.getHoursWorked())))
                .andExpect(jsonPath("$.employeeType", is(String.valueOf(mechanic.getEmployeeType()))))
                .andExpect(jsonPath("$.managerId", is(mechanic.getManager().getId().intValue())))
                .andExpect(jsonPath("$.departmentId", is(mechanic.getDepartment().getId().intValue())));
    }

    @Test
    void shouldFailToGetMechanicById_NotFound() throws Exception {
        when(mechanicService.getEmployee(mechanic.getId())).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/api/v1/mechanics/{id}", mechanic.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(404));
    }

    @Test
    void shouldDeleteMechanic() throws Exception {
        when(mechanicService.deleteEmployee(mechanic.getId())).thenReturn(mechanic);

        mockMvc.perform(delete("/api/v1/mechanics/{id}", mechanic.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200));
    }

    @Test
    void shouldFailToDeleteMechanic_NotFound() throws Exception {
        when(mechanicService.deleteEmployee(mechanic.getId())).thenThrow(NotFoundException.class);

        mockMvc.perform(delete("/api/v1/mechanics/{id}", mechanic.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(404));
    }

    @Test
    @Disabled
    void shouldUpdateMechanic() throws Exception {
        when(mechanicService.editEmployee(mechanic.getId(), editedMechanic)).thenReturn(editedMechanic);

        mockMvc.perform(put("/api/v1/mechanics/{id}", mechanic.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(editedMechanic)))
                .andExpect(status().is(200));
    }

    @Test
    @Disabled
    void shouldFailToUpdateMechanic_NotFound() throws Exception {
        when(mechanicService.editEmployee(mechanic.getId(), editedMechanic)).thenThrow(NotFoundException.class);

        mockMvc.perform(put("/api/v1/mechanics/{id}", mechanic.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(editedMechanic)))
                .andExpect(status().is(404));
    }

    @Test
    void shouldAssignTicketToMechanic() throws Exception {
        when(mechanicService.getEmployee(mechanic.getId())).thenReturn(mechanic);
        when(serviceTicketService.getServiceTicket(serviceTicket.getId())).thenReturn(serviceTicket);
        when(mechanicService.assignTicket(mechanic.getId(), serviceTicket.getId())).thenReturn(mechanic);

        mockMvc.perform(post("/api/v1/mechanics/{mechanicId}/tickets/{ticketId}/add",
                mechanic.getId(),
                serviceTicket.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200));
    }

    @Test
    void shouldFailToAssignTicketToMechanic_AlreadyAssigned() throws Exception {
        when(mechanicService.getEmployee(mechanic.getId())).thenReturn(mechanic);
        when(serviceTicketService.getServiceTicket(serviceTicket.getId())).thenReturn(serviceTicket);
        when(mechanicService.assignTicket(mechanic.getId(), serviceTicket.getId()))
                .thenThrow(AlreadyAssignedException.class);

        mockMvc.perform(post("/api/v1/mechanics/{mechanicId}/tickets/{ticketId}/add",
                mechanic.getId(),
                serviceTicket.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(409));
    }

    @Test
    @Disabled
    void shouldFailToAssignTicketToMechanic_TicketNotFound() throws Exception {
        when(mechanicService.getEmployee(mechanic.getId())).thenReturn(mechanic);
        when(serviceTicketService.getServiceTicket(serviceTicket.getId())).thenThrow(NotFoundException.class);

        mockMvc.perform(post("/api/v1/mechanics/{mechanicId}/tickets/{ticketId}/add",
                mechanic.getId(),
                serviceTicket.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(404));
    }

    @Test
    void shouldGetAssignedTickets() throws Exception {
        when(mechanicService.getEmployee(mechanic.getId())).thenReturn(mechanic);
        when(serviceTicketService.getServiceTicket(serviceTicket.getId())).thenReturn(serviceTicket);
        when(mechanicService.assignTicket(mechanic.getId(), serviceTicket.getId())).thenReturn(mechanic);

        mockMvc.perform(get("/api/v1/mechanics/{id}/tickets", mechanic.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200));
    }

    @Test
    void shouldGetAssignedTickets_EmptyList() throws Exception {
        when(mechanicService.getEmployee(mechanic.getId())).thenReturn(mechanic);

        mockMvc.perform(get("/api/v1/mechanics/{id}/tickets", mechanic.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", empty()));
    }

    @Test
    void shouldRemoveTicketFromMechanic() throws Exception {
        when(mechanicService.getEmployee(mechanic.getId())).thenReturn(mechanic);
        when(serviceTicketService.getServiceTicket(serviceTicket.getId())).thenReturn(serviceTicket);
        when(mechanicService.assignTicket(mechanic.getId(), serviceTicket.getId())).thenReturn(mechanic);
        when(mechanicService.removeTicket(mechanic.getId(), serviceTicket.getId())).thenReturn(mechanic);

        mockMvc.perform(delete("/api/v1/mechanics/{mechanicId}/tickets/{ticketId}/remove",
                mechanic.getId(),
                serviceTicket.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200));
    }

    @Test
    void shouldFailToRemoveTicketFromMechanic_NotFound() throws Exception {
        when(mechanicService.removeTicket(mechanic.getId(), serviceTicket.getId())).thenThrow(NotFoundException.class);

        mockMvc.perform(delete("/api/v1/mechanics/{mechanicId}/tickets/{ticketId}/remove",
                mechanic.getId(),
                serviceTicket.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(404));
    }
}
