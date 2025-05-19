package com.example.backend.Containers;

import com.example.backend.DTOs.EventDTO;
import com.example.backend.Interfaces.EventInterface;
import com.example.backend.Mappers.EventMapper;
import com.example.backend.Models.Event;

import java.util.List;

public class EventContainer {
    private final EventInterface repo;

    public EventContainer(EventInterface _repo) {
        this.repo = _repo;
    }

    public List<Event> getAllEvents() {
        return EventMapper.toModelList((List<EventDTO>)repo.findAll());
    }

    public Event getEventById(int eventID) {
        return EventMapper.toModel(repo.findById(eventID).orElse(null));
    }


    public Event CreateEvent(Event event){
        return EventMapper.toModel(repo.save(EventMapper.toDTO(event)));
    }

    public boolean DeleteEvent(int eventID){
        try {
            repo.deleteById(eventID);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
