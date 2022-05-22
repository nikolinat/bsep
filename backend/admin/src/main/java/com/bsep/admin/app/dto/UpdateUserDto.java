package com.bsep.admin.app.dto;

import com.bsep.admin.app.validators.constraints.RolesConstraint;

import javax.validation.constraints.Pattern;
import java.util.List;

public class UpdateUserDto {
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Name should not be empty and must have only letters.")
    private String name;
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Last name should not be empty and must have only letters.")
    private String lastName;
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Username should not be empty and must have only letters and numbers.")
    private String username;
    @Pattern(regexp = "^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$",
            message = "Wrong email format.")
    private String email;
    @RolesConstraint
    List<String> roles;

    public UpdateUserDto() {

    }

    public UpdateUserDto(String name, String lastName, String username, String email, List<String> roles) {
        this.name = name;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "UpdateUserDto{" +
                "name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", roles=" + roles +
                '}';
    }
}
