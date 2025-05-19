package com.example.backend.DTOs;

import lombok.Getter;

@Getter
public class UserDTO {
    private int UserID;
    private String Email;
    private String Password;
    private String Area;

    public UserDTO(int userID, String email, String password, String area) {
        this.UserID = userID;
        this.Email = email;
        this.Password = password;
        this.Area = area;
    }
}
