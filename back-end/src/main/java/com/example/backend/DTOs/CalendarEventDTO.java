package com.example.backend.DTOs;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "CalendarEvent")
@Getter
public class CalendarEventDTO {
    @Id
    private int CalendarEventID;

    @ManyToOne
    @JoinColumn(name = "CalendarID")
    private CalendarDTO CalendarDTO;

    @ManyToOne
    @JoinColumn(name = "EventID")
    private EventDTO EventDTO;
}
