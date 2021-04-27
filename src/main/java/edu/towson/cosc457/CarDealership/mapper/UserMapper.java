package edu.towson.cosc457.CarDealership.mapper;

import edu.towson.cosc457.CarDealership.misc.Role;
import edu.towson.cosc457.CarDealership.model.User;
import edu.towson.cosc457.CarDealership.model.dto.UserDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = Role.class)
public interface UserMapper {
    UserDto toDto(User user);

    @InheritInverseConfiguration
    User fromDto(UserDto userDto);
}
