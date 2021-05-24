package edu.towson.cosc457.CarDealership.mapper;

import edu.towson.cosc457.CarDealership.misc.Role;
import edu.towson.cosc457.CarDealership.model.User;
import edu.towson.cosc457.CarDealership.model.dto.UserDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = Role.class)
public interface UserMapper {
    /**
     * Map from User entity to UserDTO
     * @param user User object to be mapped to UserDTO
     * @return mapped UserDto object
     */
    UserDto toDto(User user);

    /**
     * Map from UserDTO to User entity
     * @param userDto Map from UserDTO to User entity
     * @return mapped User object
     */
    @InheritInverseConfiguration
    User fromDto(UserDto userDto);
}
