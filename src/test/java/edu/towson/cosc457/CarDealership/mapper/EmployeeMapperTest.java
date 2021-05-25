package edu.towson.cosc457.CarDealership.mapper;

import edu.towson.cosc457.CarDealership.misc.EmployeeType;
import edu.towson.cosc457.CarDealership.misc.Gender;
import edu.towson.cosc457.CarDealership.model.*;
import edu.towson.cosc457.CarDealership.model.dto.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class EmployeeMapperTest {
    private static EmployeeMapper employeeMapper;

    @BeforeAll
    public static void setUp() {
        employeeMapper = new EmployeeMapperImpl();
        AddressMapper addressMapper = new AddressMapperImpl();
        ReflectionTestUtils.setField(employeeMapper, "addressMapper", addressMapper);
    }

    @Test
    void shouldMapToManagerDto() {
        Manager manager = Manager.builder()
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
                .build();

        ManagerDto managerDto = employeeMapper.toManagerDto(manager);

        assertAll(() -> {
            assertThat(managerDto).isInstanceOf(ManagerDto.class);
            assertEquals(managerDto.getId(), manager.getId());
            assertEquals(managerDto.getFirstName(), manager.getFirstName());
            assertEquals(managerDto.getMiddleInitial(), manager.getMiddleInitial());
            assertEquals(managerDto.getLastName(), manager.getLastName());
            assertEquals(managerDto.getGender(), manager.getGender());
            assertEquals(managerDto.getDateOfBirth(), manager.getDateOfBirth());
            assertEquals(managerDto.getPhoneNumber(), manager.getPhoneNumber());
            assertEquals(managerDto.getEmail(), manager.getEmail());
            assertEquals(managerDto.getWorkLocationId(), manager.getWorkLocation().getId());
            assertEquals(managerDto.getSalary(), manager.getSalary());
            assertEquals(managerDto.getDateStarted(), manager.getDateStarted());
            assertEquals(managerDto.getAddress().getId(), manager.getAddress().getId());
            assertEquals(managerDto.getAddress().getStreet(), manager.getAddress().getStreet());
            assertEquals(managerDto.getAddress().getCity(), manager.getAddress().getCity());
            assertEquals(managerDto.getAddress().getState(), manager.getAddress().getState());
            assertEquals(managerDto.getAddress().getZipCode(), manager.getAddress().getZipCode());
            assertEquals(managerDto.getHoursWorked(), manager.getHoursWorked());
            assertEquals(managerDto.getEmployeeType(), manager.getEmployeeType());
            assertEquals(managerDto.getSiteManagerId(), manager.getSiteManager().getId());
            assertEquals(managerDto.getDepartmentId(), manager.getDepartment().getId());
        });
    }

    @Test
    void shouldMapFromManagerDto() {
        ManagerDto managerDto = ManagerDto.builder()
                .id(1L)
                .firstName("FirstName")
                .middleInitial('M')
                .lastName("LastName")
                .gender(Gender.MALE)
                .dateOfBirth(LocalDate.now())
                .phoneNumber("123-456-7890")
                .email("manager@company.com")
                .workLocationId(1L)
                .salary(70000.00)
                .dateStarted(LocalDate.now())
                .address(AddressDto.builder()
                        .id(1L)
                        .street("123 Main St.")
                        .city("New York City")
                        .state("New York")
                        .zipCode(12345)
                        .build())
                .hoursWorked(780.00)
                .employeeType(EmployeeType.MANAGER)
                .siteManagerId(2L)
                .departmentId(1L)
                .build();

        Manager manager = employeeMapper.toManager(managerDto);

        assertAll(() -> {
            assertThat(manager).isInstanceOf(Manager.class);
            assertEquals(manager.getId(), managerDto.getId());
            assertEquals(manager.getFirstName(), managerDto.getFirstName());
            assertEquals(manager.getMiddleInitial(), managerDto.getMiddleInitial());
            assertEquals(manager.getLastName(), managerDto.getLastName());
            assertEquals(manager.getGender(), managerDto.getGender());
            assertEquals(manager.getDateOfBirth(), managerDto.getDateOfBirth());
            assertEquals(manager.getPhoneNumber(), managerDto.getPhoneNumber());
            assertEquals(manager.getEmail(), managerDto.getEmail());
            assertEquals(manager.getSalary(), managerDto.getSalary());
            assertEquals(manager.getDateStarted(), managerDto.getDateStarted());
            assertEquals(manager.getAddress().getId(), managerDto.getAddress().getId());
            assertEquals(manager.getAddress().getStreet(), managerDto.getAddress().getStreet());
            assertEquals(manager.getAddress().getCity(), managerDto.getAddress().getCity());
            assertEquals(manager.getAddress().getState(), managerDto.getAddress().getState());
            assertEquals(manager.getAddress().getZipCode(), managerDto.getAddress().getZipCode());
            assertEquals(manager.getHoursWorked(), managerDto.getHoursWorked());
            assertEquals(manager.getEmployeeType(), managerDto.getEmployeeType());
        });
    }

    @Test
    void shouldReturnNullManagerEntity() {
        Manager manager = employeeMapper.toManager(null);

        assertThat(manager).isEqualTo(null);
    }

    @Test
    void shouldReturnNullManagerDto() {
        ManagerDto managerDto = employeeMapper.toManagerDto(null);

        assertThat(managerDto).isEqualTo(null);
    }

    @Test
    void shouldMapToMechanicDto() {
        Mechanic mechanic = Mechanic.builder()
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
                .department(Department.builder()
                        .id(1L)
                        .build())
                .build();

        MechanicDto mechanicDto = employeeMapper.toMechanicDto(mechanic);

        assertAll(() -> {
            assertThat(mechanicDto).isInstanceOf(MechanicDto.class);
            assertEquals(mechanicDto.getId(), mechanic.getId());
            assertEquals(mechanicDto.getFirstName(), mechanic.getFirstName());
            assertEquals(mechanicDto.getMiddleInitial(), mechanic.getMiddleInitial());
            assertEquals(mechanicDto.getLastName(), mechanic.getLastName());
            assertEquals(mechanicDto.getGender(), mechanic.getGender());
            assertEquals(mechanicDto.getDateOfBirth(), mechanic.getDateOfBirth());
            assertEquals(mechanicDto.getPhoneNumber(), mechanic.getPhoneNumber());
            assertEquals(mechanicDto.getEmail(), mechanic.getEmail());
            assertEquals(mechanicDto.getWorkLocationId(), mechanic.getWorkLocation().getId());
            assertEquals(mechanicDto.getSalary(), mechanic.getSalary());
            assertEquals(mechanicDto.getDateStarted(), mechanic.getDateStarted());
            assertEquals(mechanicDto.getAddress().getId(), mechanic.getAddress().getId());
            assertEquals(mechanicDto.getAddress().getStreet(), mechanic.getAddress().getStreet());
            assertEquals(mechanicDto.getAddress().getCity(), mechanic.getAddress().getCity());
            assertEquals(mechanicDto.getAddress().getState(), mechanic.getAddress().getState());
            assertEquals(mechanicDto.getAddress().getZipCode(), mechanic.getAddress().getZipCode());
            assertEquals(mechanicDto.getHoursWorked(), mechanic.getHoursWorked());
            assertEquals(mechanicDto.getEmployeeType(), mechanic.getEmployeeType());
            assertEquals(mechanicDto.getManagerId(), mechanic.getManager().getId());
            assertEquals(mechanicDto.getDepartmentId(), mechanic.getDepartment().getId());
        });
    }

    @Test
    void shouldMapFromMechanicDto() {
        MechanicDto mechanicDto = MechanicDto.builder()
                .id(1L)
                .firstName("FirstName")
                .middleInitial('M')
                .lastName("LastName")
                .gender(Gender.MALE)
                .dateOfBirth(LocalDate.now())
                .phoneNumber("123-456-7890")
                .email("mechanic@company.com")
                .workLocationId(1L)
                .salary(45000.00)
                .dateStarted(LocalDate.now())
                .address(AddressDto.builder()
                        .id(1L)
                        .street("123 Main St.")
                        .city("New York City")
                        .state("New York")
                        .zipCode(12345)
                        .build())
                .hoursWorked(780.00)
                .employeeType(EmployeeType.MECHANIC)
                .managerId(2L)
                .departmentId(1L)
                .build();

        Mechanic mechanic = employeeMapper.toMechanic(mechanicDto);

        assertAll(() -> {
            assertThat(mechanic).isInstanceOf(Mechanic.class);
            assertEquals(mechanic.getId(), mechanicDto.getId());
            assertEquals(mechanic.getFirstName(), mechanicDto.getFirstName());
            assertEquals(mechanic.getMiddleInitial(), mechanicDto.getMiddleInitial());
            assertEquals(mechanic.getLastName(), mechanicDto.getLastName());
            assertEquals(mechanic.getGender(), mechanicDto.getGender());
            assertEquals(mechanic.getDateOfBirth(), mechanicDto.getDateOfBirth());
            assertEquals(mechanic.getPhoneNumber(), mechanicDto.getPhoneNumber());
            assertEquals(mechanic.getEmail(), mechanicDto.getEmail());
            assertEquals(mechanic.getSalary(), mechanicDto.getSalary());
            assertEquals(mechanic.getDateStarted(), mechanicDto.getDateStarted());
            assertEquals(mechanic.getAddress().getId(), mechanicDto.getAddress().getId());
            assertEquals(mechanic.getAddress().getStreet(), mechanicDto.getAddress().getStreet());
            assertEquals(mechanic.getAddress().getCity(), mechanicDto.getAddress().getCity());
            assertEquals(mechanic.getAddress().getState(), mechanicDto.getAddress().getState());
            assertEquals(mechanic.getAddress().getZipCode(), mechanicDto.getAddress().getZipCode());
            assertEquals(mechanic.getHoursWorked(), mechanicDto.getHoursWorked());
            assertEquals(mechanic.getEmployeeType(), mechanicDto.getEmployeeType());
        });
    }

    @Test
    void shouldReturnNullMechanicEntity() {
        Mechanic mechanic = employeeMapper.toMechanic(null);

        assertThat(mechanic).isEqualTo(null);
    }

    @Test
    void shouldReturnNullMechanicDto() {
        MechanicDto mechanicDto = employeeMapper.toMechanicDto(null);

        assertThat(mechanicDto).isEqualTo(null);
    }

    @Test
    void shouldMapToSalesAssociateDto() {
        SalesAssociate salesAssociate = SalesAssociate.builder()
                .id(1L)
                .ssn("123-45-6789")
                .firstName("FirstName")
                .middleInitial('M')
                .lastName("LastName")
                .gender(Gender.FEMALE)
                .dateOfBirth(LocalDate.now())
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
                .build();

        SalesAssociateDto salesAssociateDto = employeeMapper.toSalesAssociateDto(salesAssociate);

        assertAll(() -> {
            assertThat(salesAssociateDto).isInstanceOf(SalesAssociateDto.class);
            assertEquals(salesAssociateDto.getId(), salesAssociate.getId());
            assertEquals(salesAssociateDto.getFirstName(), salesAssociate.getFirstName());
            assertEquals(salesAssociateDto.getMiddleInitial(), salesAssociate.getMiddleInitial());
            assertEquals(salesAssociateDto.getLastName(), salesAssociate.getLastName());
            assertEquals(salesAssociateDto.getGender(), salesAssociate.getGender());
            assertEquals(salesAssociateDto.getDateOfBirth(), salesAssociate.getDateOfBirth());
            assertEquals(salesAssociateDto.getPhoneNumber(), salesAssociate.getPhoneNumber());
            assertEquals(salesAssociateDto.getEmail(), salesAssociate.getEmail());
            assertEquals(salesAssociateDto.getWorkLocationId(), salesAssociate.getWorkLocation().getId());
            assertEquals(salesAssociateDto.getSalary(), salesAssociate.getSalary());
            assertEquals(salesAssociateDto.getDateStarted(), salesAssociate.getDateStarted());
            assertEquals(salesAssociateDto.getAddress().getId(), salesAssociate.getAddress().getId());
            assertEquals(salesAssociateDto.getAddress().getStreet(), salesAssociate.getAddress().getStreet());
            assertEquals(salesAssociateDto.getAddress().getCity(), salesAssociate.getAddress().getCity());
            assertEquals(salesAssociateDto.getAddress().getState(), salesAssociate.getAddress().getState());
            assertEquals(salesAssociateDto.getAddress().getZipCode(), salesAssociate.getAddress().getZipCode());
            assertEquals(salesAssociateDto.getHoursWorked(), salesAssociate.getHoursWorked());
            assertEquals(salesAssociateDto.getEmployeeType(), salesAssociate.getEmployeeType());
            assertEquals(salesAssociateDto.getManagerId(), salesAssociate.getManager().getId());
            assertEquals(salesAssociateDto.getDepartmentId(), salesAssociate.getDepartment().getId());
        });
    }

    @Test
    void shouldMapFromSalesAssociateDto() {
        SalesAssociateDto salesAssociateDto = SalesAssociateDto.builder()
                .id(1L)
                .firstName("FirstName")
                .middleInitial('M')
                .lastName("LastName")
                .gender(Gender.FEMALE)
                .dateOfBirth(LocalDate.now())
                .phoneNumber("123-456-7890")
                .email("sales@company.com")
                .workLocationId(1L)
                .salary(45000.00)
                .dateStarted(LocalDate.now())
                .address(AddressDto.builder()
                        .id(1L)
                        .street("123 Main St.")
                        .city("New York City")
                        .state("New York")
                        .zipCode(12345)
                        .build())
                .hoursWorked(780.00)
                .employeeType(EmployeeType.SALES_ASSOCIATE)
                .managerId(2L)
                .departmentId(1L)
                .build();

        SalesAssociate salesAssociate = employeeMapper.toSalesAssociate(salesAssociateDto);

        assertAll(() -> {
            assertThat(salesAssociate).isInstanceOf(SalesAssociate.class);
            assertEquals(salesAssociate.getId(), salesAssociateDto.getId());
            assertEquals(salesAssociate.getFirstName(), salesAssociateDto.getFirstName());
            assertEquals(salesAssociate.getMiddleInitial(), salesAssociateDto.getMiddleInitial());
            assertEquals(salesAssociate.getLastName(), salesAssociateDto.getLastName());
            assertEquals(salesAssociate.getGender(), salesAssociateDto.getGender());
            assertEquals(salesAssociate.getDateOfBirth(), salesAssociateDto.getDateOfBirth());
            assertEquals(salesAssociate.getPhoneNumber(), salesAssociateDto.getPhoneNumber());
            assertEquals(salesAssociate.getEmail(), salesAssociateDto.getEmail());
            assertEquals(salesAssociate.getSalary(), salesAssociateDto.getSalary());
            assertEquals(salesAssociate.getDateStarted(), salesAssociateDto.getDateStarted());
            assertEquals(salesAssociate.getAddress().getId(), salesAssociateDto.getAddress().getId());
            assertEquals(salesAssociate.getAddress().getStreet(), salesAssociateDto.getAddress().getStreet());
            assertEquals(salesAssociate.getAddress().getCity(), salesAssociateDto.getAddress().getCity());
            assertEquals(salesAssociate.getAddress().getState(), salesAssociateDto.getAddress().getState());
            assertEquals(salesAssociate.getAddress().getZipCode(), salesAssociateDto.getAddress().getZipCode());
            assertEquals(salesAssociate.getHoursWorked(), salesAssociateDto.getHoursWorked());
            assertEquals(salesAssociate.getEmployeeType(), salesAssociateDto.getEmployeeType());
        });
    }

    @Test
    void shouldReturnNullSalesAssociateEntity() {
        SalesAssociate salesAssociate = employeeMapper.toSalesAssociate(null);

        assertThat(salesAssociate).isEqualTo(null);
    }

    @Test
    void shouldReturnNullSalesAssociateDto() {
        SalesAssociateDto salesAssociateDto = employeeMapper.toSalesAssociateDto(null);

        assertThat(salesAssociateDto).isEqualTo(null);
    }

    @Test
    void shouldMapToSiteManagerDto() {
        SiteManager siteManager = SiteManager.builder()
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
                .build();

        SiteManagerDto siteManagerDto = employeeMapper.toSiteManagerDto(siteManager);

        assertAll(() -> {
            assertThat(siteManagerDto).isInstanceOf(SiteManagerDto.class);
            assertEquals(siteManagerDto.getId(), siteManager.getId());
            assertEquals(siteManagerDto.getFirstName(), siteManager.getFirstName());
            assertEquals(siteManagerDto.getMiddleInitial(), siteManager.getMiddleInitial());
            assertEquals(siteManagerDto.getLastName(), siteManager.getLastName());
            assertEquals(siteManagerDto.getGender(), siteManager.getGender());
            assertEquals(siteManagerDto.getDateOfBirth(), siteManager.getDateOfBirth());
            assertEquals(siteManagerDto.getPhoneNumber(), siteManager.getPhoneNumber());
            assertEquals(siteManagerDto.getEmail(), siteManager.getEmail());
            assertEquals(siteManagerDto.getWorkLocationId(), siteManager.getWorkLocation().getId());
            assertEquals(siteManagerDto.getSalary(), siteManager.getSalary());
            assertEquals(siteManagerDto.getDateStarted(), siteManager.getDateStarted());
            assertEquals(siteManagerDto.getAddress().getId(), siteManager.getAddress().getId());
            assertEquals(siteManagerDto.getAddress().getStreet(), siteManager.getAddress().getStreet());
            assertEquals(siteManagerDto.getAddress().getCity(), siteManager.getAddress().getCity());
            assertEquals(siteManagerDto.getAddress().getState(), siteManager.getAddress().getState());
            assertEquals(siteManagerDto.getAddress().getZipCode(), siteManager.getAddress().getZipCode());
            assertEquals(siteManagerDto.getHoursWorked(), siteManager.getHoursWorked());
            assertEquals(siteManagerDto.getEmployeeType(), siteManager.getEmployeeType());
            assertEquals(siteManagerDto.getManagedLocationId(), siteManager.getManagedLocation().getId());
        });
    }

    @Test
    void shouldMapFromSiteManagerDto() {
        SiteManagerDto siteManagerDto = SiteManagerDto.builder()
                .id(1L)
                .firstName("FirstName")
                .middleInitial('M')
                .lastName("LastName")
                .gender(Gender.FEMALE)
                .dateOfBirth(LocalDate.now())
                .phoneNumber("123-456-7890")
                .email("sales@company.com")
                .workLocationId(1L)
                .salary(45000.00)
                .dateStarted(LocalDate.now())
                .address(AddressDto.builder()
                        .id(1L)
                        .street("123 Main St.")
                        .city("New York City")
                        .state("New York")
                        .zipCode(12345)
                        .build())
                .hoursWorked(780.00)
                .employeeType(EmployeeType.SITE_MANAGER)
                .managedLocationId(1L)
                .build();

        SiteManager siteManager = employeeMapper.toSiteManager(siteManagerDto);

        assertAll(() -> {
            assertThat(siteManager).isInstanceOf(SiteManager.class);
            assertEquals(siteManager.getId(), siteManagerDto.getId());
            assertEquals(siteManager.getFirstName(), siteManagerDto.getFirstName());
            assertEquals(siteManager.getMiddleInitial(), siteManagerDto.getMiddleInitial());
            assertEquals(siteManager.getLastName(), siteManagerDto.getLastName());
            assertEquals(siteManager.getGender(), siteManagerDto.getGender());
            assertEquals(siteManager.getDateOfBirth(), siteManagerDto.getDateOfBirth());
            assertEquals(siteManager.getPhoneNumber(), siteManagerDto.getPhoneNumber());
            assertEquals(siteManager.getEmail(), siteManagerDto.getEmail());
            assertEquals(siteManager.getSalary(), siteManagerDto.getSalary());
            assertEquals(siteManager.getDateStarted(), siteManagerDto.getDateStarted());
            assertEquals(siteManager.getAddress().getId(), siteManagerDto.getAddress().getId());
            assertEquals(siteManager.getAddress().getStreet(), siteManagerDto.getAddress().getStreet());
            assertEquals(siteManager.getAddress().getCity(), siteManagerDto.getAddress().getCity());
            assertEquals(siteManager.getAddress().getState(), siteManagerDto.getAddress().getState());
            assertEquals(siteManager.getAddress().getZipCode(), siteManagerDto.getAddress().getZipCode());
            assertEquals(siteManager.getHoursWorked(), siteManagerDto.getHoursWorked());
            assertEquals(siteManager.getEmployeeType(), siteManagerDto.getEmployeeType());
        });
    }

    @Test
    void shouldReturnNullSiteManagerEntity() {
        SiteManager siteManager = employeeMapper.toSiteManager(null);

        assertThat(siteManager).isEqualTo(null);
    }

    @Test
    void shouldReturnNullSiteManagerDto() {
        SiteManagerDto siteManagerDto = employeeMapper.toSiteManagerDto(null);

        assertThat(siteManagerDto).isEqualTo(null);
    }
}