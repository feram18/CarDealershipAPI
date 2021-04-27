package edu.towson.cosc457.CarDealership.mapper;

import edu.towson.cosc457.CarDealership.misc.EmployeeType;
import edu.towson.cosc457.CarDealership.misc.Gender;
import edu.towson.cosc457.CarDealership.model.*;
import edu.towson.cosc457.CarDealership.model.dto.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {Gender.class, AddressMapper.class, EmployeeType.class})
public interface EmployeeMapper {
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

    @Mapping(source = "workLocation.id", target = "workLocationId")
    @Mapping(source = "department.id", target = "departmentId")
    @Mapping(source = "siteManager.id", target = "siteManagerId")
    ManagerDto toManagerDto(Manager manager);

    @InheritInverseConfiguration
    @Mapping(target = "department", ignore = true)
    @Mapping(target = "siteManager", ignore = true)
    Manager toManager(ManagerDto managerDto);

    @Mapping(source = "workLocation.id", target = "workLocationId")
    @Mapping(source = "manager.id", target = "managerId")
    @Mapping(source = "department.id", target = "departmentId")
    MechanicDto toMechanicDto(Mechanic mechanic);

    @InheritInverseConfiguration
    @Mapping(target = "manager", ignore = true)
    @Mapping(target = "department", ignore = true)
    Mechanic toMechanic(MechanicDto mechanicDto);

    @Mapping(source = "workLocation.id", target = "workLocationId")
    @Mapping(source = "manager.id", target = "managerId")
    @Mapping(source = "department.id", target = "departmentId")
    SalesAssociateDto toSalesAssociateDto(SalesAssociate salesAssociate);

    @InheritInverseConfiguration
    @Mapping(target = "manager", ignore = true)
    @Mapping(target = "department", ignore = true)
    SalesAssociate toSalesAssociate(SalesAssociateDto salesAssociateDto);

    @Mapping(source = "workLocation.id", target = "workLocationId")
    @Mapping(source = "managedLocation.id", target = "managedLocationId")
    SiteManagerDto toSiteManagerDto(SiteManager siteManager);

    @InheritInverseConfiguration
    @Mapping(target = "managedLocation", ignore = true)
    SiteManager toSiteManager(SiteManagerDto siteManagerDto);
}
