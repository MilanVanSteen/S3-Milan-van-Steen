package com.example.backend.Models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Calendar {
    private int calendarID;
    private int userID;
    private boolean isPersonal;
    private List<Event> events;

    public Calendar(int calendarID, int userID, boolean isPersonal) {
        this.calendarID = calendarID;
        this.userID = userID;
        this.isPersonal = isPersonal;
    }

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
