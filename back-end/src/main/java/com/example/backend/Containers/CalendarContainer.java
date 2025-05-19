package com.example.backend.Containers;

import com.example.backend.DTOs.CalendarDTO;
import com.example.backend.Interfaces.CalendarInterface;
import com.example.backend.Mappers.CalendarMapper;
import com.example.backend.Models.Calendar;

import java.util.List;

public class CalendarContainer {
    private final CalendarInterface repo;

    public CalendarContainer(CalendarInterface _repo) {
        this.repo = _repo;
    }

    public List<Calendar> getAllCalendars() {
        return CalendarMapper.toModelList((List<CalendarDTO>)repo.findAll());
    }

    public Calendar getCalendarById(int calendarID) {
        return CalendarMapper.toModel(repo.findById(calendarID).orElse(null));
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
}
