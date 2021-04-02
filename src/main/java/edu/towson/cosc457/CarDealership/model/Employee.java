package edu.towson.cosc457.CarDealership.model;

import com.sun.istack.NotNull;
import edu.towson.cosc457.CarDealership.misc.EmployeeType;
import edu.towson.cosc457.CarDealership.misc.Gender;
import edu.towson.cosc457.CarDealership.misc.Role;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;

@Data
@Entity
@Table(name = "EMPLOYEE")
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
            length = 9,
            unique = true)
    private Integer ssn;
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
    @Column(name = "phone_no")
    private String phoneNumber;
    @Column(name = "email")
    private String email;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH})
    @JoinColumn(name = "work_loc")
    private Location workLocation;
    @Column(name = "salary")
    private Double salary;
    @Column(name = "date_started")
    private LocalDate dateStarted;
    @Column(name = "address")
    private String address;
    @Column(name = "hours_worked")
    private Double hoursWorked;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "role")
    private Role role;
    @NotNull
    @Column(name = "username",
            unique = true)
    private String username;
    @NotNull
    @Column(name = "password")
    private String password;

    public Employee() { }

    public Employee(Integer ssn,
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
                    String address,
                    Double hoursWorked,
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