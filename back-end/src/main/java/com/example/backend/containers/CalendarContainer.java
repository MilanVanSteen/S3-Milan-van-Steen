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

    public CalendarContainer(CalendarInterface _repo, CalendarEventInterface _calendarEventRepo) {
        this.repo = _repo;
        this.calendarEventRepo = _calendarEventRepo;
    }

    public List<Calendar> GetAllCalendars() {
        List<Calendar> calendars = CalendarMapper.toModelList((List<CalendarDTO>)repo.findAll());
        for (Calendar calendar : calendars){
            if(calendar != null) {
                List<Event> events = EventMapper.toModelList(calendarEventRepo.findEventsByCalendarID(calendar.getCalendarID()));

                if(events != null && !events.isEmpty()) {
                    boolean success = calendar.SetCalendarEvents(events);
                    if(!success) {
                        return null;
                    }
                }
            }
        }
        return calendars;
    }

    public Calendar GetCalendarById(int calendarID) {
        Calendar calendar = CalendarMapper.toModel(repo.findById(calendarID).orElse(null));
        List<Event> events = EventMapper.toModelList(calendarEventRepo.findEventsByCalendarID(calendarID));

        if(events != null && !events.isEmpty() && calendar != null) {
            boolean success = calendar.SetCalendarEvents(events);
            if(!success) {
                return null;
            }
        }
        return calendar;
    }

    public List<Calendar> GetCalendarsByUserID(int userID) {
        List<Calendar> calendars = CalendarMapper.toModelList(repo.getCalendarsByUserID(userID));
        for (Calendar calendar : calendars){
            if(calendar != null) {
                List<Event> events = EventMapper.toModelList(calendarEventRepo.findEventsByCalendarID(calendar.getCalendarID()));

                if(events != null && !events.isEmpty()) {
                    boolean success = calendar.SetCalendarEvents(events);
                    if(!success) {
                        return null;
                    }
                }
                else{
                    calendar.SetCalendarEvents(new ArrayList<>());
                }
            }
        }
        return calendars;
    }

    public Calendar CreateCalendar(Calendar calendar){
        return CalendarMapper.toModel(repo.save(CalendarMapper.toDTO(calendar)));
    }

    public boolean SaveCalendarEvent(CalendarEvent calendarEvent){
        try {
            calendarEventRepo.save(CalendarEventMapper.toDTO(calendarEvent));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean DeleteCalendar(int calendarID){
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
