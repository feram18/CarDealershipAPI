package edu.towson.cosc457.CarDealership.repository;

import edu.towson.cosc457.CarDealership.misc.Gender;
import edu.towson.cosc457.CarDealership.misc.Role;
import edu.towson.cosc457.CarDealership.model.Employee;
import edu.towson.cosc457.CarDealership.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

//TODO - Parameter?
@NoRepositoryBean
//public interface EmployeeRepository<T extends Employee> extends JpaRepository<T, Long> {
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<List<Employee>> findBySsn(Integer ssn);
    Optional<List<Employee>> findByFirstName(String firstName);
    Optional<List<Employee>> findByLastName(String lastName);
    Optional<List<Employee>> findByGender(Gender gender);
    Optional<List<Employee>> findByDateOfBirth(LocalDate dateOfBirth);
    Optional<List<Employee>> findByPhoneNumber(String phoneNumber);
    Optional<List<Employee>> findByEmail(String email);
    Optional<List<Employee>> findByWorkLocation(Location location);
    Optional<List<Employee>> findByDateStarted(LocalDate dateStarted);
    Optional<List<Employee>> findBySalaryLessThan(Double salary);
    Optional<List<Employee>> findBySalaryGreaterThan(Double salary);
    Optional<List<Employee>> findBySalaryBetween(Double minSalary, Double maxSalary);
    Optional<List<Employee>> findByAddressLike(String addressLike);
    Optional<List<Employee>> findByRole(Role role);
    Optional<List<Employee>> findByUsername(String username);

//    Optional<List<T>> findBySsn(Integer ssn);
//    Optional<List<T>> findByFirstName(String firstName);
//    Optional<List<T>> findByLastName(String lastName);
//    Optional<List<T>> findByGender(Gender gender);
//    Optional<List<T>> findByDateOfBirth(LocalDate dateOfBirth);
//    Optional<List<T>> findByPhoneNumber(String phoneNumber);
//    Optional<List<T>> findByEmail(String email);
//    Optional<List<T>> findByWorkLocation(Location location);
//    Optional<List<T>> findByDateStarted(LocalDate dateStarted);
//    Optional<List<T>> findBySalaryLessThan(Double salary);
//    Optional<List<T>> findBySalaryGreaterThan(Double salary);
//    Optional<List<T>> findBySalaryBetween(Double minSalary, Double maxSalary);
//    Optional<List<T>> findByAddressLike(String addressLike);
//    Optional<List<T>> findByRole(Role role);
//    Optional<List<T>> findByUsername(String username);
}
