package com.example.backend.controllers;

import com.example.backend.containers.CalendarContainer;
import com.example.backend.containers.EventContainer;
import com.example.backend.containers.UserContainer;
import com.example.backend.models.Calendar;
import com.example.backend.models.CalendarEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/calendar")
public class CalendarController {
    private final CalendarContainer calendarContainer;
    private final UserContainer userContainer;
    private final EventContainer eventContainer;

    @Autowired
    public CalendarController(CalendarContainer calendarContainer, UserContainer userContainer, EventContainer eventContainer) {
        this.calendarContainer = calendarContainer;
        this.userContainer = userContainer;
        this.eventContainer = eventContainer;
    }

    @GetMapping("/getAllCalendars")
    public ResponseEntity<List<Calendar>> getAllCalendars() {
        List<Calendar> calendars = calendarContainer.getAllCalendars();
        if (!calendars.iterator().hasNext()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(calendars);
        }
    }

    @GetMapping("/getCalendarById")
    public ResponseEntity<Calendar> getCalendarById(@RequestParam int calendarID) {
        Calendar calendar = calendarContainer.getCalendarById(calendarID);
        if (calendar != null) {
            return ResponseEntity.ok(calendar);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/getCalendarsByUserID")
    public ResponseEntity<List<Calendar>> getCalendarsByUserID(@RequestParam int userID) {
        List<Calendar> calendars = calendarContainer.getCalendarsByUserID(userID);
        if (calendars == null || !calendars.iterator().hasNext()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(calendars);
        }
    }

    @PostMapping("/createCalendar")
    public ResponseEntity<Calendar> createCalendar(@RequestBody Calendar calendar) {
        int userID = calendar.getUser().getUserID();
        calendar.setUser(userContainer.getUserById(userID));

        Calendar createdCalendar = calendarContainer.createCalendar(calendar);
        return ResponseEntity.ok(createdCalendar);
    }

    @PostMapping("/saveCalendarEvent")
    public ResponseEntity<String> saveCalendarEvent(@RequestBody CalendarEvent calendarEvent) {
        int calendarID = calendarEvent.getCalendar().getCalendarID();
        calendarEvent.setCalendar(calendarContainer.getCalendarById(calendarID));

        int eventID = calendarEvent.getEvent().getEventID();
        calendarEvent.setEvent(eventContainer.getEventById(eventID));

        if (calendarContainer.saveCalendarEvent(calendarEvent)){
            return ResponseEntity.ok("Saved calendarEvent");
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/deleteCalendar")
    public ResponseEntity<String> deleteCalendar(@RequestParam int calendarID) {
        if (calendarContainer.deleteCalendar(calendarID)){
            return ResponseEntity.ok("Deleted calendar");
        }
        return ResponseEntity.noContent().build();
    }
}
