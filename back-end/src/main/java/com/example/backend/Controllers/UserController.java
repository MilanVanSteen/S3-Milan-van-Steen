package com.example.backend.Controllers;

import com.example.backend.Containers.UserContainer;
import com.example.backend.Interfaces.UserInterface;
import com.example.backend.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserContainer userContainer;

    @Autowired
    public UserController(UserInterface userInterface) {
        this.userContainer = new UserContainer(userInterface);
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<User>> GetAllUsers() {
        List<User> users = userContainer.GetAllUsers();
        if (users == null || !users.iterator().hasNext()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(users);
        }
    }

    @GetMapping("/getUserById")
    public ResponseEntity<User> GetUserById(@RequestParam int userID) {
        User user = userContainer.GetUserById(userID);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/createUser")
    public ResponseEntity<User> CreateUser(@RequestBody User user) {
        User createdUser = userContainer.CreateUser(user);
        return ResponseEntity.ok(createdUser);
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<String> DeleteUser(@RequestParam int userID) {
        if (userContainer.DeleteUser(userID)){
            return ResponseEntity.ok("Deleted user");
        }
        return ResponseEntity.noContent().build();
    }
}
