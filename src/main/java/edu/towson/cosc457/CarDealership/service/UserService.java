package edu.towson.cosc457.CarDealership.service;

import edu.towson.cosc457.CarDealership.exceptions.NotFoundException;
import edu.towson.cosc457.CarDealership.misc.Entity;
import edu.towson.cosc457.CarDealership.model.User;
import edu.towson.cosc457.CarDealership.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(AddressService.class);

    /**
     * Create a new User in the database
     * @param user User object to be added to database
     * @return User saved on repository
     */
    public User addUser(User user) {
        LOGGER.info("Create new User in the database");
        return userRepository.save(user);
    }

    /**
     * Get All Users
     * @return List of Users
     */
    public List<User> getUsers() {
        LOGGER.info("Get all Users");
        return StreamSupport
                .stream(userRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    /**
     * Get User by Id
     * @param id identifier of User to be fetched
     * @return fetched User
     * @throws NotFoundException if no User with matching id found
     */
    public User getUser(Long id) {
        LOGGER.info("Get User with id {}", id);
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Entity.USER.toString(), id, HttpStatus.NOT_FOUND));
    }

    /**
     * Delete User by Id
     * @param id identifier of User to be deleted
     * @return deleted User
     * @throws NotFoundException if no User with matching id found
     */
    public User deleteUser(Long id) {
        LOGGER.info("Delete User with id {}", id);
        User user = getUser(id);
        userRepository.delete(user);
        return user;
    }

    /**
     * Update User
     * @param id identifier of User to be updated
     * @param user User object with updated fields
     * @return updated User
     * @throws NotFoundException if no User with matching id found
     */
    public User editUser(Long id, User user) {
        LOGGER.info("Update User with id {}", id);
        User userToEdit = getUser(id);
        userToEdit.setId(user.getId());
        userToEdit.setUsername(user.getUsername());
        userToEdit.setPassword(user.getPassword());
        userToEdit.setRole(user.getRole());
        userToEdit.setIsActive(user.getIsActive());
        return userToEdit;
    }
}
