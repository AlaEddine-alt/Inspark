package com.inspark.sabeel.user.infrastructure.entity;

import com.inspark.sabeel.auth.infrastructure.entity.RoleEntity;
import com.inspark.sabeel.common.BaseEntity;
import com.inspark.sabeel.user.domain.enums.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;

/**
 * Entity representing a user.
 * implements UserDetails and Principal to be used in Spring Security.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity implements UserDetails, Principal {

    @Column(name = "firstName", nullable = false)
    private String firstName;

    @Column(name = "provider")
    private String provider;

    @Column(name = "providerId", unique = true)
    private String providerId;

    @Column(name = "lastName", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "emailVerified", nullable = false)
    private boolean emailVerified;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @Column(name = "accountLocked", nullable = false)
    private boolean accountLocked;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "dateOfBirth")
    private LocalDate dateOfBirth;

    @Column(name = "address")
    private String address;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<RoleEntity> roles;




    /**
     * Represents the name of the user which is the id of the user.
     * This method is used in Spring Security for authentication purposes.
     * ----
     * In Spring Security, the `Principal` interface represents the currently authenticated user.
     * The `getName` method is used to retrieve the unique identifier of the user, which in this case is the user's id.
     * This id is inherited from the `BaseEntity` class and is used to uniquely identify the user in the system.
     * ----
     *
     * @return the id of the user as a String
     */
    @Override
    public String getName() {
        return getId().toString();
    }


    /**
     * Represents the authorities of the user.
     * By default, the roles of the user are considered as authorities.
     * Authorities are the permissions or roles granted to the user.
     * This method is used in Spring Security for authorization purposes.
     * -------
     * In Spring Security, the `UserDetails` interface requires the implementation of the `getAuthorities` method.
     * This method returns a collection of `GrantedAuthority` objects, which represent the roles or permissions assigned to the user.
     * Here, we map each `RoleEntity` to a `SimpleGrantedAuthority` using the role's name.
     * This allows Spring Security to understand what roles or permissions the user has.
     *
     * @return a collection of authorities granted to the user
     */

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(
                role -> new SimpleGrantedAuthority(role.getName())
        ).toList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Returns the username of the user, which is the user's email.
     * This method is used in Spring Security for authentication purposes.
     * ----
     * In Spring Security, the `UserDetails` interface requires the implementation of the `getUsername` method.
     * This method returns the username used for authentication, which in this case is the user's email.
     * The email is used as the unique identifier for the user during the login process.
     * ----
     *
     * @return the email of the user
     */
    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !accountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

}
