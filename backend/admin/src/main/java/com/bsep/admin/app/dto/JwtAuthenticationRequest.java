package com.bsep.admin.app.dto;

import javax.validation.constraints.Pattern;

public class JwtAuthenticationRequest {
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Username should not be empty and must have only letters and numbers.")
    private String username;
//    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{13,}$", message = "Wrong password format. Must have at least 13 characters and " +
//            "one upper case letter," +
//            "one lower case letter, number and special character.")
    private String password;

    public JwtAuthenticationRequest() {
        super();
    }

    public JwtAuthenticationRequest(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
