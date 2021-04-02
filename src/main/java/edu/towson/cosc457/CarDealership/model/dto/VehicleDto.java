package edu.towson.cosc457.CarDealership.model.dto;

import edu.towson.cosc457.CarDealership.misc.TransmissionType;
import edu.towson.cosc457.CarDealership.misc.VehicleType;
import edu.towson.cosc457.CarDealership.model.Lot;
import edu.towson.cosc457.CarDealership.model.Vehicle;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
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
    private Lot lot;
    private Integer mpg;
    private Integer mileage;
    private Double price;
    private List<ServiceTicketDto> ticketsDto = new ArrayList<>();

    public static VehicleDto from (Vehicle vehicle) {
        VehicleDto vehicleDto = new VehicleDto();
        vehicleDto.setId(vehicle.getId());
        vehicleDto.setVin(vehicle.getVin());
        vehicleDto.setMake(vehicle.getMake());
        vehicleDto.setModel(vehicle.getModel());
        vehicleDto.setYear(vehicle.getYear());
        vehicleDto.setColor(vehicle.getColor());
        vehicleDto.setType(vehicle.getType());
        vehicleDto.setTransmission(vehicle.getTransmission());
        vehicleDto.setFeatures(vehicle.getFeatures());
        vehicleDto.setLot(vehicle.getLot());
        vehicleDto.setMpg(vehicle.getMpg());
        vehicleDto.setMileage(vehicle.getMileage());
        vehicleDto.setPrice(vehicle.getPrice());
        vehicleDto.setTicketsDto(vehicle.getTickets()
                .stream().map(ServiceTicketDto::from).collect(Collectors.toList()));
        return vehicleDto;
    }
}
