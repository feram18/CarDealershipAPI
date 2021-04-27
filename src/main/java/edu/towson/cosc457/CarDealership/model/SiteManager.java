package edu.towson.cosc457.CarDealership.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "site_manager", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@DiscriminatorValue("SITE_MANAGER")
@EqualsAndHashCode(callSuper = true)
public class SiteManager extends Employee {
    @JsonBackReference
    @OneToOne(mappedBy = "siteManager", cascade = {CascadeType.PERSIST, CascadeType.DETACH})
    private Location managedLocation;
    @JsonManagedReference
    @OneToMany(mappedBy = "siteManager", cascade = {CascadeType.PERSIST, CascadeType.DETACH})
    private List<Manager> managers;

    public void assignManager(Manager manager) {
        managers.add(manager);
    }

    public void removeManager(Manager manager) {
        managers.remove(manager);
    }
}
