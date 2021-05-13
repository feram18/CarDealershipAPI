package edu.towson.cosc457.CarDealership.service;

import edu.towson.cosc457.CarDealership.exceptions.NotFoundException;
import edu.towson.cosc457.CarDealership.misc.Role;
import edu.towson.cosc457.CarDealership.model.User;
import edu.towson.cosc457.CarDealership.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;
    private User user;
    private User editedUser;

    @BeforeEach
    public void setUp() {
        user = User.builder()
                .id(1L)
                .username("username")
                .password("password")
                .role(Role.ADMIN)
                .isActive(true)
                .build();

        editedUser = User.builder()
                .id(1L)
                .username("newUsername")
                .password("newPassword")
                .role(Role.ADMIN)
                .isActive(true)
                .build();
    }

    @Test
    void shouldSaveUser() {
        userService.addUser(user);

        verify(userRepository, times(1)).save(userArgumentCaptor.capture());

        assertThat(userArgumentCaptor.getValue()).usingRecursiveComparison().isEqualTo(user);
    }

    @Test
    void shouldFailToSaveNullUser() {
        userService.addUser(null);

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void shouldGetUserById() {
        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        User actualUser = userService.getUser(user.getId());
        verify(userRepository, times(1)).findById(user.getId());

        assertAll(() -> {
            assertThat(actualUser).isNotNull();
            assertThat(actualUser).usingRecursiveComparison().isEqualTo(user);
        });
    }

    @Test
    void shouldFailToGetUserById() {
        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.getUser(user.getId()));

        verify(userRepository, times(1)).findById(user.getId());
    }

    @Test
    void shouldGetAllUsers(){
        List<User> expectedUsers = new ArrayList<>();
        expectedUsers.add(user);
        expectedUsers.add(User.builder()
                .id(2L)
                .build());
        expectedUsers.add(User.builder()
                .id(3L)
                .build());

        Mockito.when(userRepository.findAll()).thenReturn(expectedUsers);
        List<User> actualUsers = userService.getUsers();
        verify(userRepository, times(1)).findAll();

        assertAll(() -> {
            assertThat(actualUsers).isNotNull();
            assertThat(actualUsers.size()).isEqualTo(expectedUsers.size());
        });
    }

    @Test
    void shouldGetAllUsers_EmptyList() {
        Mockito.when(userRepository.findAll()).thenReturn(new ArrayList<>());

        List<User> actualUsers = userService.getUsers();

        assertThat(actualUsers).isEmpty();
    }

    @Test
    void shouldDeleteUser() {
        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        User deletedUser = userService.deleteUser(user.getId());
        verify(userRepository, times(1)).delete(user);

        assertAll(() -> {
            assertThat(deletedUser).isNotNull();
            assertThat(deletedUser).usingRecursiveComparison().isEqualTo(user);
        });
    }

    @Test
    void shouldFailToDeleteUser() {
        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.deleteUser(user.getId()));

        verify(userRepository, never()).delete(any(User.class));
    }

    @Test
    void shouldUpdateUser() {
        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        User updatedUser = userService.editUser(user.getId(), editedUser);

        assertAll(() -> {
            assertThat(updatedUser).isNotNull();
            assertThat(updatedUser).usingRecursiveComparison().isEqualTo(editedUser);
        });
    }

    @Test
    void shouldFailToUpdateUser() {
        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.editUser(user.getId(), editedUser));

    }
}
