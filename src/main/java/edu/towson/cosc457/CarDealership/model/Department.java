package edu.towson.cosc457.CarDealership.model;

import com.sun.istack.NotNull;
import edu.towson.cosc457.CarDealership.model.dto.DepartmentDto;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "DEPARTMENT")
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
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH})
    @JoinColumn(name = "manager_id")
    private Manager manager;
    @NotNull
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH})
    @JoinColumn(name = "location_id")
    private Location location;
    @OneToMany(mappedBy = "department")
    private List<Mechanic> mechanics;
    @OneToMany(mappedBy = "department")
    private List<SalesAssociate> salesAssociates;

    public Department() { }

    public Department(String name,
                      Manager manager,
                      Location location,
                      List<Mechanic> mechanics,
                      List<SalesAssociate> salesAssociates) {
        this.name = name;
        this.manager = manager;
        this.location = location;
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
