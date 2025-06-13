package com.example.backend.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@SuppressWarnings("unused")
public class EventDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int eventID;

    @ManyToOne
    @JoinColumn(name = "UserID", referencedColumnName = "UserID")
    private UserDTO userDTO;

    private String name;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    public EventDTO(int eventID, UserDTO userDTO, String name, LocalDateTime startDate, LocalDateTime endDate) {
        this.eventID = eventID;
        this.userDTO = userDTO;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public EventDTO(UserDTO userDTO, String name, LocalDateTime startDate, LocalDateTime endDate) {
        this.userDTO = userDTO;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public EventDTO() {}
}
