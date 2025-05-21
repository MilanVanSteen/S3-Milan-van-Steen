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
}
