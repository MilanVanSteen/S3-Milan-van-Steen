package com.example.backend.controllers;

import com.example.backend.containers.EventContainer;
import com.example.backend.containers.UserContainer;
import com.example.backend.models.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event")
public class EventController {
    private final EventContainer eventContainer;
    private final UserContainer userContainer;

    @Autowired
    public EventController(EventContainer eventContainer, UserContainer userContainer) {
        this.eventContainer = eventContainer;
        this.userContainer = userContainer;
    }

    @GetMapping("/getAllEvents")
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = eventContainer.getAllEvents();
        if (events == null || !events.iterator().hasNext()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(events);
        }
    }

    @GetMapping("/getEventById")
    public ResponseEntity<Event> getEventById(@RequestParam int eventID) {
        Event event = eventContainer.getEventById(eventID);
        if (event != null) {
            return ResponseEntity.ok(event);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/createEvent")
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        int userID = event.getUser().getUserID();
        event.setUser(userContainer.getUserById(userID));

        Event createdEvent = eventContainer.createEvent(event);
        return ResponseEntity.ok(createdEvent);
    }

    @DeleteMapping("/deleteEvent")
    public ResponseEntity<String> deleteEvent(@RequestParam int eventID) {
        if (eventContainer.deleteEvent(eventID)){
            return ResponseEntity.ok("Deleted event");
        }
        return ResponseEntity.noContent().build();
    }
}
