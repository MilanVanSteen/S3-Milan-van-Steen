package com.example.backend.containers;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.backend.dtos.CalendarDTO;
import com.example.backend.dtos.UserDTO;
import com.example.backend.interfaces.CalendarEventInterface;
import com.example.backend.interfaces.CalendarInterface;
import com.example.backend.mappers.CalendarMapper;
import com.example.backend.mappers.EventMapper;
import com.example.backend.models.Calendar;
import com.example.backend.models.CalendarEvent;
import com.example.backend.models.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CalendarContainerTest {

    @Mock
    private CalendarInterface calendarRepo;

    @Mock
    private CalendarEventInterface calendarEventRepo;

    private CalendarContainer calendarContainer;

    @BeforeEach
    void setUp() {
        calendarContainer = new CalendarContainer(calendarRepo, calendarEventRepo);
    }

    @Test
    void testGetAllCalendarsReturnsCalendarsWithEvents() {
        // Arrange
        UserDTO userDTO = new UserDTO();
        userDTO.setUserID(1);
        CalendarDTO calendarDTO = new CalendarDTO(1, userDTO, false);
        when(calendarRepo.findAll()).thenReturn(List.of(calendarDTO));
        List<Event> events = List.of(new Event(1));
        when(calendarEventRepo.findEventsByCalendarID(1)).thenReturn(EventMapper.toDTOList(events));

        // Act
        List<Calendar> calendars = calendarContainer.getAllCalendars();

        // Assert
        assertNotNull(calendars);
        assertEquals(1, calendars.size());
        assertEquals(1, calendars.get(0).getCalendarID());
        assertNotNull(calendars.get(0).getCalendarEvents());
        assertEquals(1, calendars.get(0).getCalendarEvents().size());
    }

    @Test
    void testGetAllCalendarsReturnsCalendarsEvenWhenNoEvents() {
        // Arrange
        UserDTO userDTO = new UserDTO();
        userDTO.setUserID(1);
        CalendarDTO calendarDTO = new CalendarDTO(1, userDTO, false);
        when(calendarRepo.findAll()).thenReturn(List.of(calendarDTO));
        when(calendarEventRepo.findEventsByCalendarID(1)).thenReturn(new ArrayList<>());

        // Act
        List<Calendar> calendars = calendarContainer.getAllCalendars();

        // Assert
        assertNotNull(calendars);
        assertEquals(1, calendars.size());
        assertEquals(1, calendars.get(0).getCalendarID());
        assertTrue(calendars.get(0).getCalendarEvents() == null || calendars.get(0).getCalendarEvents().isEmpty());
    }

    @Test
    void testGetCalendarByIdReturnsCalendarWithEvents() {
        // Arrange
        UserDTO userDTO = new UserDTO();
        userDTO.setUserID(1);
        CalendarDTO calendarDTO = new CalendarDTO(1, userDTO, false);
        when(calendarRepo.findById(1)).thenReturn(Optional.of(calendarDTO));
        List<Event> events = List.of(new Event(1));
        when(calendarEventRepo.findEventsByCalendarID(1)).thenReturn(EventMapper.toDTOList(events));

        // Act
        Calendar calendar = calendarContainer.getCalendarById(1);

        // Assert
        assertNotNull(calendar);
        assertEquals(1, calendar.getCalendarID());
        assertNotNull(calendar.getCalendarEvents());
        assertEquals(1, calendar.getCalendarEvents().size());
    }

    @Test
    void testGetCalendarByIdReturnsCalendarEvenWhenNoEvents() {
        // Arrange
        UserDTO userDTO = new UserDTO();
        userDTO.setUserID(1);
        CalendarDTO calendarDTO = new CalendarDTO(1, userDTO, false);
        when(calendarRepo.findById(1)).thenReturn(Optional.of(calendarDTO));
        when(calendarEventRepo.findEventsByCalendarID(1)).thenReturn(new ArrayList<>());

        // Act
        Calendar calendar = calendarContainer.getCalendarById(1);

        // Assert
        assertNotNull(calendar);
        assertEquals(1, calendar.getCalendarID());
        assertTrue(calendar.getCalendarEvents() == null || calendar.getCalendarEvents().isEmpty());
    }

    @Test
    void testGetCalendarsByUserIDReturnsCalendarsWithEvents() {
        // Arrange
        UserDTO userDTO = new UserDTO();
        userDTO.setUserID(42);
        CalendarDTO calendarDTO = new CalendarDTO(1, userDTO, true);
        when(calendarRepo.getCalendarsByUserID(42)).thenReturn(List.of(calendarDTO));
        List<Event> events = List.of(new Event(100));
        when(calendarEventRepo.findEventsByCalendarID(1)).thenReturn(EventMapper.toDTOList(events));

        // Act
        List<Calendar> calendars = calendarContainer.getCalendarsByUserID(42);

        // Assert
        assertNotNull(calendars);
        assertEquals(1, calendars.size());
        assertEquals(1, calendars.get(0).getCalendarID());
        assertNotNull(calendars.get(0).getCalendarEvents());
        assertEquals(1, calendars.get(0).getCalendarEvents().size());
    }

    @Test
    void testGetCalendarsByUserIDReturnsCalendarsEvenWhenNoEvents() {
        // Arrange
        UserDTO userDTO = new UserDTO();
        userDTO.setUserID(42);
        CalendarDTO calendarDTO = new CalendarDTO(1, userDTO, true);
        when(calendarRepo.getCalendarsByUserID(42)).thenReturn(List.of(calendarDTO));
        when(calendarEventRepo.findEventsByCalendarID(1)).thenReturn(new ArrayList<>());

        // Act
        List<Calendar> calendars = calendarContainer.getCalendarsByUserID(42);

        // Assert
        assertNotNull(calendars);
        assertEquals(1, calendars.size());
        assertEquals(1, calendars.get(0).getCalendarID());
        assertNull(calendars.get(0).getCalendarEvents());
    }

    @Test
    void testCreateCalendarReturnsSavedCalendar() {
        // Arrange
        Calendar inputCalendar = new Calendar(42, true);
        inputCalendar.setCalendarID(1);
        CalendarDTO savedDTO = CalendarMapper.toDTO(inputCalendar);

        when(calendarRepo.save(any(CalendarDTO.class))).thenReturn(savedDTO);

        // Act
        Calendar result = calendarContainer.createCalendar(inputCalendar);

        // Assert
        assertNotNull(result);
        assertEquals(inputCalendar.getCalendarID(), result.getCalendarID());
        assertEquals(inputCalendar.isPersonal(), result.isPersonal());
    }

    @Test
    void testSaveCalendarEventReturnsTrueWhenSuccessful() {
        // Arrange
        CalendarEvent calendarEvent = new CalendarEvent(1, 1);
        when(calendarEventRepo.save(any())).thenReturn(null); // mock success

        // Act
        boolean result = calendarContainer.saveCalendarEvent(calendarEvent);

        // Assert
        assertTrue(result);
    }

    @Test
    void testSaveCalendarEventReturnsFalseOnException() {
        // Arrange
        CalendarEvent calendarEvent = new CalendarEvent(1, 1);
        doThrow(new RuntimeException("DB failure")).when(calendarEventRepo).save(any());

        // Act
        boolean result = calendarContainer.saveCalendarEvent(calendarEvent);

        // Assert
        assertFalse(result);
    }

    @Test
    void testDeleteCalendarReturnsTrueWhenSuccessful() {
        // Arrange
        doNothing().when(calendarRepo).deleteById(1);

        // Act
        boolean result = calendarContainer.deleteCalendar(1);

        // Assert
        assertTrue(result);
    }

    @Test
    void testDeleteCalendarReturnsFalseOnException() {
        // Arrange
        doThrow(new RuntimeException("DB failure")).when(calendarRepo).deleteById(1);

        // Act
        boolean result = calendarContainer.deleteCalendar(1);

        // Assert
        assertFalse(result);
    }

    @Test
    void testDeleteAllCalendarsByUserIdCallsRepo() {
        // Arrange
        int userID = 123;
        doNothing().when(calendarRepo).deleteAllByUserDTO_UserID(userID);

        // Act
        calendarContainer.deleteAllCalendarsByUserId(userID);

        // Assert
        verify(calendarRepo, times(1)).deleteAllByUserDTO_UserID(userID);
    }
}