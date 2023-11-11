package com.example.ultimavez.model.DTO;

import com.example.ultimavez.model.enums.UserEnum;

public class LoginDTO {

    private String email, password;
    private UserEnum userType;

    public LoginDTO(String email, String password, UserEnum userType) {
        this.email = email;
        this.password = password;
        this.userType = userType;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public UserEnum getUserType() {
        return userType;
    }
}
