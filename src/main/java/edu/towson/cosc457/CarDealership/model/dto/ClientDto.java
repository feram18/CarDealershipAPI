package edu.towson.cosc457.CarDealership.model.dto;

import edu.towson.cosc457.CarDealership.misc.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientDto {
    private Long id;
    private String firstName;
    private String lastName;
    private Gender gender;
    private String email;
    private String phoneNumber;
    private AddressDto address;
    private Long salesAssociateId;
    private Double minimumPrice;
    private Double maximumPrice;
}
