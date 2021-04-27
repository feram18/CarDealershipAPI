package edu.towson.cosc457.CarDealership.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LotDto {
    private Long id;
    private Double size;
    private Long locationId;
}
