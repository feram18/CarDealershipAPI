package edu.towson.cosc457.CarDealership.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "location", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id", updatable = false)
    private Long id;
    @NotNull
    @Column(name = "location_name", unique = true, length = 45)
    private String name;
    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;
    @JsonBackReference
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH})
    @JoinColumn(name = "site_manager_id")
    private SiteManager siteManager;
    @JsonManagedReference
    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Lot> lots = new ArrayList<>();
    @JsonManagedReference
    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Department> departments;
    @JsonManagedReference
    @OneToMany(mappedBy = "workLocation")
    private List<Mechanic> mechanics;
    @JsonManagedReference
    @OneToMany(mappedBy = "workLocation")
    private List<SalesAssociate> salesAssociates;

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
}
