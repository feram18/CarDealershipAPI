package edu.towson.cosc457.CarDealership.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.istack.NotNull;
import edu.towson.cosc457.CarDealership.model.dto.DepartmentDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "DEPARTMENT")
@AllArgsConstructor
@NoArgsConstructor
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dept_id",
            updatable = false)
    private Long id;
    @NotNull
    @Column(name = "dept_name",
            unique = true)
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

    public Department(String name,
                      Manager manager,
                      List<Mechanic> mechanics,
                      List<SalesAssociate> salesAssociates) {
        this.name = name;
        this.manager = manager;
        this.mechanics = mechanics;
        this.salesAssociates = salesAssociates;
    }

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

    public static Department from (DepartmentDto departmentDto) {
        Department department = new Department();
        department.setId(departmentDto.getId());
        department.setName(departmentDto.getName());
        department.setManager(departmentDto.getManager());
        department.setLocation(departmentDto.getLocation());
        department.setMechanics(departmentDto.getMechanicsDto()
                .stream().map(Mechanic::from).collect(Collectors.toList()));
        department.setSalesAssociates(departmentDto.getSalesAssociatesDto()
                .stream().map(SalesAssociate::from).collect(Collectors.toList()));
        return department;
    }
}
