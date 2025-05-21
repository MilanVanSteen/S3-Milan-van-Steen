package com.example.backend.DTOs;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class CalendarDTO {
    @Id
    private int calendarID;
    @ManyToOne
    @JoinColumn(name = "UserID", referencedColumnName = "UserID")
    private UserDTO userDTO;
    private boolean isPersonal;

    public CalendarDTO(int calendarID, UserDTO userDTO, boolean isPersonal) {
        this.calendarID = calendarID;
        this.userDTO = userDTO;
        this.isPersonal = isPersonal;
    }

    public CalendarDTO() {

    }
}
