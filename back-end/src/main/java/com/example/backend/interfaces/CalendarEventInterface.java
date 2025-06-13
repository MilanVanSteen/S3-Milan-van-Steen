package com.example.backend.interfaces;

import com.example.backend.dtos.CalendarEventDTO;
import com.example.backend.dtos.EventDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalendarEventInterface extends CrudRepository<CalendarEventDTO, Integer> {
    @Query("SELECT new EventDTO(e.eventID, e.userDTO, e.name, e.startDate, e.endDate) FROM CalendarEventDTO c JOIN c.eventDTO e WHERE c.calendarDTO.calendarID = ?1")
    List<EventDTO> findEventsByCalendarID(int calendarID);
}
