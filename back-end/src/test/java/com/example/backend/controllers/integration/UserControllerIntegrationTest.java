package com.example.backend.controllers.integration;

import com.example.backend.dtos.UserDTO;
import com.example.backend.interfaces.UserInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserInterface userRepository;

    private UserDTO testUser;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();

        testUser = new UserDTO("testuser@example.com", "TestArea", "password123");
        userRepository.save(testUser);
    }

    @Test
    void testGetAllUsers_returnsFromDatabase() throws Exception {
        mockMvc.perform(get("/user/getAllUsers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].email").value(testUser.getEmail()))
                .andExpect(jsonPath("$[0].userID").isNumber());
    }

    @Test
    void testGetUserById_returnsCorrectUser() throws Exception {
        mockMvc.perform(get("/user/getUserById")
                        .param("userID", String.valueOf(testUser.getUserID())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userID").value(testUser.getUserID()))
                .andExpect(jsonPath("$.email").value(testUser.getEmail()));
    }

    @Test
    void testCreateUser_createsAndReturnsUser() throws Exception {
        String json = """
        {
            "email": "newuser@example.com",
            "area": "NewArea",
            "password": "newPassword"
        }
        """;

        mockMvc.perform(post("/user/createUser")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userID").isNumber())
                .andExpect(jsonPath("$.email").value("newuser@example.com"));
    }

    @Test
    void testDeleteUser_deletesSuccessfully() throws Exception {
        mockMvc.perform(delete("/user/deleteUser")
                        .param("userID", String.valueOf(testUser.getUserID())))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted user"));

        // Verify user is deleted
        mockMvc.perform(get("/user/getUserById")
                        .param("userID", String.valueOf(testUser.getUserID())))
                .andExpect(status().isNotFound());
    }
}
