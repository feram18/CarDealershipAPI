package edu.towson.cosc457.CarDealership.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.istack.NotNull;
import edu.towson.cosc457.CarDealership.misc.TransmissionType;
import edu.towson.cosc457.CarDealership.misc.VehicleType;
import edu.towson.cosc457.CarDealership.model.dto.VehicleDto;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "VEHICLE")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vehicle_id",
            updatable = false)
    private Long id;
    @NotNull
    @Column(name = "vin",
            length = 17)
    private String vin;
    @NotNull
    @Column(name = "make")
    private String make;
    @NotNull
    @Column(name = "model")
    private String model;
    @NotNull
    @Column(name = "year",
            length = 4)
    private Integer year;
    @NotNull
    @Column(name = "color")
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

    public Vehicle() { }

    public Vehicle(String vin,
                   String make,
                   String model,
                   Integer year,
                   String color,
                   VehicleType type,
                   TransmissionType transmission,
                   String features,
                   Integer mpg,
                   Integer mileage,
                   Double price,
                   Lot lot,
                   List<ServiceTicket> tickets) {
        this.vin = vin;
        this.make = make;
        this.model = model;
        this.year = year;
        this.color = color;
        this.type = type;
        this.transmission = transmission;
        this.features = features;
        this.mpg = mpg;
        this.mileage = mileage;
        this.price = price;
        this.lot = lot;
        this.tickets = tickets;
    }

    public Location getLocation(Vehicle vehicle) {
        return vehicle.getLot().getLocation();
    }

    public void assignTicket(ServiceTicket ticket) {
        tickets.add(ticket);
    }

    public void removeTicket(ServiceTicket ticket) {
        tickets.remove(ticket);
    }

    public static Vehicle from (VehicleDto vehicleDto) {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(vehicleDto.getId());
        vehicle.setVin(vehicleDto.getVin());
        vehicle.setMake(vehicleDto.getMake());
        vehicle.setModel(vehicleDto.getModel());
        vehicle.setYear(vehicleDto.getYear());
        vehicle.setColor(vehicleDto.getColor());
        vehicle.setType(vehicleDto.getType());
        vehicle.setTransmission(vehicleDto.getTransmission());
        vehicle.setFeatures(vehicleDto.getFeatures());
        vehicle.setMpg(vehicleDto.getMpg());
        vehicle.setMileage(vehicleDto.getMileage());
        vehicle.setPrice(vehicleDto.getPrice());
        vehicle.setLot(vehicleDto.getLot());
//        vehicle.setTickets(vehicleDto.getTicketsDto()
//                .stream().map(ServiceTicket::from).collect(Collectors.toList()));
        return vehicle;
    }
}
