package edu.towson.cosc457.CarDealership.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "mechanic", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@DiscriminatorValue("MECHANIC")
@EqualsAndHashCode(callSuper = true)
public class Mechanic extends Employee {
    @JsonBackReference
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH})
    @JoinColumn(name = "manager_id")
    private Manager manager;
    @JsonBackReference
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH})
    @JoinColumn(name = "dept_id")
    private Department department;
    @JsonManagedReference
    @OneToMany(mappedBy = "mechanic")
    private List<ServiceTicket> tickets;
    @JsonManagedReference
    @OneToMany(mappedBy = "mechanic")
    private List<Comment> comments;

    public void addServiceTicket(ServiceTicket serviceTicket) {
        tickets.add(serviceTicket);
    }

    public void removeServiceTicket(ServiceTicket serviceTicket) {
        tickets.remove(serviceTicket);
    }
}
