package com.example.backend.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Calendar {
    private int calendarID;
    private User user;
    private boolean isPersonal;
    private List<Event> events;

    public Calendar(int calendarID, User user, boolean isPersonal) {
        this.calendarID = calendarID;
        this.user = user;
        this.isPersonal = isPersonal;
    }

    public Calendar(int calendarID, int userID, boolean isPersonal) {
        this.calendarID = calendarID;
        this.user = new User(userID);
        this.isPersonal = isPersonal;
    }

    public Calendar() {}

    public List<Event> GetCalendarEvents(){
        return events;
    }

    public boolean SetCalendarEvents(List<Event> events){
        if (events != null && !events.isEmpty()) {
            this.events = events;
            return true;
        }
        return false;
    }
}
