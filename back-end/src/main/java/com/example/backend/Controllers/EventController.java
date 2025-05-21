package com.example.backend.Controllers;

import com.example.backend.Containers.EventContainer;
import com.example.backend.Containers.UserContainer;
import com.example.backend.Models.Event;
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
    public ResponseEntity<List<Event>> GetAllEvents() {
        List<Event> events = eventContainer.GetAllEvents();
        if (events == null || !events.iterator().hasNext()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(events);
        }
    }

    @GetMapping("/getEventById")
    public ResponseEntity<Event> GetEventById(@RequestParam int eventID) {
        Event event = eventContainer.GetEventById(eventID);
        if (event != null) {
            return ResponseEntity.ok(event);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/createEvent")
    public ResponseEntity<Event> CreateEvent(@RequestBody Event event) {
        int userID = event.getUser().getUserID();
        event.setUser(userContainer.GetUserById(userID));

        Event createdEvent = eventContainer.CreateEvent(event);
        return ResponseEntity.ok(createdEvent);
    }

    @DeleteMapping("/deleteEvent")
    public ResponseEntity<String> DeleteEvent(@RequestParam int eventID) {
        if (eventContainer.DeleteEvent(eventID)){
            return ResponseEntity.ok("Deleted event");
        }
        return ResponseEntity.noContent().build();
    }
}
