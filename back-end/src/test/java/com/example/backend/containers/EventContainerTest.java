package com.example.backend.containers;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.backend.dtos.CategoryDTO;
import com.example.backend.dtos.EventDTO;
import com.example.backend.interfaces.EventCategoryInterface;
import com.example.backend.interfaces.EventInterface;
import com.example.backend.mappers.EventMapper;
import com.example.backend.models.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class EventContainerTest {

    @Mock
    private EventInterface eventRepo;

    @Mock
    private EventCategoryInterface categoryRepo;

    private EventContainer eventContainer;

    @BeforeEach
    void setUp() {
        eventContainer = new EventContainer(eventRepo, categoryRepo);
    }

    @Test
    void testGetAllEventsReturnsEventsWithCategories() {
        // Arrange
        EventDTO eventDTO = new EventDTO(1, null, "Test Event", LocalDateTime.now(), LocalDateTime.now().plusHours(2));
        when(eventRepo.findAll()).thenReturn(List.of(eventDTO));

        CategoryDTO categoryDTO = new CategoryDTO(1, "Test Category");
        when(categoryRepo.findCategoriesByEventID(anyInt())).thenReturn(List.of(categoryDTO));

        // Act
        List<Event> events = eventContainer.getAllEvents();

        // Assert
        assertNotNull(events);
        assertFalse(events.isEmpty());
        assertEquals("Test Event", events.get(0).getName());
        assertNotNull(events.get(0).getEventCategories());
        assertFalse(events.get(0).getEventCategories().isEmpty());
        assertEquals("Test Category", events.get(0).getEventCategories().get(0).getName());
    }

    @Test
    void testGetAllEventsWithEmptyCategories() {
        // Arrange
        EventDTO eventDTO = new EventDTO(1, null, "Event With Empty Categories", LocalDateTime.now(), LocalDateTime.now().plusHours(1));
        when(eventRepo.findAll()).thenReturn(List.of(eventDTO));
        when(categoryRepo.findCategoriesByEventID(1)).thenReturn(List.of());

        // Act
        List<Event> result = eventContainer.getAllEvents();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.get(0).getEventCategories().isEmpty());
    }

    @Test
    void testGetEventByIdReturnsEventWithCategories() {
        // Arrange
        EventDTO eventDTO = new EventDTO(1, null, "Test Event", LocalDateTime.now(), LocalDateTime.now().plusHours(2));
        when(eventRepo.findById(1)).thenReturn(Optional.of(eventDTO));

        CategoryDTO categoryDTO = new CategoryDTO(1, "Test Category");
        when(categoryRepo.findCategoriesByEventID(1)).thenReturn(List.of(categoryDTO));

        // Act
        Event event = eventContainer.getEventById(1);

        // Assert
        assertNotNull(event);
        assertEquals("Test Event", event.getName());
        assertNotNull(event.getEventCategories());
        assertFalse(event.getEventCategories().isEmpty());
        assertEquals("Test Category", event.getEventCategories().get(0).getName());
    }

    @Test
    void testGetEventByIdReturnsNullWhenEventNotFound() {
        // Arrange
        when(eventRepo.findById(99)).thenReturn(Optional.empty());

        // Act
        Event event = eventContainer.getEventById(99);

        // Assert
        assertNull(event);  // event should be null because repo returned empty
    }

    @Test
    void testGetEventByIdSkipsSettingCategoriesWhenEventIsNullButCategoriesExist() {
        // Arrange
        int eventId = 99;
        when(eventRepo.findById(eventId)).thenReturn(Optional.empty());  // event = null
        when(categoryRepo.findCategoriesByEventID(eventId))
                .thenReturn(List.of(new CategoryDTO(1, "Sample Category")));

        // Act
        Event result = eventContainer.getEventById(eventId);

        // Assert
        assertNull(result, "Expected null event when repo returns empty");
    }

    @Test
    void testGetEventByIdWithEmptyCategories() {
        // Arrange
        EventDTO eventDTO = new EventDTO(3, null, "Test Event", LocalDateTime.now(), LocalDateTime.now().plusHours(1));
        when(eventRepo.findById(3)).thenReturn(Optional.of(eventDTO));
        when(categoryRepo.findCategoriesByEventID(3)).thenReturn(List.of());

        // Act
        Event result = eventContainer.getEventById(3);

        // Assert
        assertNotNull(result);
        assertTrue(result.getEventCategories().isEmpty());
    }

    @Test
    void testCreateEvent() {
        // Arrange
        Event event = new Event(1, null, "New Event", LocalDateTime.now(), LocalDateTime.now().plusHours(1));
        EventDTO eventDTO = EventMapper.toDTO(event);
        when(eventRepo.save(any(EventDTO.class))).thenReturn(eventDTO);

        // Act
        Event created = eventContainer.createEvent(event);

        // Assert
        assertNotNull(created);
        assertEquals(event.getName(), created.getName());
        verify(eventRepo, times(1)).save(any(EventDTO.class));
    }

    @Test
    void testDeleteEventSuccess() {
        // Arrange
        doNothing().when(eventRepo).deleteById(1);

        // Act
        boolean result = eventContainer.deleteEvent(1);

        // Assert
        assertTrue(result);
        verify(eventRepo, times(1)).deleteById(1);
    }

    @Test
    void testDeleteEventFailure() {
        // Arrange
        doThrow(new RuntimeException()).when(eventRepo).deleteById(1);

        // Act
        boolean result = eventContainer.deleteEvent(1);

        // Assert
        assertFalse(result);
        verify(eventRepo, times(1)).deleteById(1);
    }

    @Test
    void testDeleteAllEventsByUserId() {
        // Arrange
        doNothing().when(eventRepo).deleteAllByUserDTO_UserID(42);

        // Act
        eventContainer.deleteAllEventsByUserId(42);

        // Assert
        verify(eventRepo, times(1)).deleteAllByUserDTO_UserID(42);
    }
}