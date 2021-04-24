package edu.towson.cosc457.CarDealership.model;

import com.sun.istack.NotNull;
import edu.towson.cosc457.CarDealership.misc.Role;
import edu.towson.cosc457.CarDealership.model.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @NotNull
    @Column(name = "user_id", unique = true, updatable = false)
    private Long id;
    @NotNull
    @Column(name = "username", unique = true)
    private String username;
    @NotNull
    @Column(name = "password")
    private String password;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "user_role")
    private Role role;
    @Column(name = "is_active", columnDefinition = "boolean default false")
    private Boolean isActive;

    public User(String username, String password, Role role, Boolean isActive) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.isActive = isActive;
    }

    public static User from(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setRole(userDto.getRole());
        user.setIsActive(userDto.getIsActive());
        return user;
    }
}
