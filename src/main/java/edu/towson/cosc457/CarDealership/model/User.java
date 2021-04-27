package edu.towson.cosc457.CarDealership.model;

import com.sun.istack.NotNull;
import edu.towson.cosc457.CarDealership.misc.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
}
