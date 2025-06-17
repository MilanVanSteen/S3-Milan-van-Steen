package com.example.backend.controllers;

import com.example.backend.containers.EventContainer;
import com.example.backend.containers.UserContainer;
import com.example.backend.models.Event;
import com.example.backend.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(EventController.class)
class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EventContainer eventContainer;

    @MockitoBean
    private UserContainer userContainer;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testGetAllEvents_withResults_returnsOk() throws Exception {
        Event event = new Event();
        event.setEventID(1);
        event.setName("Test Event");

        when(eventContainer.getAllEvents()).thenReturn(List.of(event));

        mockMvc.perform(get("/event/getAllEvents"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].eventID").value(1))
                .andExpect(jsonPath("$[0].title").value("Test Event"));
    }

    @Test
    void testGetAllEvents_noResults_returnsNoContent() throws Exception {
        when(eventContainer.getAllEvents()).thenReturn(List.of());

        mockMvc.perform(get("/event/getAllEvents"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetEventById_exists_returnsOk() throws Exception {
        Event event = new Event();
        event.setEventID(1);
        event.setName("Sample Event");

        when(eventContainer.getEventById(1)).thenReturn(event);

        mockMvc.perform(get("/event/getEventById").param("eventID", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Sample Event"));
    }

    @Test
    void testGetEventById_notFound_returnsNotFound() throws Exception {
        when(eventContainer.getEventById(anyInt())).thenReturn(null);

        mockMvc.perform(get("/event/getEventById").param("eventID", "99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateEvent_returnsCreatedEvent() throws Exception {
        Event event = new Event();
        event.setEventID(5);
        event.setName("Created Event");
        User user = new User();
        user.setUserID(10);
        event.setUser(user);

        // Mocks
        when(userContainer.getUserById(10)).thenReturn(user);
        when(eventContainer.createEvent(any(Event.class))).thenReturn(event);

        mockMvc.perform(post("/event/createEvent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(event)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eventID").value(5))
                .andExpect(jsonPath("$.title").value("Created Event"));
    }

    @Test
    void testDeleteEvent_success_returnsOk() throws Exception {
        when(eventContainer.deleteEvent(1)).thenReturn(true);

        mockMvc.perform(delete("/event/deleteEvent").param("eventID", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted event"));
    }

    @Test
    void testDeleteEvent_notFound_returnsNoContent() throws Exception {
        when(eventContainer.deleteEvent(anyInt())).thenReturn(false);

        mockMvc.perform(delete("/event/deleteEvent").param("eventID", "123"))
                .andExpect(status().isNoContent());
    }
}
