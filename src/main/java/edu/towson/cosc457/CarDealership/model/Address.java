package edu.towson.cosc457.CarDealership.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "address", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id", updatable = false)
    private Long id;
    @NotNull
    @Column(name = "street")
    private String street;
    @NotNull
    @Column(name = "city")
    private String city;
    @NotNull
    @Column(name = "state")
    private String state;
    @NotNull
    @Column(name = "zip_code", length = 5)
    private Integer zipCode;
}
