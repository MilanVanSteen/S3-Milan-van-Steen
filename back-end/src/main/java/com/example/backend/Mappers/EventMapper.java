package com.example.backend.Mappers;

import com.example.backend.DTOs.EventDTO;
import com.example.backend.Models.Event;

import java.util.ArrayList;
import java.util.List;

public class EventMapper {
    public static EventDTO toDTO(Event event) {
        if(event == null){
            return null;
        }
        return new EventDTO(event.getEventID(), event.getUserID(), event.getName(), event.getStartDate(), event.getEndDate());
    }

    public static Event toModel(EventDTO eventDTO) {
        if(eventDTO == null){
            return null;
        }
        return new Event(eventDTO.getEventID(), eventDTO.getUserID(), eventDTO.getName(), eventDTO.getStartDate(), eventDTO.getEndDate());
    }

    public static List<EventDTO> toDTOList(List<Event> events) {
        if (events == null) {
            return new ArrayList<>();
        }
        return events.stream()
                .map(EventMapper::toDTO)
                .toList();
    }

    public static List<Event> toModelList(List<EventDTO> eventDTOs) {
        if (eventDTOs == null) {
            return new ArrayList<>();
        }
        return eventDTOs.stream()
                .map(EventMapper::toModel)
                .toList();
    }
}
