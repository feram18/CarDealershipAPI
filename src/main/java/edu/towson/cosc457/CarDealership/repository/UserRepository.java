package edu.towson.cosc457.CarDealership.repository;

import edu.towson.cosc457.CarDealership.misc.Role;
import edu.towson.cosc457.CarDealership.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<List<User>> findByRole(Role role);
}
