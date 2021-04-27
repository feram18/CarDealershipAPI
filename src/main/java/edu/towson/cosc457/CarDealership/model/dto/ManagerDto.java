package edu.towson.cosc457.CarDealership.model.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class ManagerDto extends EmployeeDto {
    private Long siteManagerId;
    private Long departmentId;
}
