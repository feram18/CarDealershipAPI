package edu.towson.cosc457.CarDealership.model.dto;

import edu.towson.cosc457.CarDealership.misc.EmployeeType;
import edu.towson.cosc457.CarDealership.misc.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class EmployeeDto {
    private Long id;
    private String firstName;
    private Character middleInitial;
    private String lastName;
    private Gender gender;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private String email;
    private Long workLocationId;
    private Double salary;
    private LocalDate dateStarted;
    private AddressDto address;
    private Double hoursWorked;
    private EmployeeType employeeType;
}