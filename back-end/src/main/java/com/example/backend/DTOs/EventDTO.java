package com.example.backend.DTOs;

import lombok.Getter;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Getter
public class EventDTO {
    @Id
    private int EventID;
    private int UserID;
    private String Name;
    private LocalDateTime StartDate;
    private LocalDateTime EndDate;

    public EventDTO(int eventID, int userID, String name, LocalDateTime startDate, LocalDateTime endDate) {
        this.EventID = eventID;
        this.UserID = userID;
        this.Name = name;
        this.StartDate = startDate;
        this.EndDate = endDate;
    }
}
