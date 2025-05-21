package com.example.backend.Interfaces;

import com.example.backend.DTOs.CalendarDTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalendarInterface extends CrudRepository<CalendarDTO, Integer> {
    void deleteAllByUserDTO_UserID(int userID);
}
