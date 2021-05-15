package edu.towson.cosc457.CarDealership.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.istack.NotNull;
import edu.towson.cosc457.CarDealership.misc.TransmissionType;
import edu.towson.cosc457.CarDealership.misc.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "vehicle", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vehicle_id", updatable = false)
    private Long id;
    @NotNull
    @Column(name = "vin", length = 17)
    private String vin;
    @NotNull
    @Column(name = "make", length = 45)
    private String make;
    @NotNull
    @Column(name = "model", length = 45)
    private String model;
    @NotNull
    @Column(name = "year", length = 4)
    private Integer year;
    @NotNull
    @Column(name = "color", length = 45)
    private String color;
    @NotNull
    @Column(name = "vehicle_type")
    @Enumerated(value = EnumType.STRING)
    private VehicleType type;
    @NotNull
    @Column(name = "transmission")
    @Enumerated(value = EnumType.STRING)
    private TransmissionType transmission;
    @Column(name = "features")
    private String features;
    @NotNull
    @Column(name = "mpg")
    private Integer mpg;
    @NotNull
    @Column(name = "mileage")
    private Integer mileage;
    @Column(name = "price")
    private Double price;
    @JsonBackReference
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH})
    @JoinColumn(name = "lot_id")
    private Lot lot;
    @JsonManagedReference
    @OneToMany(mappedBy = "vehicle")
    private List<ServiceTicket> tickets;

    public Location getLocation() {
        return getLot().getLocation();
    }

    public void assignTicket(ServiceTicket ticket) {
        tickets.add(ticket);
    }

    public void removeTicket(ServiceTicket ticket) {
        tickets.remove(ticket);
    }
}
