package com.example.backend.Models;

import lombok.Getter;

import java.util.List;

@Getter
public class User {
    private int UserID;
    private String Email;
    private String Password;
    private String Area;
    private List<Calendar> calendars;

    public User(int userID, String email, String password, String area) {
        this.UserID = userID;
        this.Email = email;
        this.Password = password;
        this.Area = area;
    }

    public List<Calendar> GetUserCalendars(){
        return calendars;
    }
}
