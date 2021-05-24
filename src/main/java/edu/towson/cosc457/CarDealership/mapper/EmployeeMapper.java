package edu.towson.cosc457.CarDealership.mapper;

import edu.towson.cosc457.CarDealership.misc.EmployeeType;
import edu.towson.cosc457.CarDealership.misc.Gender;
import edu.towson.cosc457.CarDealership.model.*;
import edu.towson.cosc457.CarDealership.model.dto.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {Gender.class, AddressMapper.class, EmployeeType.class})
public interface EmployeeMapper {
    /**
     * Default method to route @param employee to appropriate Employee subtype mapper
     * @param employee (of type of Employee) object to be mapped to DTO (of type EmployeeDto)
     * @return mapped DTO object of type EmployeeDTO
     * @throws IllegalArgumentException if passed parameter is not of type Employee
     */
    default EmployeeDto toDto(Employee employee) {
        if (employee instanceof Manager) {
            return toManagerDto((Manager) employee);
        }

        if (employee instanceof Mechanic) {
            return toMechanicDto((Mechanic) employee);
        }

        if (employee instanceof SalesAssociate) {
            return toSalesAssociateDto((SalesAssociate) employee);
        }

        if (employee instanceof SiteManager) {
            return toSiteManagerDto((SiteManager) employee);
        }

        throw new IllegalArgumentException("Unknown subtype of Employee");
    }

    /**
     * Default method to route @param employeeDto to appropriate EmployeeDto subtype mapper
     * @param employeeDto (of type EmployeeDto) object to be mapped to entity (of type Employee)
     * @return mapped entity object of type Employee
     * @throws IllegalArgumentException if passed parameter is not of type EmployeeDto
     */
    default Employee fromDto(EmployeeDto employeeDto){
        if (employeeDto instanceof ManagerDto) {
            return toManager((ManagerDto) employeeDto);
        }

        if (employeeDto instanceof MechanicDto) {
            return toMechanic((MechanicDto) employeeDto);
        }

        if (employeeDto instanceof SalesAssociateDto) {
            return toSalesAssociate((SalesAssociateDto) employeeDto);
        }

        if (employeeDto instanceof SiteManagerDto) {
            return toSiteManager((SiteManagerDto) employeeDto);
        }

        throw new IllegalArgumentException("Unknown subtype of EmployeeDto");
    }

    /**
     * Map from Manager entity to ManagerDTO
     * @param manager Manager object to be mapped to ManagerDTO
     * @return mapped ManagerDto object
     */
    @Mapping(source = "workLocation.id", target = "workLocationId")
    @Mapping(source = "department.id", target = "departmentId")
    @Mapping(source = "siteManager.id", target = "siteManagerId")
    ManagerDto toManagerDto(Manager manager);

    /**
     * Map from ManagerDTO to Manager entity
     * @param managerDto Map from ManagerDTO to Manager entity
     * @return mapped Manager object
     */
    @InheritInverseConfiguration
    @Mapping(target = "department", ignore = true)
    @Mapping(target = "siteManager", ignore = true)
    Manager toManager(ManagerDto managerDto);

    /**
     * Map from Mechanic entity to MechanicDTO
     * @param mechanic Mechanic object to be mapped to MechanicDTO
     * @return mapped MechanicDto object
     */
    @Mapping(source = "workLocation.id", target = "workLocationId")
    @Mapping(source = "manager.id", target = "managerId")
    @Mapping(source = "department.id", target = "departmentId")
    MechanicDto toMechanicDto(Mechanic mechanic);

    /**
     * Map from MechanicDTO to Mechanic entity
     * @param mechanicDto Map from MechanicDTO to Mechanic entity
     * @return mapped Mechanic object
     */
    @InheritInverseConfiguration
    @Mapping(target = "manager", ignore = true)
    @Mapping(target = "department", ignore = true)
    Mechanic toMechanic(MechanicDto mechanicDto);

    /**
     * Map from SalesAssociate entity to SalesAssociateDTO
     * @param salesAssociate SalesAssociate object to be mapped to SalesAssociateDTO
     * @return mapped SalesAssociateDto object
     */
    @Mapping(source = "workLocation.id", target = "workLocationId")
    @Mapping(source = "manager.id", target = "managerId")
    @Mapping(source = "department.id", target = "departmentId")
    SalesAssociateDto toSalesAssociateDto(SalesAssociate salesAssociate);

    /**
     * Map from SalesAssociateDTO to SalesAssociate entity
     * @param salesAssociateDto Map from SalesAssociateDTO to SalesAssociate entity
     * @return mapped SalesAssociate object
     */
    @InheritInverseConfiguration
    @Mapping(target = "manager", ignore = true)
    @Mapping(target = "department", ignore = true)
    SalesAssociate toSalesAssociate(SalesAssociateDto salesAssociateDto);

    /**
     * Map from SiteManager entity to SiteManagerDTO
     * @param siteManager SiteManager object to be mapped to SiteManagerDTO
     * @return mapped SiteManagerDto object
     */
    @Mapping(source = "workLocation.id", target = "workLocationId")
    @Mapping(source = "managedLocation.id", target = "managedLocationId")
    SiteManagerDto toSiteManagerDto(SiteManager siteManager);

    /**
     * Map from SiteManagerDTO to SiteManager entity
     * @param siteManagerDto Map from SiteManagerDTO to SiteManager entity
     * @return mapped SiteManager object
     */
    @InheritInverseConfiguration
    @Mapping(target = "managedLocation", ignore = true)
    SiteManager toSiteManager(SiteManagerDto siteManagerDto);
}
