package edu.towson.cosc457.CarDealership.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sun.istack.NotNull;
import edu.towson.cosc457.CarDealership.misc.EmployeeType;
import edu.towson.cosc457.CarDealership.misc.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;

@Data
@Entity
@Table(name = "employee", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING, name = "employee_type")
public abstract class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;
    @NotNull
    @Column(name = "ssn", length = 11, unique = true)
    private String ssn;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "middle_init", length = 1)
    private Character middleInitial;
    @Column(name = "last_name")
    private String lastName;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;
    @Column(name = "dob")
    private LocalDate dateOfBirth;
    @Column(name = "phone_no", length = 12)
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

    public Integer getYearsWorked() {
        return Period.between(getDateStarted(), LocalDate.now()).getYears();
    }

    public Integer getAge() {
        return Period.between(getDateOfBirth(), LocalDate.now()).getYears();
    }
}