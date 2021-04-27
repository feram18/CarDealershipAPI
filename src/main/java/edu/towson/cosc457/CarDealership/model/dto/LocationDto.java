package edu.towson.cosc457.CarDealership.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LocationDto {
    private Long id;
    private String name;
    private AddressDto address;
    private Long siteManagerId;
}
