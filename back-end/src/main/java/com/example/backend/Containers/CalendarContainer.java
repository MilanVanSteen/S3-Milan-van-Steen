package com.example.backend.Containers;

import com.example.backend.DTOs.CalendarDTO;
import com.example.backend.Interfaces.CalendarEventInterface;
import com.example.backend.Interfaces.CalendarInterface;
import com.example.backend.Mappers.CalendarMapper;
import com.example.backend.Mappers.EventMapper;
import com.example.backend.Models.Calendar;
import com.example.backend.Models.Event;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

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
            }
        }
        return calendars;
    }

    public Calendar CreateCalendar(Calendar calendar){
        return CalendarMapper.toModel(repo.save(CalendarMapper.toDTO(calendar)));
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
