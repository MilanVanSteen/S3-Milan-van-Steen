package com.example.backend.Models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class Event {
    private int eventID;
    private int userID;
    private String name;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<Category> categories;

    public Event(int eventID, int userID, String name, LocalDateTime startDate, LocalDateTime endDate) {
        this.eventID = eventID;
        this.userID = userID;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public List<Category> GetEventCategories(){
        return categories;
    }

    public boolean SetEventCategories(List<Category> categories){
        if (categories != null && !categories.isEmpty()) {
            this.categories = categories;
            return true;
        }
        return false;
    }
}
