package edu.towson.cosc457.CarDealership.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import edu.towson.cosc457.CarDealership.misc.Gender;
import edu.towson.cosc457.CarDealership.misc.Role;
import edu.towson.cosc457.CarDealership.model.dto.SiteManagerDto;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "SITE_MANAGER")
@DiscriminatorValue("Site Manager")
public class SiteManager extends Employee {
    @JsonBackReference
    @OneToOne(mappedBy = "siteManager",
                cascade = {CascadeType.PERSIST, CascadeType.DETACH})
    private Location location;
    @JsonManagedReference
    @OneToMany(mappedBy = "siteManager",
                cascade = {CascadeType.PERSIST, CascadeType.DETACH})
    private List<Manager> managers;

    public SiteManager() { }

    public SiteManager(String ssn,
                       String firstName,
                       Character middleInitial,
                       String lastName,
                       Gender gender,
                       LocalDate dateOfBirth,
                       String phoneNumber,
                       String email,
                       Location workLocation,
                       Double salary,
                       LocalDate dateStarted,
                       Address address,
                       Double hoursWorked,
                       Boolean isActive,
                       Role role,
                       String username,
                       String password,
                       Location location,
                       List<Manager> managers) {
        super(ssn,
                firstName,
                middleInitial,
                lastName,
                gender,
                dateOfBirth,
                phoneNumber,
                email,
                workLocation,
                salary,
                dateStarted,
                address,
                hoursWorked,
                isActive,
                role,
                username,
                password);
        this.location = location;
        this.managers = managers;
    }

    public void assignManager(Manager manager) {
        managers.add(manager);
    }

    public void removeManager(Manager manager) {
        managers.remove(manager);
    }

    public static SiteManager from (SiteManagerDto siteManagerDto) {
        SiteManager siteManager = new SiteManager();
        siteManager.setLocation(siteManagerDto.getLocation());
        siteManager.setManagers(siteManagerDto.getManagers()
                .stream().map(Manager::from).collect(Collectors.toList()));
        return siteManager;
    }
}