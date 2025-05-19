package com.example.backend.Controllers;

import com.example.backend.Containers.UserContainer;
import com.example.backend.Interfaces.UserInterface;
import com.example.backend.Models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class UserController {
    private final UserContainer userContainer;

    public UserController(UserInterface userInterface) {
        this.userContainer = new UserContainer(userInterface);
    }

    public ResponseEntity<List<User>> GetAllUsers() {
        List<User> users = userContainer.GetAllUsers();
        if (users == null || !users.iterator().hasNext()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(users);
        }
    }

    public ResponseEntity<User> GetUserById(@RequestParam int userID) {
        User user = userContainer.GetUserById(userID);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<User> CreateUser(@RequestBody User user) {
        User createdUser = userContainer.CreateUser(user);
        return ResponseEntity.ok(createdUser);
    }

    public ResponseEntity<String> DeleteUser(@RequestParam int userID) {
        if (userContainer.DeleteUser(userID)){
            return ResponseEntity.ok("Deleted user");
        }
        return ResponseEntity.noContent().build();
    }
}
