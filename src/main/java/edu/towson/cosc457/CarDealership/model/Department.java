package edu.towson.cosc457.CarDealership.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "department", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dept_id", updatable = false)
    private Long id;
    @NotNull
    @Column(name = "dept_name", unique = true)
    private String name;
    @JsonBackReference
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH})
    @JoinColumn(name = "manager_id")
    private Manager manager;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;
    @JsonManagedReference
    @OneToMany(mappedBy = "department")
    private List<Mechanic> mechanics;
    @JsonManagedReference
    @OneToMany(mappedBy = "department")
    private List<SalesAssociate> salesAssociates;

    public void assignMechanic(Mechanic mechanic) {
        mechanics.add(mechanic);
    }

    public void removeMechanic(Mechanic mechanic) {
        mechanics.remove(mechanic);
    }

    public void addAssociates(SalesAssociate associate) {
        salesAssociates.add(associate);
    }

    public void removeAssociates(SalesAssociate associate) {
        salesAssociates.remove(associate);
    }
}
