package edu.towson.cosc457.CarDealership.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.istack.NotNull;
import edu.towson.cosc457.CarDealership.misc.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "service_ticket", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServiceTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id", updatable = false)
    private Long id;
    @JsonBackReference
    @NotNull
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH})
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;
    @JsonBackReference
    @NotNull
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH})
    @JoinColumn(name = "mechanic_id")
    private Mechanic mechanic;
    @NotNull
    @Column(name = "date_created", updatable = false)
    private LocalDate dateCreated;
    @NotNull
    @Column(name = "date_updated")
    private LocalDate dateUpdated;
    @NotNull
    @Enumerated(value = EnumType.STRING)
    @Column(name = "ticket_status")
    private Status status;
    @JsonManagedReference
    @OneToMany(mappedBy = "serviceTicket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
    }
}
