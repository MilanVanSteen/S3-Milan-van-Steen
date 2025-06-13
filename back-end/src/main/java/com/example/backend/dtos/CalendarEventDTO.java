package com.example.backend.dtos;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "CalendarEvent")
@Getter
@Setter
@SuppressWarnings("unused")
public class CalendarEventDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public CalendarEventDTO(CalendarDTO calendarDTO, EventDTO eventDTO) {
        this.calendarDTO = calendarDTO;
        this.eventDTO = eventDTO;
    }

    public CalendarEventDTO() {}
}
