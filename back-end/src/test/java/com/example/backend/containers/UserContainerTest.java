package com.example.backend.containers;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.backend.dtos.UserDTO;
import com.example.backend.interfaces.UserInterface;
import com.example.backend.models.User;
import com.example.backend.mappers.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserContainerTest {

    @Mock
    private UserInterface userRepo;

    @Mock
    private EventContainer eventContainer;

    @Mock
    private CalendarContainer calendarContainer;

    private UserContainer userContainer;

    @BeforeEach
    void setUp() {
        userContainer = new UserContainer(userRepo, eventContainer, calendarContainer);
    }

    @Test
    void testGetAllUsersReturnsUsers() {
        // Arrange
        UserDTO userDTO = new UserDTO(1, "test@example.com", "password", "area1");
        when(userRepo.findAll()).thenReturn(List.of(userDTO));

        // Act
        List<User> users = userContainer.getAllUsers();

        // Assert
        assertNotNull(users);
        assertFalse(users.isEmpty());
        assertEquals("test@example.com", users.get(0).getEmail());
    }

    @Test
    void testGetUserByIdReturnsUser() {
        // Arrange
        int userId = 1;
        UserDTO userDTO = new UserDTO(userId, "test@example.com", "password", "area1");
        when(userRepo.findById(userId)).thenReturn(Optional.of(userDTO));

        // Act
        User user = userContainer.getUserById(userId);

        // Assert
        assertNotNull(user);
        assertEquals("test@example.com", user.getEmail());
    }

    @Test
    void testGetUserByIdReturnsNullWhenNotFound() {
        // Arrange
        when(userRepo.findById(anyInt())).thenReturn(Optional.empty());

        // Act
        User user = userContainer.getUserById(999);

        // Assert
        assertNull(user);
    }

    @Test
    void testCreateUserReturnsCreatedUser() {
        // Arrange
        User user = new User(1, "newuser@example.com", "password123", "someArea");
        UserDTO userDTO = UserMapper.toDTO(user);
        when(userRepo.save(any(UserDTO.class))).thenReturn(userDTO);

        // Act
        User created = userContainer.createUser(user);

        // Assert
        assertNotNull(created);
        assertEquals(user.getEmail(), created.getEmail());
        verify(userRepo, times(1)).save(any(UserDTO.class));
    }

    @Test
    void testDeleteUserReturnsTrueOnSuccess() {
        // Arrange
        doNothing().when(eventContainer).deleteAllEventsByUserId(1);
        doNothing().when(calendarContainer).deleteAllCalendarsByUserId(1);
        doNothing().when(userRepo).deleteById(1);

        // Act
        boolean result = userContainer.deleteUser(1);

        // Assert
        assertTrue(result);
        verify(eventContainer, times(1)).deleteAllEventsByUserId(1);
        verify(calendarContainer, times(1)).deleteAllCalendarsByUserId(1);
        verify(userRepo, times(1)).deleteById(1);
    }

    @Test
    void testDeleteUserReturnsFalseOnException() {
        // Arrange
        doNothing().when(eventContainer).deleteAllEventsByUserId(1);
        doNothing().when(calendarContainer).deleteAllCalendarsByUserId(1);
        doThrow(new RuntimeException()).when(userRepo).deleteById(1);

        // Act
        boolean result = userContainer.deleteUser(1);

        // Assert
        assertFalse(result);
        verify(eventContainer, times(1)).deleteAllEventsByUserId(1);
        verify(calendarContainer, times(1)).deleteAllCalendarsByUserId(1);
        verify(userRepo, times(1)).deleteById(1);
    }
}