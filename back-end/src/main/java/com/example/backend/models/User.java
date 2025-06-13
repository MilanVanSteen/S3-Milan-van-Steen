package com.example.backend.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class User {
    private int userID;
    private String email;
    private String password;
    private String area;
    private List<Calendar> calendars;

    public User(int userID, String email, String password, String area) {
        this.userID = userID;
        this.email = email;
        this.password = password;
        this.area = area;
    }

    public User(String email, String password, String area) {
        this.email = email;
        this.password = password;
        this.area = area;
    }

    public User(int userID) {
        this.userID = userID;
    }

    public User(){}

    public List<Calendar> GetUserCalendars(){
        return calendars;
    }
}
