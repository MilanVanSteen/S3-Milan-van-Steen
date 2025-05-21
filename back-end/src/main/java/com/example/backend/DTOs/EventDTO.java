package com.example.backend.DTOs;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class EventDTO {
    @Id
    private int EventID;
    @ManyToOne
    @JoinColumn(name = "UserID", referencedColumnName = "UserID")
    private UserDTO UserDTO;
    private String Name;
    private LocalDateTime StartDate;
    private LocalDateTime EndDate;

    public EventDTO(int eventID, UserDTO userDTO, String name, LocalDateTime startDate, LocalDateTime endDate) {
        this.EventID = eventID;
        this.UserDTO = userDTO;
        this.Name = name;
        this.StartDate = startDate;
        this.EndDate = endDate;
    }

    public EventDTO() {

    }
}
