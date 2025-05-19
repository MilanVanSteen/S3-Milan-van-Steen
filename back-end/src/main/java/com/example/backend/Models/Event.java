package com.example.backend.Models;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class Event {
    private int EventID;
    private int UserID;
    private String Name;
    private LocalDateTime StartDate;
    private LocalDateTime EndDate;
    private List<Category> categories;

    public Event(int eventID, int userID, String name, LocalDateTime startDate, LocalDateTime endDate) {
        this.EventID = eventID;
        this.UserID = userID;
        this.Name = name;
        this.StartDate = startDate;
        this.EndDate = endDate;
    }

    public List<Category> GetEventCategories(){
        return categories;
    }
}
