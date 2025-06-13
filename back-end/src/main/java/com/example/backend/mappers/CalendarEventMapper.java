package com.example.backend.mappers;

import com.example.backend.dtos.CalendarDTO;
import com.example.backend.dtos.CalendarEventDTO;
import com.example.backend.dtos.EventDTO;
import com.example.backend.models.Calendar;
import com.example.backend.models.CalendarEvent;
import com.example.backend.models.Event;

import java.util.ArrayList;
import java.util.List;

public class CalendarEventMapper {
    // Private constructor to prevent instantiation
    private CalendarEventMapper() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static CalendarEventDTO toDTO(CalendarEvent calendarEvent) {
        if (calendarEvent == null) {
            return null;
        }

        CalendarDTO calendarDTO = CalendarMapper.toDTO(calendarEvent.getCalendar());
        EventDTO eventDTO = EventMapper.toDTO(calendarEvent.getEvent());

        if (calendarEvent.getCalendarEventID() == 0) {
            // New CalendarEvent, no ID set
            return new CalendarEventDTO(calendarDTO, eventDTO);
        } else {
            // Existing CalendarEvent with ID
            return new CalendarEventDTO(
                    calendarEvent.getCalendarEventID(),
                    calendarDTO,
                    eventDTO
            );
        }
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
