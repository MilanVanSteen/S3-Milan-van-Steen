package com.example.backend.Interfaces;

import com.example.backend.DTOs.CalendarEventDTO;
import com.example.backend.DTOs.EventDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalendarEventInterface extends CrudRepository<CalendarEventDTO, Integer> {
    @Query("SELECT new EventDTO(e.EventID, e.UserDTO, e.Name, e.StartDate, e.EndDate) FROM CalendarEventDTO c JOIN EventDTO e WHERE c.CalendarDTO.CalendarID = ?1")
    List<EventDTO> findEventsByCalendarID(int questionnaireID);
}
