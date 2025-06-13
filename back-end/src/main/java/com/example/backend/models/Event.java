package com.example.backend.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@SuppressWarnings("unused")
public class Event {
    private int eventID;
    private User user;
    private String name;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<Category> categories;

    public Event(int eventID, User user, String name, LocalDateTime startDate, LocalDateTime endDate) {
        this.eventID = eventID;
        this.user = user;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Event(int eventID, int userID, String name, LocalDateTime startDate, LocalDateTime endDate) {
        this.eventID = eventID;
        this.user = new User(userID);
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Event(User user, String name, LocalDateTime startDate, LocalDateTime endDate) {
        this.user = user;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Event(int userID, String name, LocalDateTime startDate, LocalDateTime endDate) {
        this.user = new User(userID);
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Event(int eventID){
        this.eventID = eventID;
    }

    public Event() {}

    public List<Category> getEventCategories(){
        return categories;
    }

    public boolean setEventCategories(List<Category> categories){
        if (categories != null && !categories.isEmpty()) {
            this.categories = categories;
            return true;
        }
        return false;
    }
}
