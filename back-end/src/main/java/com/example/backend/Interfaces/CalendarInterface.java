package com.example.backend.Interfaces;

import com.example.backend.DTOs.CalendarDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalendarInterface extends CrudRepository<CalendarDTO, Integer> {
    void deleteAllByUserDTO_UserID(int userID);

    @Query("SELECT new com.example.backend.DTOs.CalendarDTO(c.calendarID, c.userDTO, c.isPersonal) FROM CalendarDTO c WHERE c.userDTO.userID = ?1")
    List<CalendarDTO> getCalendarsByUserID(int userID);
}
