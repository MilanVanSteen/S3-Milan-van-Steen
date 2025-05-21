package com.example.backend.DTOs;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "EventCategory")
@Getter
public class EventCategoryDTO {
    @Id
    private int eventCategoryID;

    @ManyToOne
    @JoinColumn(name = "EventID")
    private EventDTO eventDTO;

    @ManyToOne
    @JoinColumn(name = "CategoryID")
    private CategoryDTO categoryDTO;
}


