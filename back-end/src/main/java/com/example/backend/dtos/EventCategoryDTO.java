package com.example.backend.dtos;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "EventCategory")
@Getter
@Setter
@SuppressWarnings("unused")
public class EventCategoryDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int eventCategoryID;

    @ManyToOne
    @JoinColumn(name = "EventID")
    private EventDTO eventDTO;

    @ManyToOne
    @JoinColumn(name = "CategoryID")
    private CategoryDTO categoryDTO;
}


