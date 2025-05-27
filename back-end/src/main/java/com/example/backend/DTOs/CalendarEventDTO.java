package com.example.backend.DTOs;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "CalendarEvent")
@Getter
public class CalendarEventDTO {
    @Id
    private int calendarEventID;

    @ManyToOne
    @JoinColumn(name = "CalendarID")
    private CalendarDTO calendarDTO;

    @ManyToOne
    @JoinColumn(name = "EventID")
    private EventDTO eventDTO;

    public CalendarEventDTO(int calendarEventID, CalendarDTO calendarDTO, EventDTO eventDTO) {
        this.calendarEventID = calendarEventID;
        this.calendarDTO = calendarDTO;
        this.eventDTO = eventDTO;
    }

    public CalendarEventDTO() {}
}
