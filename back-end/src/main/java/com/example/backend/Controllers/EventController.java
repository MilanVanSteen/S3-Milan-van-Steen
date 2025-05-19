package com.example.backend.Controllers;

import com.example.backend.Containers.EventContainer;
import com.example.backend.Interfaces.EventInterface;
import com.example.backend.Models.Event;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public class EventController {
    private final EventContainer eventContainer;

    public EventController(EventInterface eventInterface) {
        this.eventContainer = new EventContainer(eventInterface);
    }

    public ResponseEntity<List<Event>> GetAllEvents() {
        List<Event> events = eventContainer.GetAllEvents();
        if (events == null || !events.iterator().hasNext()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(events);
        }
    }

    public ResponseEntity<Event> GetEventById(@RequestParam int eventID) {
        Event event = eventContainer.GetEventById(eventID);
        if (event != null) {
            return ResponseEntity.ok(event);
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<Event> CreateEvent(@RequestBody Event event) {
        Event createdEvent = eventContainer.CreateEvent(event);
        return ResponseEntity.ok(createdEvent);
    }

    public ResponseEntity<String> DeleteEvent(@RequestParam int eventID) {
        if (eventContainer.DeleteEvent(eventID)){
            return ResponseEntity.ok("Deleted event");
        }
        return ResponseEntity.noContent().build();
    }
}
