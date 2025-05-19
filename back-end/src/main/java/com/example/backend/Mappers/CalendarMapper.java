package com.example.backend.Mappers;

import com.example.backend.DTOs.CalendarDTO;
import com.example.backend.Models.Calendar;

public class CalendarMapper {
    public static CalendarDTO toDTO(Calendar calendar) {
        if(calendar == null){
            return null;
        }
        return new CalendarDTO(calendar.getCalendarID(), calendar.getUserID(), calendar.isIsPersonal());
    }

    public static Calendar toModel(CalendarDTO calendarDTO) {
        if(calendarDTO == null){
            return null;
        }
        return new Calendar(calendarDTO.getCalendarID(), calendarDTO.getUserID(), calendarDTO.isIsPersonal());
    }
}
