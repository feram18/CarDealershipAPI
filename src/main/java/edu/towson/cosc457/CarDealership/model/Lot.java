package edu.towson.cosc457.CarDealership.model;

import edu.towson.cosc457.CarDealership.model.dto.LotDto;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "LOT")
public class Lot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lot_id",
            updatable = false)
    private Long id;
    @Column(name = "lot_size")
    private Double size; // in sq. ft.
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH})
    @JoinColumn(name = "location_id")
    private Location location;
    @OneToMany(mappedBy = "lot")
    private List<Vehicle> vehicles;

    public Lot() { }

    public void addVehicleToLot(Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    public void removeVehicleFromLot(Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    public Lot(Double size, Location location, List<Vehicle> vehicles) {
        this.size = size;
        this.location = location;
        this.vehicles = vehicles;
    }

    public static Lot from(LotDto lotDto) {
        Lot lot = new Lot();
        lot.setId(lotDto.getId());
        lot.setSize(lotDto.getSize());
        lot.setLocation(lotDto.getLocation());
        lot.setVehicles(lotDto.getVehiclesDto().stream().map(Vehicle::from).collect(Collectors.toList()));
        return lot;
    }
}
