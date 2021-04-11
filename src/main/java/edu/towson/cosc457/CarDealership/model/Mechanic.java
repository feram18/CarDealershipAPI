package edu.towson.cosc457.CarDealership.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import edu.towson.cosc457.CarDealership.misc.Gender;
import edu.towson.cosc457.CarDealership.misc.Role;
import edu.towson.cosc457.CarDealership.model.dto.MechanicDto;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "MECHANIC")
@DiscriminatorValue("Mechanic")
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

    public Mechanic() { }

    public Mechanic(String ssn,
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
                    Manager manager,
                    Department department,
                    List<ServiceTicket> tickets,
                    List<Comment> comments) {
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
        this.manager = manager;
        this.department = department;
        this.tickets = tickets;
        this.comments = comments;
    }

    public void addServiceTicket(ServiceTicket serviceTicket) {
        tickets.add(serviceTicket);
    }

    public void removeServiceTicket(ServiceTicket serviceTicket) {
        tickets.remove(serviceTicket);
    }

    public static Mechanic from (MechanicDto mechanicDto) {
        Mechanic mechanic = new Mechanic();
        mechanic.setManager(mechanicDto.getManager());
        mechanic.setDepartment(mechanicDto.getDepartment());
        mechanic.setTickets(mechanicDto.getTicketsDto()
                .stream().map(ServiceTicket::from).collect(Collectors.toList()));
        mechanic.setComments(mechanicDto.getCommentsDto()
                .stream().map(Comment::from).collect(Collectors.toList()));
        return mechanic;
    }
}
