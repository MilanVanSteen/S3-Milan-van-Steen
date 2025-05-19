package com.example.backend.Containers;

import com.example.backend.DTOs.UserDTO;
import com.example.backend.Mappers.UserMapper;
import com.example.backend.Interfaces.UserInterface;
import com.example.backend.Models.User;

import java.util.List;

public class UserContainer {
    private final UserInterface repo;

    public UserContainer(UserInterface _repo) {
        this.repo = _repo;
    }

    public List<User> getAllUsers() {
        return UserMapper.toModelList((List<UserDTO>)repo.findAll());
    }

    public User getUserById(int userID) {
        return UserMapper.toModel(repo.findById(userID).orElse(null));
    }


    public User CreateUser(User user){
        return UserMapper.toModel(repo.save(UserMapper.toDTO(user)));
    }

    public boolean DeleteUser(int userID){
        try {
            repo.deleteById(userID);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
