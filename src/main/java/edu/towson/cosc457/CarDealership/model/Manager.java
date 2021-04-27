package edu.towson.cosc457.CarDealership.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "manager", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@DiscriminatorValue("MANAGER")
@EqualsAndHashCode(callSuper = true)
public class Manager extends Employee {
    @JsonBackReference
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH})
    @JoinColumn(name = "site_manager_id")
    private SiteManager siteManager;
    @JsonBackReference
    @OneToOne(mappedBy = "manager", cascade = {CascadeType.PERSIST, CascadeType.DETACH})
    private Department department;
    @JsonManagedReference
    @OneToMany(mappedBy = "manager")
    private List<Mechanic> mechanics;
    @JsonManagedReference
    @OneToMany(mappedBy = "manager")
    private List<SalesAssociate> salesAssociates;

    public void assignMechanics(Mechanic mechanic) {
        mechanics.add(mechanic);
    }

    public void removeMechanics(Mechanic mechanic) {
        mechanics.remove(mechanic);
    }

    public void assignAssociates(SalesAssociate associate) {
        salesAssociates.add(associate);
    }

    public void removeAssociates(SalesAssociate associate) {
        salesAssociates.remove(associate);
    }
}
