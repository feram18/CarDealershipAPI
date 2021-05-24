package edu.towson.cosc457.CarDealership.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import edu.towson.cosc457.CarDealership.exceptions.AlreadyAssignedException;
import edu.towson.cosc457.CarDealership.exceptions.NotFoundException;
import edu.towson.cosc457.CarDealership.misc.EmployeeType;
import edu.towson.cosc457.CarDealership.misc.Gender;
import edu.towson.cosc457.CarDealership.model.*;
import edu.towson.cosc457.CarDealership.service.ClientService;
import edu.towson.cosc457.CarDealership.service.SalesAssociateService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class SalesAssociateControllerTest {
    public MockMvc mockMvc;
    @Autowired
    public SalesAssociateController salesAssociateController;
    @MockBean
    public SalesAssociateService salesAssociateService;
    @MockBean
    public ClientService clientService;
    private final ObjectMapper mapper = new ObjectMapper();
    private SalesAssociate salesAssociate;
    private SalesAssociate editedSalesAssociate;
    private Client client;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(salesAssociateController).build();

        JavaTimeModule module = new JavaTimeModule();
        mapper.registerModule(module);

        salesAssociate = SalesAssociate.builder()
                .id(1L)
                .ssn("123-45-6789")
                .firstName("FirstName")
                .middleInitial('M')
                .lastName("LastName")
                .gender(Gender.FEMALE)
                .dateOfBirth(LocalDate.of(1999, 1, 1))
                .phoneNumber("123-456-7890")
                .email("sales@company.com")
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
                .employeeType(EmployeeType.SALES_ASSOCIATE)
                .manager(Manager.builder()
                        .id(2L)
                        .build())
                .department(Department.builder()
                        .id(1L)
                        .build())
                .clients(new ArrayList<>())
                .build();

        editedSalesAssociate = SalesAssociate.builder()
                .id(1L)
                .ssn("123-45-6789")
                .firstName("NewName")
                .middleInitial('M')
                .lastName("LastName")
                .gender(Gender.FEMALE)
                .dateOfBirth(LocalDate.of(1999, 1, 1))
                .phoneNumber("123-456-7890")
                .email("new-sales@company.com")
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
                .employeeType(EmployeeType.SALES_ASSOCIATE)
                .manager(Manager.builder()
                        .id(2L)
                        .build())
                .department(Department.builder()
                        .id(1L)
                        .build())
                .clients(new ArrayList<>())
                .build();

        client = Client.builder()
                .id(1L)
                .ssn("123-45-6789")
                .firstName("FirstName")
                .lastName("LastName")
                .gender(Gender.MALE)
                .email("email@company.com")
                .phoneNumber("123-456-7890")
                .address(Address.builder()
                        .id(1L)
                        .street("123 Main St.")
                        .city("New York City")
                        .state("New York")
                        .zipCode(12345)
                        .build())
                .minimumPrice(10000.01)
                .maximumPrice(15000.01)
                .build();
    }

    @Test
    @Disabled
    void shouldAddSalesAssociate() throws Exception {
        when(salesAssociateService.addEmployee(salesAssociate)).thenReturn(salesAssociate);

        mockMvc.perform(post("/api/v1/associates")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(salesAssociate)))
                .andExpect(status().is(201));
    }

    @Test
    void shouldFailToAddSalesAssociate_Null() throws Exception {
        mockMvc.perform(post("/api/v1/associates")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(null)))
                .andExpect(status().is(400)); // BAD_REQUEST
    }

    @Test
    void shouldGetAllSalesAssociates() throws Exception {
        when(salesAssociateService.getEmployees()).thenReturn(Collections.singletonList(salesAssociate));

        mockMvc.perform(get("/api/v1/associates")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200));
    }

    @Test
    void shouldGetAllSalesAssociates_EmptyList() throws Exception {
        when(salesAssociateService.getEmployees()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/v1/associates")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", empty()));
    }

    @Test
    void shouldGetSalesAssociateById() throws Exception {
        when(salesAssociateService.getEmployee(salesAssociate.getId())).thenReturn(salesAssociate);

        mockMvc.perform(get("/api/v1/associates/{id}", salesAssociate.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", is(salesAssociate.getId().intValue())))
                .andExpect(jsonPath("$.firstName", is(salesAssociate.getFirstName())))
                .andExpect(jsonPath("$.middleInitial", is(String.valueOf(salesAssociate.getMiddleInitial()))))
                .andExpect(jsonPath("$.lastName", is(salesAssociate.getLastName())))
                .andExpect(jsonPath("$.gender", is(String.valueOf(salesAssociate.getGender()))))
                .andExpect(jsonPath("$.dateOfBirth.[0]", is(salesAssociate.getDateOfBirth().getYear())))
                .andExpect(jsonPath("$.dateOfBirth.[1]", is(salesAssociate.getDateOfBirth().getMonthValue())))
                .andExpect(jsonPath("$.dateOfBirth.[2]", is(salesAssociate.getDateOfBirth().getDayOfMonth())))
                .andExpect(jsonPath("$.phoneNumber", is(salesAssociate.getPhoneNumber())))
                .andExpect(jsonPath("$.email", is(salesAssociate.getEmail())))
                .andExpect(jsonPath("$.workLocationId", is(salesAssociate.getWorkLocation().getId().intValue())))
                .andExpect(jsonPath("$.salary", is(salesAssociate.getSalary())))
                .andExpect(jsonPath("$.dateStarted.[0]", is(salesAssociate.getDateStarted().getYear())))
                .andExpect(jsonPath("$.dateStarted.[1]", is(salesAssociate.getDateStarted().getMonthValue())))
                .andExpect(jsonPath("$.dateStarted.[2]", is(salesAssociate.getDateStarted().getDayOfMonth())))
                .andExpect(jsonPath("$.address").isMap())
                .andExpect(jsonPath("$.address.id", is(salesAssociate.getAddress().getId().intValue())))
                .andExpect(jsonPath("$.hoursWorked", is(salesAssociate.getHoursWorked())))
                .andExpect(jsonPath("$.employeeType", is(String.valueOf(salesAssociate.getEmployeeType()))))
                .andExpect(jsonPath("$.managerId", is(salesAssociate.getManager().getId().intValue())))
                .andExpect(jsonPath("$.departmentId", is(salesAssociate.getDepartment().getId().intValue())));
    }

    @Test
    void shouldFailToGetSalesAssociateById_NotFound() throws Exception {
        when(salesAssociateService.getEmployee(salesAssociate.getId())).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/api/v1/associates/{id}", salesAssociate.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(404));
    }

    @Test
    void shouldDeleteSalesAssociate() throws Exception {
        when(salesAssociateService.deleteEmployee(salesAssociate.getId())).thenReturn(salesAssociate);

        mockMvc.perform(delete("/api/v1/associates/{id}", salesAssociate.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200));
    }

    @Test
    void shouldFailToDeleteSalesAssociate_NotFound() throws Exception {
        when(salesAssociateService.deleteEmployee(salesAssociate.getId())).thenThrow(NotFoundException.class);

        mockMvc.perform(delete("/api/v1/associates/{id}", salesAssociate.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(404));
    }

    @Test
    @Disabled
    void shouldUpdateSalesAssociate() throws Exception {
        when(salesAssociateService.editEmployee(salesAssociate.getId(), editedSalesAssociate))
                .thenReturn(editedSalesAssociate);

        mockMvc.perform(put("/api/v1/associates/{id}", salesAssociate.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(editedSalesAssociate)))
                .andExpect(status().is(200));
    }

    @Test
    @Disabled
    void shouldFailToUpdateSalesAssociate_NotFound() throws Exception {
        when(salesAssociateService.editEmployee(salesAssociate.getId(), editedSalesAssociate))
                .thenThrow(NotFoundException.class);

        mockMvc.perform(put("/api/v1/associates/{id}", salesAssociate.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(editedSalesAssociate)))
                .andExpect(status().is(404));
    }

    @Test
    void shouldAssignClientToAssociate() throws Exception {
        when(salesAssociateService.getEmployee(salesAssociate.getId())).thenReturn(salesAssociate);
        when(clientService.getClient(client.getId())).thenReturn(client);
        when(salesAssociateService.assignClient(salesAssociate.getId(), client.getId()))
                .thenReturn(salesAssociate);

        mockMvc.perform(post("/api/v1/associates/{associateId}/clients/{clientId}/add",
                salesAssociate.getId(),
                client.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200));
    }

    @Test
    void shouldFailToAssignClientToAssociate_AlreadyAssigned() throws Exception {
        when(salesAssociateService.getEmployee(salesAssociate.getId())).thenReturn(salesAssociate);
        when(clientService.getClient(client.getId())).thenReturn(client);
        when(salesAssociateService.assignClient(salesAssociate.getId(), client.getId()))
                .thenThrow(AlreadyAssignedException.class);

        mockMvc.perform(post("/api/v1/associates/{associateId}/clients/{clientId}/add",
                salesAssociate.getId(),
                client.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(409));
    }

    @Test
    void shouldFailToAssignClientToAssociate_NotFound() throws Exception {
        when(salesAssociateService.assignClient(salesAssociate.getId(), client.getId()))
                .thenThrow(NotFoundException.class);

        mockMvc.perform(post("/api/v1/associates/{associateId}/clients/{clientId}/add",
                salesAssociate.getId(),
                client.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(404));
    }

    @Test
    void shouldGetAssignedClients() throws Exception {
        when(salesAssociateService.getEmployee(salesAssociate.getId())).thenReturn(salesAssociate);
        when(clientService.getClient(client.getId())).thenReturn(client);
        when(salesAssociateService.assignClient(salesAssociate.getId(), client.getId())).thenReturn(salesAssociate);

        mockMvc.perform(get("/api/v1/associates/{id}/clients", salesAssociate.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200));
    }

    @Test
    void shouldGetAssignedClients_EmptyList() throws Exception {
        when(salesAssociateService.getEmployee(salesAssociate.getId())).thenReturn(salesAssociate);

        mockMvc.perform(get("/api/v1/associates/{id}/clients", salesAssociate.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", empty()));
    }

    @Test
    void shouldRemoveClientFromAssociate() throws Exception {
        when(salesAssociateService.getEmployee(salesAssociate.getId())).thenReturn(salesAssociate);
        when(clientService.getClient(client.getId())).thenReturn(client);
        when(salesAssociateService.assignClient(salesAssociate.getId(), client.getId())).thenReturn(salesAssociate);
        when(salesAssociateService.removeClient(salesAssociate.getId(), client.getId())).thenReturn(salesAssociate);

        mockMvc.perform(delete("/api/v1/associates/{associateId}/clients/{clientId}/remove",
                salesAssociate.getId(),
                client.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200));
    }

    @Test
    void shouldFailToRemoveClientFromAssociate_NotFound() throws Exception {
        when(salesAssociateService.removeClient(salesAssociate.getId(), client.getId()))
                .thenThrow(NotFoundException.class);

        mockMvc.perform(delete("/api/v1/associates/{associateId}/clients/{clientId}/remove",
                salesAssociate.getId(),
                client.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(404));
    }
}
