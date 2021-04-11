package edu.towson.cosc457.CarDealership.model.dto;

import edu.towson.cosc457.CarDealership.model.Location;
import edu.towson.cosc457.CarDealership.model.Lot;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
public class LotDto {
    private Long id;
    private Double size;
    private Location location;
    private List<VehicleDto> vehiclesDto = new ArrayList<>();

    public LotDto() { }

    public static LotDto from(Lot lot) {
        LotDto lotDto = new LotDto();
        lotDto.setId(lot.getId());
        lotDto.setSize(lot.getSize());
        lotDto.setLocation(lot.getLocation());
        lotDto.setVehiclesDto(lot.getVehicles()
                .stream().map(VehicleDto::from).collect(Collectors.toList()));
        return lotDto;
    }
}
