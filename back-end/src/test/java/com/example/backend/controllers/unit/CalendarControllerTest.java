package com.example.backend.controllers.unit;

import com.example.backend.containers.CalendarContainer;
import com.example.backend.containers.EventContainer;
import com.example.backend.containers.UserContainer;
import com.example.backend.models.Calendar;
import com.example.backend.models.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CalendarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CalendarContainer calendarContainer;

    @MockitoBean
    private UserContainer userContainer;

    @MockitoBean
    private EventContainer eventContainer;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testGetAllCalendars_withResults_returnsOk() throws Exception {
        User user = new User();
        user.setUserID(2);

        Calendar calendar = new Calendar();
        calendar.setCalendarID(1);
        calendar.setUser(user);
        calendar.setPersonal(true);

        when(calendarContainer.getAllCalendars()).thenReturn(List.of(calendar));

        mockMvc.perform(get("/calendar/getAllCalendars"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].calendarID").value(1))
                .andExpect(jsonPath("$[0].user.userID").value(2))
                .andExpect(jsonPath("$[0].personal").value(true));
    }

    @Test
    void testGetAllCalendars_noResults_returnsNoContent() throws Exception {
        when(calendarContainer.getAllCalendars()).thenReturn(List.of());

        mockMvc.perform(get("/calendar/getAllCalendars"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetCalendarById_exists_returnsOk() throws Exception {
        User user = new User();
        user.setUserID(2);

        Calendar calendar = new Calendar();
        calendar.setCalendarID(1);
        calendar.setUser(user);
        calendar.setPersonal(false);

        when(calendarContainer.getCalendarById(1)).thenReturn(calendar);

        mockMvc.perform(get("/calendar/getCalendarById").param("calendarID", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.calendarID").value(1))
                .andExpect(jsonPath("$.user.userID").value(2))
                .andExpect(jsonPath("$.personal").value(false));
    }

    @Test
    void testGetCalendarById_notFound_returnsNotFound() throws Exception {
        when(calendarContainer.getCalendarById(anyInt())).thenReturn(null);

        mockMvc.perform(get("/calendar/getCalendarById").param("calendarID", "99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateCalendar_returnsCreatedCalendar() throws Exception {
        User user = new User();
        user.setUserID(1);

        Calendar calendar = new Calendar();
        calendar.setCalendarID(5);
        calendar.setUser(user);
        calendar.setPersonal(true);

        when(userContainer.getUserById(1)).thenReturn(user);
        when(calendarContainer.createCalendar(any(Calendar.class))).thenReturn(calendar);

        String json = objectMapper.writeValueAsString(calendar);

        mockMvc.perform(post("/calendar/createCalendar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.calendarID").value(5))
                .andExpect(jsonPath("$.user.userID").value(1))
                .andExpect(jsonPath("$.personal").value(true));
    }

    @Test
    void testDeleteCalendar_success_returnsOk() throws Exception {
        when(calendarContainer.deleteCalendar(1)).thenReturn(true);

        mockMvc.perform(delete("/calendar/deleteCalendar").param("calendarID", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted calendar"));
    }

    @Test
    void testDeleteCalendar_notFound_returnsNoContent() throws Exception {
        when(calendarContainer.deleteCalendar(anyInt())).thenReturn(false);

        mockMvc.perform(delete("/calendar/deleteCalendar").param("calendarID", "123"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testSaveCalendarEvent_success_returnsOk() throws Exception {
        when(eventContainer.getEventById(anyInt())).thenReturn(new com.example.backend.models.Event() {{
            setEventID(10);
            setName("Mock Event");
        }});

        when(calendarContainer.getCalendarById(anyInt())).thenReturn(new Calendar() {{
            setCalendarID(20);
            setUser(new User() {{ setUserID(1); }});
            setPersonal(true);
        }});

        when(calendarContainer.saveCalendarEvent(any())).thenReturn(true);

        String json = """
    {
        "calendar": { "calendarID": 20 },
        "event": { "eventID": 10 }
    }
    """;

        mockMvc.perform(post("/calendar/saveCalendarEvent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string("Saved calendarEvent"));
    }

    @Test
    void testSaveCalendarEvent_failure_returnsNoContent() throws Exception {
        when(calendarContainer.saveCalendarEvent(any())).thenReturn(false);

        String json = """
    {
        "calendar": { "calendarID": 20 },
        "event": { "eventID": 10 }
    }
    """;

        mockMvc.perform(post("/calendar/saveCalendarEvent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNoContent());  // <-- expect 204 here
    }
}