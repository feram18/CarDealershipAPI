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

@NoRepositoryBean
public interface EmployeeRepository<T extends Employee> extends JpaRepository<T, Long> {
    Optional<T> findBySsn(String ssn);
    Optional<List<T>> findByFirstName(String firstName);
    Optional<List<T>> findByLastName(String lastName);
    Optional<List<T>> findByGender(Gender gender);
    Optional<List<T>> findByDateOfBirth(LocalDate dateOfBirth);
    Optional<T> findByPhoneNumber(String phoneNumber);
    Optional<T> findByEmail(String email);
    Optional<List<T>> findByWorkLocation(Location location);
    Optional<List<T>> findByDateStarted(LocalDate dateStarted);
    Optional<List<T>> findBySalaryLessThan(Double salary);
    Optional<List<T>> findBySalaryGreaterThan(Double salary);
    Optional<List<T>> findBySalaryBetween(Double minSalary, Double maxSalary);
}
