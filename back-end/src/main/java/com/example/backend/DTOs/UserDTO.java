package com.example.backend.DTOs;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class UserDTO {
    @Id
    private int userID;
    private String email;
    private String password;
    private String area;

    public UserDTO(int userID, String email, String password, String area) {
        this.userID = userID;
        this.email = email;
        this.password = password;
        this.area = area;
    }

    public UserDTO() {

    }
}
