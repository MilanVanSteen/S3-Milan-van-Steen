package com.example.backend.controllers;

import com.example.backend.containers.UserContainer;
import com.example.backend.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserContainer userContainer;

    @Autowired
    public UserController(UserContainer userContainer) {
        this.userContainer = userContainer;
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userContainer.getAllUsers();
        if (users == null || !users.iterator().hasNext()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(users);
        }
    }

    @GetMapping("/getUserById")
    public ResponseEntity<User> getUserById(@RequestParam int userID) {
        User user = userContainer.getUserById(userID);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/createUser")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userContainer.createUser(user);
        return ResponseEntity.ok(createdUser);
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<String> deleteUser(@RequestParam int userID) {
        if (userContainer.deleteUser(userID)){
            return ResponseEntity.ok("Deleted user");
        }
        return ResponseEntity.noContent().build();
    }
}
