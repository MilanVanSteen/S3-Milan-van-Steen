package com.example.backend.mappers;

import com.example.backend.dtos.EventDTO;
import com.example.backend.dtos.UserDTO;
import com.example.backend.models.Event;
import com.example.backend.models.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EventMapperTest {

    @Test
    void testToDTO_withExistingEvent() {
        // Arrange
        User user = new User(1, "user@test.com", "pass", "area");
        Event event = new Event(10, user, "Meeting", LocalDateTime.of(2025, 6, 1, 10, 0), LocalDateTime.of(2025, 6, 1, 11, 0));

        // Act
        EventDTO dto = EventMapper.toDTO(event);

        // Assert
        assertNotNull(dto);
        assertEquals(10, dto.getEventID());
        assertEquals("Meeting", dto.getName());
        assertEquals("user@test.com", dto.getUserDTO().getEmail());
        assertEquals(LocalDateTime.of(2025, 6, 1, 10, 0), dto.getStartDate());
        assertEquals(LocalDateTime.of(2025, 6, 1, 11, 0), dto.getEndDate());
    }

    @Test
    void testToDTO_withNewEvent() {
        // Arrange
        User user = new User(2, "user2@test.com", "pw", "region");
        Event event = new Event(0, user, "Workout", LocalDateTime.of(2025, 7, 1, 9, 0), LocalDateTime.of(2025, 7, 1, 10, 0));

        // Act
        EventDTO dto = EventMapper.toDTO(event);

        // Assert
        assertNotNull(dto);
        assertEquals(0, dto.getEventID());
        assertEquals("Workout", dto.getName());
    }

    @Test
    void testToDTO_withNull() {
        // Assert
        assertNull(EventMapper.toDTO(null));
    }

    @Test
    void testToModel_withValidDTO() {
        // Arrange
        UserDTO userDTO = new UserDTO(3, "dto@test.com", "secret", "area3");
        EventDTO dto = new EventDTO(11, userDTO, "Study", LocalDateTime.of(2025, 8, 2, 14, 0), LocalDateTime.of(2025, 8, 2, 16, 0));

        // Act
        Event event = EventMapper.toModel(dto);

        // Assert
        assertNotNull(event);
        assertEquals(11, event.getEventID());
        assertEquals("Study", event.getName());
        assertEquals("dto@test.com", event.getUser().getEmail());
    }

    @Test
    void testToModel_withNull() {
        // Assert
        assertNull(EventMapper.toModel(null));
    }

    @Test
    void testToDTOList_withNullList() {
        // Act
        List<EventDTO> result = EventMapper.toDTOList(null);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testToDTOList_withValidEvents() {
        // Arrange
        User user = new User(4, "u@a.com", "pw", "aa");
        List<Event> events = List.of(new Event(12, user, "Lunch", LocalDateTime.now(), LocalDateTime.now().plusHours(1)));

        // Act
        List<EventDTO> result = EventMapper.toDTOList(events);

        // Assert
        assertEquals(1, result.size());
        assertEquals(12, result.get(0).getEventID());
    }

    @Test
    void testToModelList_withNullList() {
        // Act
        List<Event> result = EventMapper.toModelList(null);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testToModelList_withValidDTOs() {
        // Arrange
        UserDTO userDTO = new UserDTO(5, "e@e.com", "pw", "ee");
        List<EventDTO> eventDTOList = List.of(new EventDTO(13, userDTO, "Walk", LocalDateTime.now(), LocalDateTime.now().plusHours(1)));

        // Act
        List<Event> result = EventMapper.toModelList(eventDTOList);

        // Assert
        assertEquals(1, result.size());
        assertEquals(13, result.get(0).getEventID());
    }
}