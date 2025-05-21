package com.example.backend.Containers;

import com.example.backend.DTOs.EventDTO;
import com.example.backend.Interfaces.EventCategoryInterface;
import com.example.backend.Interfaces.EventInterface;
import com.example.backend.Mappers.CategoryMapper;
import com.example.backend.Mappers.EventMapper;
import com.example.backend.Models.Category;
import com.example.backend.Models.Event;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventContainer {
    private final EventInterface repo;
    private final EventCategoryInterface eventCategoryRepo;

    public EventContainer(EventInterface _repo, EventCategoryInterface _eventCategoryRepo) {
        this.repo = _repo;
        this.eventCategoryRepo = _eventCategoryRepo;
    }

    public List<Event> GetAllEvents() {
        List<Event> events = EventMapper.toModelList((List<EventDTO>)repo.findAll());
        for (Event event : events){
            if(event != null) {
                List<Category> categories = CategoryMapper.toModelList(eventCategoryRepo.findCategoriesByEventID(event.getEventID()));

                if (categories != null && !categories.isEmpty()) {
                    boolean success = event.SetEventCategories(categories);
                    if (!success) {
                        return null;
                    }
                }
            }
        }
        return events;
    }

    public Event GetEventById(int eventID) {
        Event event = EventMapper.toModel(repo.findById(eventID).orElse(null));
        List<Category> categories = CategoryMapper.toModelList(eventCategoryRepo.findCategoriesByEventID(eventID));

        if(categories != null && !categories.isEmpty() && event != null) {
            boolean success = event.SetEventCategories(categories);
            if(!success) {
                return null;
            }
        }
        return event;
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

    @Transactional
    public void deleteAllEventsByUserId(int userID) {
        repo.deleteAllByUserDTO_UserID(userID);
    }
}
