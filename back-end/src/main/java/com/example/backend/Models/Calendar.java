package com.example.backend.Models;

import lombok.Getter;

import java.util.List;

@Getter
public class Calendar {
    private int CalendarID;
    private int UserID;
    private boolean IsPersonal;
    private List<Event> events;

    public Calendar(int calendarID, int userID, boolean isPersonal) {
        this.CalendarID = calendarID;
        this.UserID = userID;
        this.IsPersonal = isPersonal;
    }

    public List<Event> GetCalendarEvents(){
        return events;
    }
}
