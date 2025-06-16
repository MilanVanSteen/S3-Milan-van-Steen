package com.example.backend.mappers;

import com.example.backend.dtos.CalendarDTO;
import com.example.backend.dtos.CalendarEventDTO;
import com.example.backend.dtos.EventDTO;
import com.example.backend.models.Calendar;
import com.example.backend.models.CalendarEvent;
import com.example.backend.models.Event;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CalendarEventMapperTest {

    @Test
    void testToDTO_withNewCalendarEvent() {
        // Arrange
        Calendar calendar = new Calendar(1, null, true);
        Event event = new Event(2, null, "EventName", null, null);
        CalendarEvent calendarEvent = new CalendarEvent(0, calendar, event);

        // Act
        CalendarEventDTO dto = CalendarEventMapper.toDTO(calendarEvent);

        // Assert
        assertNotNull(dto);
        assertEquals(0, dto.getCalendarEventID());
        assertNotNull(dto.getCalendarDTO());
        assertNotNull(dto.getEventDTO());
    }

    @Test
    void testToDTO_withExistingCalendarEvent() {
        // Arrange
        Calendar calendar = new Calendar(1, null, true);
        Event event = new Event(2, null, "EventName", null, null);
        CalendarEvent calendarEvent = new CalendarEvent(10, calendar, event);

        // Act
        CalendarEventDTO dto = CalendarEventMapper.toDTO(calendarEvent);

        // Assert
        assertNotNull(dto);
        assertEquals(10, dto.getCalendarEventID());
        assertNotNull(dto.getCalendarDTO());
        assertNotNull(dto.getEventDTO());
    }

    @Test
    void testToDTO_withNull_returnsNull() {
        // Assert
        assertNull(CalendarEventMapper.toDTO(null));
    }

    @Test
    void testToModel_withNewCalendarEventDTO() {
        // Arrange
        CalendarDTO calendarDTO = new CalendarDTO(1, null, true);
        EventDTO eventDTO = new EventDTO(2, null, "EventName", null, null);
        CalendarEventDTO calendarEventDTO = new CalendarEventDTO(0, calendarDTO, eventDTO);

        // Act
        CalendarEvent model = CalendarEventMapper.toModel(calendarEventDTO);

        // Assert
        assertNotNull(model);
        assertEquals(0, model.getCalendarEventID());
        assertNotNull(model.getCalendar());
        assertNotNull(model.getEvent());
    }

    @Test
    void testToModel_withExistingCalendarEventDTO() {
        // Arrange
        CalendarDTO calendarDTO = new CalendarDTO(1, null, true);
        EventDTO eventDTO = new EventDTO(2, null, "EventName", null, null);
        CalendarEventDTO calendarEventDTO = new CalendarEventDTO(10, calendarDTO, eventDTO);

        // Act
        CalendarEvent model = CalendarEventMapper.toModel(calendarEventDTO);

        // Assert
        assertNotNull(model);
        assertEquals(10, model.getCalendarEventID());
        assertNotNull(model.getCalendar());
        assertNotNull(model.getEvent());
    }

    @Test
    void testToModel_withNull_returnsNull() {
        // Assert
        assertNull(CalendarEventMapper.toModel(null));
    }

    @Test
    void testToDTOList_withNull_returnsEmptyList() {
        // Arrange & Act
        List<CalendarEventDTO> calendarEventDTOS = CalendarEventMapper.toDTOList(null);

        // Assert
        assertNotNull(calendarEventDTOS);
        assertTrue(calendarEventDTOS.isEmpty());
    }

    @Test
    void testToModelList_withNull_returnsEmptyList() {
        // Arrange & Act
        List<CalendarEvent> models = CalendarEventMapper.toModelList(null);

        // Assert
        assertNotNull(models);
        assertTrue(models.isEmpty());
    }
}
