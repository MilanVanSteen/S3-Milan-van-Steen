package com.example.backend.Mappers;

import com.example.backend.DTOs.CalendarDTO;
import com.example.backend.DTOs.CalendarEventDTO;
import com.example.backend.DTOs.EventDTO;
import com.example.backend.Models.Calendar;
import com.example.backend.Models.CalendarEvent;
import com.example.backend.Models.Event;

import java.util.ArrayList;
import java.util.List;

public class CalendarEventMapper {
    public static CalendarEventDTO toDTO(CalendarEvent calendarEvent) {
        if (calendarEvent == null) {
            return null;
        }

        CalendarDTO calendarDTO = CalendarMapper.toDTO(calendarEvent.getCalendar());
        EventDTO eventDTO = EventMapper.toDTO(calendarEvent.getEvent());

        return new CalendarEventDTO(
                calendarEvent.getCalendarEventID(),
                calendarDTO,
                eventDTO
        );
    }


    public static CalendarEvent toModel(CalendarEventDTO calendarEventDTO) {
        if (calendarEventDTO == null) {
            return null;
        }
        Calendar calendar = CalendarMapper.toModel(calendarEventDTO.getCalendarDTO());
        Event event = EventMapper.toModel(calendarEventDTO.getEventDTO());

        return new CalendarEvent(
                calendarEventDTO.getCalendarEventID(),
                calendar,
                event
        );
    }

    public static List<CalendarEventDTO> toDTOList(List<CalendarEvent> calendarEvents) {
        if (calendarEvents == null) {
            return new ArrayList<>();
        }
        return calendarEvents.stream()
                .map(CalendarEventMapper::toDTO)
                .toList();
    }

    public static List<CalendarEvent> toModelList(List<CalendarEventDTO> calendarEventDTOS) {
        if (calendarEventDTOS == null) {
            return new ArrayList<>();
        }
        return calendarEventDTOS.stream()
                .map(CalendarEventMapper::toModel)
                .toList();
    }
}
