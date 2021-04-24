package edu.towson.cosc457.CarDealership.service;

import edu.towson.cosc457.CarDealership.exceptions.NotFoundException;
import edu.towson.cosc457.CarDealership.misc.Entity;
import edu.towson.cosc457.CarDealership.model.User;
import edu.towson.cosc457.CarDealership.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return StreamSupport
                .stream(userRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Entity.USER.toString(), id));
    }

    public User deleteUser(Long id) {
        User user = getUser(id);
        userRepository.delete(user);
        return user;
    }

    public User editUser(Long id, User user) {
        User userToEdit = getUser(id);
        userToEdit.setId(user.getId());
        userToEdit.setUsername(user.getUsername());
        userToEdit.setPassword(user.getPassword());
        userToEdit.setRole(user.getRole());
        userToEdit.setIsActive(user.getIsActive());
        return userToEdit;
    }
}
