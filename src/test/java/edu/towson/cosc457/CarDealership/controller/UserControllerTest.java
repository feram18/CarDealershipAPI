package edu.towson.cosc457.CarDealership.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.towson.cosc457.CarDealership.controller.UserController;
import edu.towson.cosc457.CarDealership.misc.Role;
import edu.towson.cosc457.CarDealership.model.User;
import edu.towson.cosc457.CarDealership.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class UserControllerTest {
    public MockMvc mockMvc;
    @Autowired
    public UserController userController;
    @MockBean
    public UserService userService;
    private final ObjectMapper mapper = new ObjectMapper();
    private User user;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        user = User.builder()
                .id(1L)
                .username("username")
                .password("password")
                .role(Role.ADMIN)
                .isActive(true)
                .build();
    }

    @Test
    void shouldAddUser() throws Exception {
        when(userService.addUser(user)).thenReturn(user);

        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(user)))
                .andExpect(status().is(201));
    }

    @Test
    void shouldGetAllUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(Collections.singletonList(user));

        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().is(200));
    }

    @Test
    void shouldGetLotById() throws Exception {
        when(userService.getUser(user.getId())).thenReturn(user);

        mockMvc.perform(get("/api/v1/users/{id}", user.getId()))
                .andExpect(status().is(200));
    }

    @Test
    void shouldDeleteLot() throws Exception {
        when(userService.deleteUser(user.getId())).thenReturn(user);

        mockMvc.perform(delete("/api/v1/users/{id}", user.getId()))
                .andExpect(status().is(200));
    }

    @Test
    void shouldUpdateLot() throws Exception {
        User editedUser = User.builder()
                .id(1L)
                .username("username2")
                .password("newPassword")
                .role(Role.ADMIN)
                .isActive(true)
                .build();
        when(userService.editUser(user.getId(), editedUser)).thenReturn(editedUser);

        mockMvc.perform(put("/api/v1/users/{id}", user.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(editedUser)))
                .andExpect(status().is(200));
    }
}
