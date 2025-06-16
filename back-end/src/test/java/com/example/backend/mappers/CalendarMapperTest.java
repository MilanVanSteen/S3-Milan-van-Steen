package com.example.backend.mappers;

import com.example.backend.dtos.CalendarDTO;
import com.example.backend.dtos.UserDTO;
import com.example.backend.models.Calendar;
import com.example.backend.models.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CalendarMapperTest {

    @Test
    void testToDTO_withValidCalendar() {
        // Arrange
        User user = new User(1, "test@example.com", "password123", "area51");
        Calendar calendar = new Calendar(1, user, true);

        // Act
        CalendarDTO dto = CalendarMapper.toDTO(calendar);

        // Assert
        assertNotNull(dto);
        assertEquals(1, dto.getCalendarID());
        assertEquals("test@example.com", dto.getUserDTO().getEmail());
        assertTrue(dto.isPersonal());
    }

    @Test
    void testToDTO_withNullCalendar() {
        // Assert
        assertNull(CalendarMapper.toDTO(null));
    }

    @Test
    void testToModel_withValidDTO() {
        // Arrange
        UserDTO userDTO = new UserDTO(1, "test@example.com", "password123", "area51");
        CalendarDTO dto = new CalendarDTO(1, userDTO, false);

        // Act
        Calendar model = CalendarMapper.toModel(dto);

        // Assert
        assertNotNull(model);
        assertEquals(1, model.getCalendarID());
        assertEquals("test@example.com", model.getUser().getEmail());
        assertFalse(model.isPersonal());
    }

    @Test
    void testToModel_withNullDTO() {
        // Assert
        assertNull(CalendarMapper.toModel(null));
    }

    @Test
    void testToDTOList_withNullList() {
        // Arrange
        List<CalendarDTO> result = CalendarMapper.toDTOList(null);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testToDTOList_withCalendars() {
        // Arrange
        User user = new User(1, "a@a.com", "pass", "area");
        Calendar calendar = new Calendar(5, user, true);

        // Act
        List<CalendarDTO> result = CalendarMapper.toDTOList(List.of(calendar));

        // Assert
        assertEquals(1, result.size());
        assertEquals(5, result.get(0).getCalendarID());
    }

    @Test
    void testToModelList_withNullList() {
        // Act
        List<Calendar> result = CalendarMapper.toModelList(null);

        // Arrange
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testToModelList_withDTOs() {
        // Arrange
        UserDTO userDTO = new UserDTO(2, "b@b.com", "123", "zone");
        CalendarDTO dto = new CalendarDTO(8, userDTO, false);

        // Act
        List<Calendar> result = CalendarMapper.toModelList(List.of(dto));

        // Assert
        assertEquals(1, result.size());
        assertEquals(8, result.get(0).getCalendarID());
    }
}