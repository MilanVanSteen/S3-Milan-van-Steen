package com.example.backend.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@SuppressWarnings("unused")
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

    public List<Event> getCalendarEvents(){
        return events;
    }

    public void setCalendarEvents(List<Event> events){
        if (events != null && !events.isEmpty()) {
            this.events = events;
        }
    }
}
