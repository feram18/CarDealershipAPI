package edu.towson.cosc457.CarDealership.repository;

import edu.towson.cosc457.CarDealership.model.Comment;
import edu.towson.cosc457.CarDealership.model.Mechanic;
import edu.towson.cosc457.CarDealership.model.ServiceTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<List<Comment>> findByServiceTicket(ServiceTicket serviceTicket);
    Optional<List<Comment>> findByMechanic(Mechanic mechanic);
    Optional<List<Comment>> findByDateCreated(LocalDate date);
}
