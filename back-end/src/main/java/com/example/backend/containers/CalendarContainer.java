package com.example.backend.containers;

import com.example.backend.dtos.CalendarDTO;
import com.example.backend.interfaces.CalendarEventInterface;
import com.example.backend.interfaces.CalendarInterface;
import com.example.backend.mappers.CalendarEventMapper;
import com.example.backend.mappers.CalendarMapper;
import com.example.backend.mappers.EventMapper;
import com.example.backend.models.Calendar;
import com.example.backend.models.CalendarEvent;
import com.example.backend.models.Event;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CalendarContainer {
    private final CalendarInterface repo;
    private final CalendarEventInterface calendarEventRepo;

    public CalendarContainer(CalendarInterface repo, CalendarEventInterface calendarEventRepo) {
        this.repo = repo;
        this.calendarEventRepo = calendarEventRepo;
    }

    public List<Calendar> getAllCalendars() {
        List<Calendar> calendars = CalendarMapper.toModelList((List<CalendarDTO>)repo.findAll());
        for (Calendar calendar : calendars) {
            List<Event> events = EventMapper.toModelList(calendarEventRepo.findEventsByCalendarID(calendar.getCalendarID()));

            if (!events.isEmpty()) {
                calendar.setCalendarEvents(events);
            }
        }
        return calendars;
    }

    public Calendar getCalendarById(int calendarID) {
        Calendar calendar = CalendarMapper.toModel(repo.findById(calendarID).orElse(null));
        List<Event> events = EventMapper.toModelList(calendarEventRepo.findEventsByCalendarID(calendarID));

        if(calendar != null && !events.isEmpty()) {
            calendar.setCalendarEvents(events);
        }
        return calendar;
    }

    public List<Calendar> getCalendarsByUserID(int userID) {
        List<Calendar> calendars = CalendarMapper.toModelList(repo.getCalendarsByUserID(userID));
        for (Calendar calendar : calendars) {
            List<Event> events = EventMapper.toModelList(calendarEventRepo.findEventsByCalendarID(calendar.getCalendarID()));

            if (!events.isEmpty()) {
                calendar.setCalendarEvents(events);
            } else {
                calendar.setCalendarEvents(new ArrayList<>());
            }
        }
        return calendars;
    }

    public Calendar createCalendar(Calendar calendar){
        return CalendarMapper.toModel(repo.save(CalendarMapper.toDTO(calendar)));
    }

    public boolean saveCalendarEvent(CalendarEvent calendarEvent){
        try {
            calendarEventRepo.save(CalendarEventMapper.toDTO(calendarEvent));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deleteCalendar(int calendarID){
        try {
            repo.deleteById(calendarID);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Transactional
    public void deleteAllCalendarsByUserId(int userID) {
        repo.deleteAllByUserDTO_UserID(userID);
    }
}
