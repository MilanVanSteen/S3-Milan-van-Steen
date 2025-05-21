package com.example.backend.DTOs;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "EventCategory")
@Getter
public class EventCategoryDTO {
    @Id
    private int EventCategoryID;

    @ManyToOne
    @JoinColumn(name = "EventID")
    private EventDTO EventDTO;

    @ManyToOne
    @JoinColumn(name = "CategoryID")
    private CategoryDTO CategoryDTO;
}


