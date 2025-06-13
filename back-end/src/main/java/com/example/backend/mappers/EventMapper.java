package com.example.backend.mappers;

import com.example.backend.dtos.EventDTO;
import com.example.backend.dtos.UserDTO;
import com.example.backend.models.Event;
import com.example.backend.models.User;

import java.util.ArrayList;
import java.util.List;

public class EventMapper {
    // Private constructor to prevent instantiation
    private EventMapper() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static EventDTO toDTO(Event event) {
        if (event == null) {
            return null;
        }

        UserDTO userDTO = UserMapper.toDTO(event.getUser());

        if (event.getEventID() == 0) {
            // New event, no ID yet
            return new EventDTO(userDTO, event.getName(), event.getStartDate(), event.getEndDate());
        } else {
            // Existing event with ID
            return new EventDTO(event.getEventID(), userDTO, event.getName(), event.getStartDate(), event.getEndDate());
        }
    }



    public static Event toModel(EventDTO eventDTO) {
        if(eventDTO == null){
            return null;
        }
        User user = UserMapper.toModel(eventDTO.getUserDTO());
        return new Event(eventDTO.getEventID(), user, eventDTO.getName(), eventDTO.getStartDate(), eventDTO.getEndDate());
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
