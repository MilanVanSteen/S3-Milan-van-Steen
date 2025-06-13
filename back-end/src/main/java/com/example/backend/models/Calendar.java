package com.example.backend.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Calendar {
    private int calendarID;
    private User user;
    private boolean personal;
    private List<Event> events;

    public Calendar(int calendarID, User user, boolean personal) {
        this.calendarID = calendarID;
        this.user = user;
        this.personal = personal;
    }

    public Calendar(int calendarID, int userID, boolean personal) {
        this.calendarID = calendarID;
        this.user = new User(userID);
        this.personal = personal;
    }

    public Calendar(User user, boolean personal) {
        this.user = user;
        this.personal = personal;
    }

    public Calendar(int userID, boolean personal) {
        this.user = new User(userID);
        this.personal = personal;
    }

    public Calendar(int calendarID){
        this.calendarID = calendarID;
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
