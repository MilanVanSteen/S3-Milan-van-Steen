package com.example.backend.config;

import com.example.backend.containers.CalendarContainer;
import com.example.backend.containers.CategoryContainer;
import com.example.backend.containers.EventContainer;
import com.example.backend.containers.UserContainer;
import com.example.backend.interfaces.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContainerConfig {

    @Bean
    public EventContainer eventContainer(EventInterface eventInterface, EventCategoryInterface eventCategoryInterface) {
        return new EventContainer(eventInterface, eventCategoryInterface);
    }

    @Bean
    public CalendarContainer calendarContainer(CalendarInterface calendarInterface, CalendarEventInterface calendarEventInterface) {
        return new CalendarContainer(calendarInterface, calendarEventInterface);
    }

    @Bean
    public UserContainer userContainer(UserInterface userInterface, EventContainer eventContainer, CalendarContainer calendarContainer) {
        return new UserContainer(userInterface, eventContainer, calendarContainer);
    }

    @Bean
    public CategoryContainer categoryContainer(CategoryInterface categoryInterface) {
        return new CategoryContainer(categoryInterface);
    }
}
