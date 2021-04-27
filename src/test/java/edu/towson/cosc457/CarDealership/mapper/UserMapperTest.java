package edu.towson.cosc457.CarDealership.mapper;

import edu.towson.cosc457.CarDealership.misc.Role;
import edu.towson.cosc457.CarDealership.model.User;
import edu.towson.cosc457.CarDealership.model.dto.UserDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserMapperTest {
    private static UserMapper mapper;

    @BeforeAll
    public static void setUp() {
        mapper = new UserMapperImpl();
    }

    @Test
    void shouldMapToDto() {
        User user = User.builder()
                .id(1L)
                .username("username")
                .role(Role.ADMIN)
                .isActive(true)
                .build();

        UserDto userDto = mapper.toDto(user);

        assertAll(() -> {
           assertThat(userDto).isInstanceOf(UserDto.class);
           assertEquals(userDto.getId(), user.getId());
           assertEquals(userDto.getUsername(), user.getUsername());
           assertEquals(userDto.getRole(), user.getRole());
           assertEquals(userDto.getIsActive(), user.getIsActive());
        });
    }

    @Test
    void shouldMapFromDto() {
        UserDto userDto = UserDto.builder()
                .id(1L)
                .username("username")
                .role(Role.ADMIN)
                .isActive(true)
                .build();

        User user = mapper.fromDto(userDto);

        assertAll(() -> {
            assertThat(user).isInstanceOf(User.class);
            assertEquals(user.getId(), userDto.getId());
            assertEquals(user.getUsername(), userDto.getUsername());
            assertEquals(user.getRole(), userDto.getRole());
            assertEquals(user.getIsActive(), userDto.getIsActive());
        });
    }

    @Test
    void shouldReturnNullEntity() {
        User user = mapper.fromDto(null);

        assertThat(user).isEqualTo(null);
    }

    @Test
    void shouldReturnNullDto() {
        UserDto userDto = mapper.toDto(null);

        assertThat(userDto).isEqualTo(null);
    }
}
