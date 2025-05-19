package com.example.backend.Mappers;

import com.example.backend.DTOs.UserDTO;
import com.example.backend.Models.User;

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
}
