package com.example.backend.mappers;

import com.example.backend.dtos.CalendarDTO;
import com.example.backend.dtos.UserDTO;
import com.example.backend.models.Calendar;
import com.example.backend.models.User;

import java.util.ArrayList;
import java.util.List;

public class CalendarMapper {
    // Private constructor to prevent instantiation
    private CalendarMapper() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static CalendarDTO toDTO(Calendar calendar) {
        if (calendar == null) {
            return null;
        }
        UserDTO userDTO = UserMapper.toDTO(calendar.getUser());

        if (calendar.getCalendarID() == 0) {
            // New calendar, don't set ID
            return new CalendarDTO(userDTO, calendar.isPersonal());
        } else {
            // Existing calendar with ID
            return new CalendarDTO(calendar.getCalendarID(), userDTO, calendar.isPersonal());
        }
    }

    public static Calendar toModel(CalendarDTO calendarDTO) {
        if(calendarDTO == null){
            return null;
        }
        User user = UserMapper.toModel(calendarDTO.getUserDTO());
        return new Calendar(calendarDTO.getCalendarID(), user, calendarDTO.isPersonal());
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
