package com.example.backend.controllers.unit;

import com.example.backend.containers.UserContainer;
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
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserContainer userContainer;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testGetAllUsers_withResults_returnsOk() throws Exception {
        User user = new User();
        user.setUserID(1);
        user.setEmail("test@example.com");
        user.setArea("TestArea");
        user.setPassword("secret");

        when(userContainer.getAllUsers()).thenReturn(List.of(user));

        mockMvc.perform(get("/user/getAllUsers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userID").value(1))
                .andExpect(jsonPath("$[0].email").value("test@example.com"));
    }

    @Test
    void testGetAllUsers_noResults_returnsNoContent() throws Exception {
        when(userContainer.getAllUsers()).thenReturn(List.of());

        mockMvc.perform(get("/user/getAllUsers"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetUserById_exists_returnsOk() throws Exception {
        User user = new User();
        user.setUserID(1);
        user.setEmail("found@example.com");

        when(userContainer.getUserById(1)).thenReturn(user);

        mockMvc.perform(get("/user/getUserById").param("userID", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("found@example.com"));
    }

    @Test
    void testGetUserById_notFound_returnsNotFound() throws Exception {
        when(userContainer.getUserById(anyInt())).thenReturn(null);

        mockMvc.perform(get("/user/getUserById").param("userID", "99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateUser_returnsCreatedUser() throws Exception {
        User user = new User();
        user.setUserID(5);
        user.setEmail("created@example.com");
        user.setArea("AreaX");
        user.setPassword("pw");

        when(userContainer.createUser(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/user/createUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userID").value(5))
                .andExpect(jsonPath("$.email").value("created@example.com"));
    }

    @Test
    void testDeleteUser_success_returnsOk() throws Exception {
        when(userContainer.deleteUser(1)).thenReturn(true);

        mockMvc.perform(delete("/user/deleteUser").param("userID", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted user"));
    }

    @Test
    void testDeleteUser_notFound_returnsNoContent() throws Exception {
        when(userContainer.deleteUser(anyInt())).thenReturn(false);

        mockMvc.perform(delete("/user/deleteUser").param("userID", "123"))
                .andExpect(status().isNoContent());
    }
}