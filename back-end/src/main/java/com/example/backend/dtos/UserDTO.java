package com.example.backend.dtos;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class UserDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public UserDTO(String email, String password, String area) {
        this.email = email;
        this.password = password;
        this.area = area;
    }

    public UserDTO() {}
}
