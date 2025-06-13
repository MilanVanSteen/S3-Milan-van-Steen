package com.example.backend.mappers;

import com.example.backend.dtos.UserDTO;
import com.example.backend.models.User;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {
    public static UserDTO toDTO(User user) {
        if(user == null){
            return null;
        }

        if(user.getUserID() == 0){
            // New user, don't set ID
            return new UserDTO(user.getEmail(), user.getPassword(), user.getArea());
        } else {
            // Existing user with ID
            return new UserDTO(user.getUserID(), user.getEmail(), user.getPassword(), user.getArea());
        }
    }

    public static User toModel(UserDTO userDTO) {
        if(userDTO == null){
            return null;
        }
        return new User(userDTO.getUserID(), userDTO.getEmail(), userDTO.getPassword(), userDTO.getArea());
    }

    public static List<UserDTO> toDTOList(List<User> users) {
        if (users == null) {
            return new ArrayList<>();
        }
        return users.stream()
                .map(UserMapper::toDTO)
                .toList();
    }

    public static List<User> toModelList(List<UserDTO> userDTOs) {
        if (userDTOs == null) {
            return new ArrayList<>();
        }
        return userDTOs.stream()
                .map(UserMapper::toModel)
                .toList();
    }
}
