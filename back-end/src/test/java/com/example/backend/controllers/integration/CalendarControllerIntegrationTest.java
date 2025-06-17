package com.example.backend.controllers.integration;

import com.example.backend.dtos.CalendarDTO;
import com.example.backend.dtos.EventDTO;
import com.example.backend.dtos.UserDTO;
import com.example.backend.interfaces.CalendarInterface;
import com.example.backend.interfaces.EventInterface;
import com.example.backend.interfaces.UserInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class CalendarControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CalendarInterface calendarRepository;

    @Autowired
    private UserInterface userRepository;

    @Autowired
    private EventInterface eventRepository;

    private UserDTO testUser;
    private CalendarDTO testCalendar;

    @BeforeEach
    void setUp() {
        calendarRepository.deleteAll();
        userRepository.deleteAll();
        eventRepository.deleteAll();

        testUser = new UserDTO();
        testUser = userRepository.save(testUser);

        testCalendar = new CalendarDTO();
        testCalendar.setUserDTO(testUser);
        testCalendar.setPersonal(true);
        testCalendar = calendarRepository.save(testCalendar);
    }

    @Test
    void testGetAllCalendars_returnsCalendars() throws Exception {
        mockMvc.perform(get("/calendar/getAllCalendars"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].calendarID").value(testCalendar.getCalendarID()))
                .andExpect(jsonPath("$[0].user.userID").value(testUser.getUserID()))
                .andExpect(jsonPath("$[0].personal").value(true));
    }

    @Test
    void testGetCalendarById_returnsCalendar() throws Exception {
        mockMvc.perform(get("/calendar/getCalendarById")
                        .param("calendarID", String.valueOf(testCalendar.getCalendarID())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.calendarID").value(testCalendar.getCalendarID()))
                .andExpect(jsonPath("$.user.userID").value(testUser.getUserID()));
    }

    @Test
    void testGetCalendarsByUserID_returnsCalendars() throws Exception {
        mockMvc.perform(get("/calendar/getCalendarsByUserID")
                        .param("userID", String.valueOf(testUser.getUserID())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].calendarID").value(testCalendar.getCalendarID()))
                .andExpect(jsonPath("$[0].user.userID").value(testUser.getUserID()));
    }

    @Test
    void testCreateCalendar_createsAndReturns() throws Exception {
        String json = """
    {
        "user": { "userID": %d },
        "personal": false
    }
    """.formatted(testUser.getUserID());

        mockMvc.perform(post("/calendar/createCalendar")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.calendarID").isNumber())
                .andExpect(jsonPath("$.user.userID").value(testUser.getUserID()))
                .andExpect(jsonPath("$.personal").value(false));
    }

    @Test
    void testDeleteCalendar_deletesSuccessfully() throws Exception {
        mockMvc.perform(delete("/calendar/deleteCalendar")
                        .param("calendarID", String.valueOf(testCalendar.getCalendarID())))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted calendar"));
    }

    @Test
    void testSaveCalendarEvent_createsAndReturns() throws Exception {
        EventDTO testEvent = new EventDTO();
        testEvent.setName("Test Event");
        testEvent = eventRepository.save(testEvent);

        String json = """
        {
            "calendar": { "calendarID": %d },
            "event": { "eventID": %d }
        }
        """.formatted(testCalendar.getCalendarID(), testEvent.getEventID());

        mockMvc.perform(post("/calendar/saveCalendarEvent")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string("Saved calendarEvent"));
    }
}