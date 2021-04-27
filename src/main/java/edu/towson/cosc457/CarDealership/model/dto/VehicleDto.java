package edu.towson.cosc457.CarDealership.model.dto;

import edu.towson.cosc457.CarDealership.misc.TransmissionType;
import edu.towson.cosc457.CarDealership.misc.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VehicleDto {
    private Long id;
    private String vin;
    private String make;
    private String model;
    private Integer year;
    private String color;
    private VehicleType type;
    private TransmissionType transmission;
    private String features;
    private Integer mpg;
    private Integer mileage;
    private Double price;
    private Long lotId;
}
