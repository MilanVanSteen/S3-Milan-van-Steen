package com.example.backend.containers;

import com.example.backend.dtos.UserDTO;
import com.example.backend.mappers.UserMapper;
import com.example.backend.interfaces.UserInterface;
import com.example.backend.models.User;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserContainer {
    private final UserInterface repo;
    private final EventContainer eventContainer;
    private final CalendarContainer calendarContainer;

    public UserContainer(UserInterface _repo, EventContainer _eventContainer, CalendarContainer _calendarContainer) {
        this.repo = _repo;
        this.eventContainer = _eventContainer;
        this.calendarContainer = _calendarContainer;
    }

    public List<User> GetAllUsers() {
        return UserMapper.toModelList((List<UserDTO>)repo.findAll());
    }

    public User GetUserById(int userID) {
        return UserMapper.toModel(repo.findById(userID).orElse(null));
    }


    public User CreateUser(User user){
        return UserMapper.toModel(repo.save(UserMapper.toDTO(user)));
    }

    @Transactional
    public boolean DeleteUser(int userID){
        try {
            eventContainer.deleteAllEventsByUserId(userID);
            calendarContainer.deleteAllCalendarsByUserId(userID);
            repo.deleteById(userID);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
