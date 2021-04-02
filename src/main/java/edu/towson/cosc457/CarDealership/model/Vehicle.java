package edu.towson.cosc457.CarDealership.model;

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
    @Column(name = "vin",
            length = 17)
    private String vin;
    @Column(name = "make",
            nullable = false)
    private String make;
    @Column(name = "model",
            nullable = false)
    private String model;
    @Column(name = "year",
            length = 4,
            nullable = false)
    private Integer year;
    @Column(name = "color",
            nullable = false)
    private String color;
    @Column(name = "type",
            nullable = false)
    private VehicleType type;
    @Column(name = "transmission",
            nullable = false)
    @Enumerated(value = EnumType.STRING)
    private TransmissionType transmission;
    @Column(name = "features")
    private String features;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH})
    @JoinColumn(name = "lot_id")
    private Lot lot;
    @Column(name = "mpg",
            nullable = false)
    private Integer mpg;
    @Column(name = "mileage",
            nullable = false)
    private Integer mileage;
    @Column(name = "price")
    private Double price;
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
                   Lot lot,
                   Integer mpg,
                   Integer mileage,
                   Double price,
                   List<ServiceTicket> tickets) {
        this.vin = vin;
        this.make = make;
        this.model = model;
        this.year = year;
        this.color = color;
        this.type = type;
        this.transmission = transmission;
        this.features = features;
        this.lot = lot;
        this.mpg = mpg;
        this.mileage = mileage;
        this.price = price;
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
        vehicle.setLot(vehicleDto.getLot());
        vehicle.setMpg(vehicleDto.getMpg());
        vehicle.setMileage(vehicleDto.getMileage());
        vehicle.setPrice(vehicleDto.getPrice());
        vehicle.setTickets(vehicleDto.getTicketsDto()
                .stream().map(ServiceTicket::from).collect(Collectors.toList()));
        return vehicle;
    }
}
