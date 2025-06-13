package com.example.backend.dtos;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@SuppressWarnings("unused")
public class CalendarDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int calendarID;

    @ManyToOne
    @JoinColumn(name = "UserID", referencedColumnName = "UserID")
    private UserDTO userDTO;

    private boolean personal;

    public CalendarDTO(int calendarID, UserDTO userDTO, boolean personal) {
        this.calendarID = calendarID;
        this.userDTO = userDTO;
        this.personal = personal;
    }

    public CalendarDTO(UserDTO userDTO, boolean personal) {
        this.userDTO = userDTO;
        this.personal = personal;
    }

    public CalendarDTO() {}
}
