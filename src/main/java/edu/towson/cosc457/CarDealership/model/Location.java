package edu.towson.cosc457.CarDealership.model;

import com.sun.istack.NotNull;
import edu.towson.cosc457.CarDealership.model.dto.LocationDto;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "LOCATION")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id",
            updatable = false)
    private Long id;
    @NotNull
    @Column(name = "location_name",
            unique = true)
    private String name;
    @Column(name = "address")
    private String address;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH})
    @JoinColumn(name = "site_manager_id")
    private SiteManager siteManager;
    @OneToMany(mappedBy = "location")
    private List<Lot> lots = new ArrayList<>();
    @OneToMany(mappedBy = "location")
    private List<Department> departments = new ArrayList<>();
    @OneToMany(mappedBy = "workLocation")
    private List<Mechanic> mechanics;
    @OneToMany(mappedBy = "workLocation")
    private List<SalesAssociate> salesAssociates;

    public Location() { }

    public Location(String name,
                    String address,
                    SiteManager siteManager,
                    List<Lot> lots,
                    List<Department> departments,
                    List<Mechanic> mechanics,
                    List<SalesAssociate> salesAssociates) {
        this.name = name;
        this.address = address;
        this.siteManager = siteManager;
        this.lots = lots;
        this.departments = departments;
        this.mechanics = mechanics;
        this.salesAssociates = salesAssociates;
    }

    public void addLot(Lot lot) {
        lots.add(lot);
    }

    public void removeLot(Lot lot) {
        lots.remove(lot);
    }

    public void addDepartment(Department department) {
        departments.add(department);
    }

    public void removeDepartment(Department department) {
        departments.remove(department);
    }

    public void assignMechanic(Mechanic mechanic) {
        mechanics.add(mechanic);
    }

    public void removeMechanic(Mechanic mechanic) {
        mechanics.remove(mechanic);
    }

    public void assignAssociate(SalesAssociate associate) {
        salesAssociates.add(associate);
    }

    public void removeAssociate(SalesAssociate associate) {
        salesAssociates.remove(associate);
    }

    public static Location from (LocationDto locationDto) {
        Location location = new Location();
        location.setId(locationDto.getId());
        location.setName(locationDto.getName());
        location.setAddress(locationDto.getAddress());
        location.setSiteManager(locationDto.getSiteManager());
        location.setLots(locationDto.getLotsDto()
                .stream().map(Lot::from).collect(Collectors.toList()));
        location.setDepartments(locationDto.getDepartmentsDto()
                .stream().map(Department::from).collect(Collectors.toList()));
        location.setMechanics(locationDto.getMechanicsDto()
                .stream().map(Mechanic::from).collect(Collectors.toList()));
        location.setSalesAssociates(locationDto.getSalesAssociatesDto()
                .stream().map(SalesAssociate::from).collect(Collectors.toList()));
        return location;
    }
}
