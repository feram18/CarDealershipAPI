package edu.towson.cosc457.CarDealership.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sun.istack.NotNull;
import edu.towson.cosc457.CarDealership.misc.EmployeeType;
import edu.towson.cosc457.CarDealership.misc.Gender;
import edu.towson.cosc457.CarDealership.misc.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;

@Data
@Entity
@Table(name = "EMPLOYEE")
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING,
                        name = "employee_type")
public abstract class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",
            updatable = false)
    private Long id;
    @NotNull
    @Column(name = "ssn",
            length = 11,
            unique = true)
    private String ssn;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "middle_init",
            length = 1)
    private Character middleInitial;
    @Column(name = "last_name")
    private String lastName;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;
    @Column(name = "dob")
    private LocalDate dateOfBirth;
    @Column(name = "phone_no",
            length = 12)
    private String phoneNumber;
    @Column(name = "email")
    private String email;
    @JsonBackReference
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH})
    @JoinColumn(name = "work_location_id")
    private Location workLocation;
    @Column(name = "salary")
    private Double salary;
    @Column(name = "date_started")
    private LocalDate dateStarted;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;
    @Column(name = "hours_worked")
    private Double hoursWorked;
    @NotNull
    @Enumerated(value = EnumType.STRING)
    @Column(name = "employee_type")
    private EmployeeType employeeType;
    @NotNull
    @Column(name = "is_active",
            columnDefinition = "boolean default true")
    private Boolean isActive;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "user_role")
    private Role role;
    @NotNull
    @Column(name = "username",
            unique = true)
    private String username;
    @NotNull
    @Column(name = "password")
    private String password;

    public Employee(String ssn,
                    String firstName,
                    Character middleInitial,
                    String lastName,
                    Gender gender,
                    LocalDate dateOfBirth,
                    String phoneNumber,
                    String email,
                    Location workLocation,
                    Double salary,
                    LocalDate dateStarted,
                    Address address,
                    Double hoursWorked,
                    EmployeeType employeeType,
                    Boolean isActive,
                    Role role,
                    String username,
                    String password) {
        this.ssn = ssn;
        this.firstName = firstName;
        this.middleInitial = middleInitial;
        this.lastName = lastName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.workLocation = workLocation;
        this.salary = salary;
        this.dateStarted = dateStarted;
        this.address = address;
        this.hoursWorked = hoursWorked;
        this.employeeType = employeeType;
        this.isActive = isActive;
        this.role = role;
        this.username = username;
        this.password = password;
    }

    public Integer getYearsWorked(Employee employee) {
        return Period.between(employee.getDateStarted(), LocalDate.now()).getYears();
    }

    public Integer getAge(Employee employee) {
        return Period.between(employee.getDateOfBirth(), LocalDate.now()).getYears();
    }
}