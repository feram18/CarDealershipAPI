package edu.towson.cosc457.CarDealership.model.dto;

import edu.towson.cosc457.CarDealership.misc.Role;
import edu.towson.cosc457.CarDealership.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private Role role;
    private Boolean isActive;

    public static UserDto from (User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setRole(user.getRole());
        userDto.setIsActive(user.getIsActive());
        return userDto;
    }
}
