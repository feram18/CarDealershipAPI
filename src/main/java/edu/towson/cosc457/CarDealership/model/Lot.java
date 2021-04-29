package edu.towson.cosc457.CarDealership.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "lot", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Lot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lot_id", updatable = false)
    private Long id;
    @Column(name = "lot_size")
    private Double size; // in sq. ft.
    @JsonBackReference
    @NotNull
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH})
    @JoinColumn(name = "location_id")
    private Location location;
    @JsonManagedReference
    @OneToMany(mappedBy = "lot")
    private List<Vehicle> vehicles;

    public void addVehicleToLot(Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    public void removeVehicleFromLot(Vehicle vehicle) {
        vehicles.remove(vehicle);
    }
}
