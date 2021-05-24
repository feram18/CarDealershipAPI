package edu.towson.cosc457.CarDealership.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.towson.cosc457.CarDealership.exceptions.NotFoundException;
import edu.towson.cosc457.CarDealership.misc.Role;
import edu.towson.cosc457.CarDealership.model.User;
import edu.towson.cosc457.CarDealership.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Collections;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
    private User editedUser;

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

        editedUser = User.builder()
                .id(1L)
                .username("username2")
                .password("newPassword")
                .role(Role.ADMIN)
                .isActive(false)
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
    void shouldFailToAddUser_Null() throws Exception {
        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(null)))
                .andExpect(status().is(400)); // BAD_REQUEST
    }

    @Test
    void shouldGetAllUsers() throws Exception {
        when(userService.getUsers()).thenReturn(Collections.singletonList(user));

        mockMvc.perform(get("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200));
    }

    @Test
    void shouldGetAllUsers_EmptyList() throws Exception {
        when(userService.getUsers()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", empty()));
    }

    @Test
    void shouldGetUserById() throws Exception {
        when(userService.getUser(user.getId())).thenReturn(user);

        mockMvc.perform(get("/api/v1/users/{id}", user.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", is(user.getId().intValue())))
                .andExpect(jsonPath("$.username", is(user.getUsername())))
                .andExpect(jsonPath("$.role", is(String.valueOf(user.getRole()))))
                .andExpect(jsonPath("$.isActive", is(user.getIsActive())));
    }

    @Test
    void shouldFailToGetUserById_NotFound() throws Exception {
        when(userService.getUser(user.getId())).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/api/v1/users/{id}", user.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(404));
    }

    @Test
    void shouldDeleteUser() throws Exception {
        when(userService.deleteUser(user.getId())).thenReturn(user);

        mockMvc.perform(delete("/api/v1/users/{id}", user.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200));
    }

    @Test
    void shouldFailToDeleteUser_NotFound() throws Exception {
        when(userService.deleteUser(user.getId())).thenThrow(NotFoundException.class);

        mockMvc.perform(delete("/api/v1/users/{id}", user.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(404));
    }

    @Test
    void shouldUpdateUser() throws Exception {
        when(userService.editUser(user.getId(), editedUser)).thenReturn(editedUser);

        mockMvc.perform(put("/api/v1/users/{id}", user.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(editedUser)))
                .andExpect(status().is(200));
    }

    @Test
    @Disabled
    void shouldFailToUpdateUser_NotFound() throws Exception {
        when(userService.editUser(user.getId(), editedUser)).thenThrow(NotFoundException.class);

        mockMvc.perform(put("/api/v1/users/{id}", user.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsBytes(editedUser)))
                .andExpect(status().is(404));
    }
}
