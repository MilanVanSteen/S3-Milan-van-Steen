package com.example.backend.controllers.integration;

import com.example.backend.dtos.EventDTO;
import com.example.backend.dtos.UserDTO;
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

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class EventControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EventInterface eventRepository;

    @Autowired
    private UserInterface userRepository;

    private UserDTO testUser;
    private EventDTO testEvent;

    @BeforeEach
    void setUp() {
        eventRepository.deleteAll();
        userRepository.deleteAll();

        // Create and save a test user
        testUser = new UserDTO("testuser@example.com", "area", "password");
        userRepository.save(testUser);

        // Create and save a test event linked to the test user
        testEvent = new EventDTO(testUser, "Test Event",  LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1));
        eventRepository.save(testEvent);
    }

    @Test
    void testGetAllEvents_returnsFromDatabase() throws Exception {
        mockMvc.perform(get("/event/getAllEvents"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value(testEvent.getName()))
                .andExpect(jsonPath("$[0].eventID").isNumber())
                .andExpect(jsonPath("$[0].user.email").value(testUser.getEmail()));
    }

    @Test
    void testGetEventById_returnsCorrectEvent() throws Exception {
        mockMvc.perform(get("/event/getEventById")
                        .param("eventID", String.valueOf(testEvent.getEventID())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eventID").value(testEvent.getEventID()))
                .andExpect(jsonPath("$.name").value(testEvent.getName()))
                .andExpect(jsonPath("$.user.email").value(testUser.getEmail()));
    }

    @Test
    void testCreateEvent_createsAndReturnsEvent() throws Exception {
        String json = """
        {
            "name": "New Event",
            "user": { "userID": %d },
            "startDate": "2025-06-20T10:00:00",
            "endDate": "2025-06-21T10:00:00"
        }
        """.formatted(testUser.getUserID());

        mockMvc.perform(
                        post("/event/createEvent")
                                .contentType("application/json")
                                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eventID").isNumber())
                .andExpect(jsonPath("$.name").value("New Event"))
                .andExpect(jsonPath("$.user.userID").value(testUser.getUserID()));
    }

    @Test
    void testDeleteEvent_deletesSuccessfully() throws Exception {
        mockMvc.perform(delete("/event/deleteEvent")
                        .param("eventID", String.valueOf(testEvent.getEventID())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Deleted event"));

        // Optionally assert that event is gone
        mockMvc.perform(get("/event/getEventById")
                        .param("eventID", String.valueOf(testEvent.getEventID())))
                .andExpect(status().isNotFound());
    }

}