package edu.towson.cosc457.CarDealership.model;

import edu.towson.cosc457.CarDealership.misc.Status;
import edu.towson.cosc457.CarDealership.model.dto.ServiceTicketDto;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "SERVICE_TICKET")
public class ServiceTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id",
            updatable = false)
    private Long id;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH})
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH})
    @JoinColumn(name = "mechanic_id")
    private Mechanic mechanic;
    @Column(name = "date_created",
            nullable = false)
    private LocalDate dateCreated;
    @Column(name = "date_updated")
    private LocalDate dateUpdated;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private Status status;
    @OneToMany(mappedBy = "serviceTicket")
    private List<Comment> comments;

    public ServiceTicket() { }

    public ServiceTicket(Vehicle vehicle,
                         Mechanic mechanic,
                         LocalDate dateCreated,
                         LocalDate dateUpdated,
                         Status status,
                         List<Comment> comments) {
        this.vehicle = vehicle;
        this.mechanic = mechanic;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
        this.status = status;
        this.comments = comments;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
    }

    public static ServiceTicket from (ServiceTicketDto serviceTicketDto) {
        ServiceTicket serviceTicket = new ServiceTicket();
        serviceTicket.setId(serviceTicketDto.getId());
        serviceTicket.setVehicle(serviceTicketDto.getVehicle());
        serviceTicket.setMechanic(serviceTicketDto.getMechanic());
        serviceTicket.setDateCreated(serviceTicketDto.getDateCreated());
        serviceTicket.setDateUpdated(serviceTicketDto.getDateUpdated());
        serviceTicket.setStatus(serviceTicketDto.getStatus());
        serviceTicket.setComments(serviceTicketDto.getComments()
                .stream().map(Comment::from).collect(Collectors.toList()));
        return serviceTicket;
    }
}
