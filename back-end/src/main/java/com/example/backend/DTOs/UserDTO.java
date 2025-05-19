package com.example.backend.DTOs;

import lombok.Getter;
import org.springframework.data.annotation.Id;

@Getter
public class UserDTO {
    @Id
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
