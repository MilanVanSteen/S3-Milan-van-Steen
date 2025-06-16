package com.example.backend.mappers;

import com.example.backend.dtos.UserDTO;
import com.example.backend.models.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    @Test
    void testToDTO_withExistingUser() {
        // Arrange
        User user = new User(1, "test@example.com", "pass123", "region1");

        // Act
        UserDTO dto = UserMapper.toDTO(user);

        // Assert
        assertNotNull(dto);
        assertEquals(1, dto.getUserID());
        assertEquals("test@example.com", dto.getEmail());
        assertEquals("pass123", dto.getPassword());
        assertEquals("region1", dto.getArea());
    }

    @Test
    void testToDTO_withNewUser() {
        // Arrange
        User user = new User(0, "new@example.com", "newPassword", "newArea");

        // Act
        UserDTO dto = UserMapper.toDTO(user);

        // Assert
        assertNotNull(dto);
        assertEquals("new@example.com", dto.getEmail());
        assertEquals("newPassword", dto.getPassword());
        assertEquals("newArea", dto.getArea());
    }

    @Test
    void testToDTO_withNullUser() {
        // Act & Assert
        assertNull(UserMapper.toDTO(null));
    }

    @Test
    void testToModel_withValidDTO() {
        // Arrange
        UserDTO dto = new UserDTO(2, "dto@example.com", "dtoPass", "dtoArea");

        // Act
        User user = UserMapper.toModel(dto);

        // Assert
        assertNotNull(user);
        assertEquals(2, user.getUserID());
        assertEquals("dto@example.com", user.getEmail());
        assertEquals("dtoPass", user.getPassword());
        assertEquals("dtoArea", user.getArea());
    }

    @Test
    void testToModel_withNullDTO() {
        // Act & Assert
        assertNull(UserMapper.toModel(null));
    }

    @Test
    void testToDTOList_withNullList() {
        // Act
        List<UserDTO> result = UserMapper.toDTOList(null);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testToDTOList_withValidUsers() {
        // Arrange
        List<User> users = List.of(new User(3, "a@a.com", "pw", "a"));

        // Act
        List<UserDTO> result = UserMapper.toDTOList(users);

        // Assert
        assertEquals(1, result.size());
        assertEquals(3, result.get(0).getUserID());
    }

    @Test
    void testToModelList_withNullList() {
        // Act
        List<User> result = UserMapper.toModelList(null);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testToModelList_withValidDTOs() {
        // Arrange
        List<UserDTO> userDTOList = List.of(new UserDTO(4, "b@b.com", "pw", "b"));

        // Act
        List<User> result = UserMapper.toModelList(userDTOList);

        // Assert
        assertEquals(1, result.size());
        assertEquals(4, result.get(0).getUserID());
    }
}