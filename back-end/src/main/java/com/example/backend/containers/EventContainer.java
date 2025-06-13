package com.example.backend.containers;

import com.example.backend.dtos.EventDTO;
import com.example.backend.interfaces.EventCategoryInterface;
import com.example.backend.interfaces.EventInterface;
import com.example.backend.mappers.CategoryMapper;
import com.example.backend.mappers.EventMapper;
import com.example.backend.models.Category;
import com.example.backend.models.Event;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventContainer {
    private final EventInterface repo;
    private final EventCategoryInterface eventCategoryRepo;

    public EventContainer(EventInterface repo, EventCategoryInterface eventCategoryRepo) {
        this.repo = repo;
        this.eventCategoryRepo = eventCategoryRepo;
    }

    public List<Event> getAllEvents() {
        List<Event> events = EventMapper.toModelList((List<EventDTO>)repo.findAll());
        for (Event event : events){
            if(event != null) {
                List<Category> categories = CategoryMapper.toModelList(eventCategoryRepo.findCategoriesByEventID(event.getEventID()));

                if (categories != null && !categories.isEmpty()) {
                    boolean success = event.setEventCategories(categories);
                    if (!success) {
                        return null;
                    }
                }
            }
        }
        return events;
    }

    public Event getEventById(int eventID) {
        Event event = EventMapper.toModel(repo.findById(eventID).orElse(null));
        List<Category> categories = CategoryMapper.toModelList(eventCategoryRepo.findCategoriesByEventID(eventID));

        if(categories != null && !categories.isEmpty() && event != null) {
            boolean success = event.setEventCategories(categories);
            if(!success) {
                return null;
            }
        }
        return event;
    }


    public Event createEvent(Event event){
        return EventMapper.toModel(repo.save(EventMapper.toDTO(event)));
    }

    public boolean deleteEvent(int eventID){
        try {
            repo.deleteById(eventID);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Transactional
    public void deleteAllEventsByUserId(int userID) {
        repo.deleteAllByUserDTO_UserID(userID);
    }
}
