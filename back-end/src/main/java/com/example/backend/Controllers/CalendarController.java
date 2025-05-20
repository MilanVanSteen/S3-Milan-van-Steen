package com.example.backend.Controllers;

import com.example.backend.Containers.CalendarContainer;
import com.example.backend.Interfaces.CalendarInterface;
import com.example.backend.Models.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/calendar")
public class CalendarController {
    private final CalendarContainer calendarContainer;

    @Autowired
    public CalendarController(CalendarInterface calendarInterface) {
        this.calendarContainer = new CalendarContainer(calendarInterface);
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

    @PostMapping("/createCalendar")
    public ResponseEntity<Calendar> CreateCalendar(@RequestBody Calendar calendar) {
        Calendar createdCalendar = calendarContainer.CreateCalendar(calendar);
        return ResponseEntity.ok(createdCalendar);
    }

    @DeleteMapping("/deleteCalendar")
    public ResponseEntity<String> DeleteCalendar(@RequestParam int calendarID) {
        if (calendarContainer.DeleteCalendar(calendarID)){
            return ResponseEntity.ok("Deleted calendar");
        }
        return ResponseEntity.noContent().build();
    }
}
