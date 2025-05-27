package com.example.backend.Controllers;

import com.example.backend.Containers.CalendarContainer;
import com.example.backend.Containers.EventContainer;
import com.example.backend.Containers.UserContainer;
import com.example.backend.Models.Calendar;
import com.example.backend.Models.CalendarEvent;
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
    public ResponseEntity<List<Calendar>> GetAllCalendars() {
        List<Calendar> calendars = calendarContainer.GetAllCalendars();
        if (calendars == null || !calendars.iterator().hasNext()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(calendars);
        }
    }

    @GetMapping("/getCalendarById")
    public ResponseEntity<Calendar> GetCalendarById(@RequestParam int calendarID) {
        Calendar calendar = calendarContainer.GetCalendarById(calendarID);
        if (calendar != null) {
            return ResponseEntity.ok(calendar);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/getCalendarsByUserID")
    public ResponseEntity<List<Calendar>> GetCalendarsByUserID(@RequestParam int userID) {
        List<Calendar> calendars = calendarContainer.GetCalendarsByUserID(userID);
        if (calendars == null || !calendars.iterator().hasNext()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(calendars);
        }
    }

    @PostMapping("/createCalendar")
    public ResponseEntity<Calendar> CreateCalendar(@RequestBody Calendar calendar) {
        int userID = calendar.getUser().getUserID();
        calendar.setUser(userContainer.GetUserById(userID));

        Calendar createdCalendar = calendarContainer.CreateCalendar(calendar);
        return ResponseEntity.ok(createdCalendar);
    }

    @PostMapping("/saveCalendarEvent")
    public ResponseEntity<String> SaveCalendarEvent(@RequestBody CalendarEvent calendarEvent) {
        int calendarID = calendarEvent.getCalendar().getCalendarID();
        calendarEvent.setCalendar(calendarContainer.GetCalendarById(calendarID));

        int eventID = calendarEvent.getEvent().getEventID();
        calendarEvent.setEvent(eventContainer.GetEventById(eventID));

        if (calendarContainer.SaveCalendarEvent(calendarEvent)){
            return ResponseEntity.ok("Saved calendarEvent");
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/deleteCalendar")
    public ResponseEntity<String> DeleteCalendar(@RequestParam int calendarID) {
        if (calendarContainer.DeleteCalendar(calendarID)){
            return ResponseEntity.ok("Deleted calendar");
        }
        return ResponseEntity.noContent().build();
    }
}
