package com.example.backend.Mappers;

import com.example.backend.DTOs.CalendarDTO;
import com.example.backend.Models.Calendar;

import java.util.ArrayList;
import java.util.List;

public class CalendarMapper {
    public static CalendarDTO toDTO(Calendar calendar) {
        if(calendar == null){
            return null;
        }
        return new CalendarDTO(calendar.getCalendarID(), calendar.getUserID(), calendar.isPersonal());
    }

    public static Calendar toModel(CalendarDTO calendarDTO) {
        if(calendarDTO == null){
            return null;
        }
        return new Calendar(calendarDTO.getCalendarID(), calendarDTO.getUserID(), calendarDTO.isIsPersonal());
    }

    public static List<CalendarDTO> toDTOList(List<Calendar> calendars) {
        if (calendars == null) {
            return new ArrayList<>();
        }
        return calendars.stream()
                .map(CalendarMapper::toDTO)
                .toList();
    }

    public static List<Calendar> toModelList(List<CalendarDTO> calendarDTOS) {
        if (calendarDTOS == null) {
            return new ArrayList<>();
        }
        return calendarDTOS.stream()
                .map(CalendarMapper::toModel)
                .toList();
    }
}
