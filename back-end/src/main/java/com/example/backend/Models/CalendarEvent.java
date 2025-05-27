package com.example.backend.Models;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CalendarEvent {
    @Id
    private int calendarEventID;
    private Calendar calendar;
    private Event event;

    public CalendarEvent(int calendarEventID, Calendar calendar, Event event){
        this.calendarEventID = calendarEventID;
        this.calendar = calendar;
        this.event = event;
    }

    public CalendarEvent(int calendarEventID, int calendarID, int eventID){
        this.calendarEventID = calendarEventID;
        this.calendar = new Calendar(calendarID);
        this.event = new Event(eventID);
    }

    public CalendarEvent(){}
}
