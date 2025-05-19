package com.example.backend.DTOs;

import lombok.Getter;
import org.springframework.data.annotation.Id;

@Getter
public class CalendarDTO {
    @Id
    private int CalendarID;
    private int UserID;
    private boolean IsPersonal;

    public CalendarDTO(int calendarID, int userID, boolean isPersonal) {
        this.CalendarID = calendarID;
        this.UserID = userID;
        this.IsPersonal = isPersonal;
    }
}
