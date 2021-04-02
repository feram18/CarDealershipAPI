package edu.towson.cosc457.CarDealership.model.dto;

import edu.towson.cosc457.CarDealership.misc.Gender;
import edu.towson.cosc457.CarDealership.misc.Role;
import edu.towson.cosc457.CarDealership.model.Location;
import lombok.Data;

import java.time.LocalDate;

@Data
public abstract class EmployeeDto {
    private Long id;
    private Integer ssn;
    private String firstName;
    private Character middleInitial;
    private String lastName;
    private Gender gender;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private String email;
    private Location workLocation;
    private Double salary;
    private LocalDate dateStarted;
    private String address;
    private Double hoursWorked;
    private Role role;
    private String username;
    private String password;
}