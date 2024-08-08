package com.inspark.sabeel.user.domain.model;

import com.inspark.sabeel.auth.domain.model.Role;
import com.inspark.sabeel.common.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class User extends BaseModel {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private Set<Role> roles;
    private boolean enabled;
    private boolean accountLocked;
    private boolean emailVerified;

    public String getFullName() {
        return firstName + " " + lastName;
    }

}