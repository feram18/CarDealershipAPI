package edu.towson.cosc457.CarDealership.controller;

import edu.towson.cosc457.CarDealership.model.User;
import edu.towson.cosc457.CarDealership.model.dto.UserDto;
import edu.towson.cosc457.CarDealership.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> addUser(@RequestBody final UserDto userDto) {
        User user = userService.addUser(User.from(userDto));
        return new ResponseEntity<>(UserDto.from(user), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserDto> userDtos = users.stream().map(UserDto::from).collect(Collectors.toList());
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable final Long id) {
        User user = userService.getUser(id);
        return new ResponseEntity<>(UserDto.from(user), HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<UserDto> deleteUser(@PathVariable final Long id) {
        User user = userService.deleteUser(id);
        return new ResponseEntity<>(UserDto.from(user), HttpStatus.OK);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<UserDto> editUser(@PathVariable final Long id,
                                            @RequestBody final UserDto userDto) {
        User user = userService.editUser(id, User.from(userDto));
        return new ResponseEntity<>(UserDto.from(user), HttpStatus.OK);
    }
}
