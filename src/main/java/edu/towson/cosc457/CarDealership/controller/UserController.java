package edu.towson.cosc457.CarDealership.controller;

import edu.towson.cosc457.CarDealership.mapper.UserMapper;
import edu.towson.cosc457.CarDealership.model.User;
import edu.towson.cosc457.CarDealership.model.dto.UserDto;
import edu.towson.cosc457.CarDealership.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @ApiOperation(value = "Add User", response = UserDto.class)
    @PostMapping
    public ResponseEntity<UserDto> addUser(@RequestBody final UserDto userDto) {
        LOGGER.info("POST /api/v1/users/");
        User user = userService.addUser(userMapper.fromDto(userDto));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userMapper.toDto(user));
    }

    @ApiOperation(value = "Fetch All Users", response = Iterable.class)
    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers() {
        LOGGER.info("GET /api/v1/users/");
        List<User> users = userService.getUsers();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(users.stream().map(userMapper::toDto).collect(Collectors.toList()));
    }

    @ApiOperation(value = "Fetch User by Id", response = UserDto.class)
    @GetMapping(value = "{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable final Long id) {
        LOGGER.info("GET /api/v1/users/{}", id);
        User user = userService.getUser(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userMapper.toDto(user));
    }

    @ApiOperation(value = "Delete User", response = UserDto.class)
    @DeleteMapping(value = "{id}")
    public ResponseEntity<UserDto> deleteUser(@PathVariable final Long id) {
        LOGGER.info("DELETE /api/v1/users/{}", id);
        User user = userService.deleteUser(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userMapper.toDto(user));
    }

    @ApiOperation(value = "Update User", response = UserDto.class)
    @PutMapping(value = "{id}")
    public ResponseEntity<UserDto> editUser(@PathVariable final Long id,
                                            @RequestBody final UserDto userDto) {
        LOGGER.info("PUT /api/v1/users/{}", id);
        User user = userService.editUser(id, userMapper.fromDto(userDto));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userMapper.toDto(user));
    }
}
