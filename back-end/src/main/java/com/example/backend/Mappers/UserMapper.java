package com.example.backend.Mappers;

import com.example.backend.DTOs.UserDTO;
import com.example.backend.Models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

public class UserMapper {
    public static UserDTO toDTO(User user) {
        if(user == null){
            return null;
        }
        return new UserDTO(user.getUserID(), user.getEmail(), user.getPassword(), user.getArea());
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
