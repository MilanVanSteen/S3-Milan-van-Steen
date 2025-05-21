package com.example.backend.DTOs;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class CalendarDTO {
    @Id
    private int CalendarID;
    @ManyToOne
    @JoinColumn(name = "UserID", referencedColumnName = "UserID")
    private UserDTO UserDTO;
    private boolean IsPersonal;

    public CalendarDTO(int calendarID, UserDTO userDTO, boolean isPersonal) {
        this.CalendarID = calendarID;
        this.UserDTO = userDTO;
        this.IsPersonal = isPersonal;
    }

    public CalendarDTO() {

    }
}
