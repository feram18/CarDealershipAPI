package edu.towson.cosc457.CarDealership.controller;

import edu.towson.cosc457.CarDealership.mapper.UserMapper;
import edu.towson.cosc457.CarDealership.model.User;
import edu.towson.cosc457.CarDealership.model.dto.UserDto;
import edu.towson.cosc457.CarDealership.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    public ResponseEntity<UserDto> addUser(@RequestBody final UserDto userDto) {
        User user = userService.addUser(userMapper.fromDto(userDto));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userMapper.toDto(user));
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(users.stream().map(userMapper::toDto).collect(Collectors.toList()));
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable final Long id) {
        User user = userService.getUser(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userMapper.toDto(user));
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<UserDto> deleteUser(@PathVariable final Long id) {
        User user = userService.deleteUser(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userMapper.toDto(user));
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<UserDto> editUser(@PathVariable final Long id,
                                            @RequestBody final UserDto userDto) {
        User user = userService.editUser(id, userMapper.fromDto(userDto));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userMapper.toDto(user));
    }
}
