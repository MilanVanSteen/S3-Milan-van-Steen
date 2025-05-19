package com.example.backend.Controllers;

import com.example.backend.Containers.CalendarContainer;
import com.example.backend.Interfaces.CalendarInterface;
import com.example.backend.Models.Calendar;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public class CalendarController {
    private final CalendarContainer calendarContainer;

    public CalendarController(CalendarInterface calendarInterface) {
        this.calendarContainer = new CalendarContainer(calendarInterface);
    }

    public ResponseEntity<List<Calendar>> GetAllCalendars() {
        List<Calendar> calendars = calendarContainer.GetAllCalendars();
        if (calendars == null || !calendars.iterator().hasNext()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(calendars);
        }
    }

    public ResponseEntity<Calendar> GetCalendarById(@RequestParam int calendarID) {
        Calendar calendar = calendarContainer.GetCalendarById(calendarID);
        if (calendar != null) {
            return ResponseEntity.ok(calendar);
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<Calendar> CreateCalendar(@RequestBody Calendar calendar) {
        Calendar createdCalendar = calendarContainer.CreateCalendar(calendar);
        return ResponseEntity.ok(createdCalendar);
    }

    public ResponseEntity<String> DeleteCalendar(@RequestParam int calendarID) {
        if (calendarContainer.DeleteCalendar(calendarID)){
            return ResponseEntity.ok("Deleted calendar");
        }
        return ResponseEntity.noContent().build();
    }
}
